package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.object.ObjectId;
import superapp.entity.object.SuperAppObjectEntity;
import superapp.entity.user.UserRole;
import superapp.exception.InvalidInputException;
import superapp.repository.MiniAppCommandsRepository;
import superapp.repository.ObjectRepository;
import superapp.service.MiniAppService;
import superapp.service.MiniAppCommandService;
import superapp.service.UserService;
import superapp.utils.EmailChecker;

import static superapp.common.Consts.APPLICATION_NAME;
import static superapp.exception.Consts.MINI_APP_PERMISSION_EXCEPTION;

import java.util.Date;
import java.util.UUID;

@Service
public class MiniAppCommandServiceImpl implements MiniAppCommandService {

    private static final Logger logger = LoggerFactory.getLogger(MiniAppCommandServiceImpl.class);
    @Autowired
    private MiniAppCommandsRepository miniAppCommandsRepository;
    private ApplicationContext context;
    private ObjectRepository objectRepository;
    @Autowired
    private Environment environment;

    private UserService userService;

    private MiniAppService miniAppService;

    public MiniAppCommandServiceImpl(MiniAppCommandsRepository miniAppRepo,
                                     ObjectRepository objectRepository,
                                     UserService userService,
                                     ApplicationContext context
    ) {
        super();
        this.miniAppCommandsRepository = miniAppRepo;
        this.objectRepository = objectRepository;
        this.userService = userService;
        this.context = context;
    }

    @Override
    public Flux<Object> invokeACommand(String miniAppName, MiniAppCommandBoundary command) {
        this.checkInvokedCommand(command);
        String superApp = command.getInvokedBy().getUserId().getSuperapp();
        String email = command.getInvokedBy().getUserId().getEmail();
        return validateUserCredentials(email, superApp)
                .then(validateAndFetchActiveObject(command.getTargetObject().getObjectId()))
                .flatMapMany(activeObject -> processCommand(command, miniAppName, activeObject))
                .log();
    }

    private Mono<Boolean> validateUserCredentials(String email, String superApp) {
        return userService.isValidUserCredentials(email, superApp, UserRole.MINIAPP_USER)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(MINI_APP_PERMISSION_EXCEPTION));
                    }
                    return Mono.just(true);
                });
    }

    private Mono<SuperAppObjectEntity> validateAndFetchActiveObject(ObjectId objectId) {
        return objectRepository.findById(objectId)
                //TODO: now object is SuperObjectEntity, but I think it has to be SuperAppObjectBoundary
                .flatMap(object -> {
                    if (!object.getActive()) {
                        return Mono.error(new IllegalAccessException("Object is not active"));
                    }
                    return Mono.just(object);
                })
                .switchIfEmpty(Mono.error(new IllegalAccessException("Object not found")));
    }

    private Flux<Object> processCommand(MiniAppCommandBoundary command, String miniAppName, SuperAppObjectEntity activeObject) {
        String id = UUID.randomUUID().toString();
        logger.info("Invoking a miniApp command {}", command);
        command.setInvocationTimestamp(new Date());
        command.getInvokedBy().getUserId().setSuperapp(environment.getProperty(APPLICATION_NAME));
        command.getTargetObject().getObjectId().setSuperapp(environment.getProperty(APPLICATION_NAME));
        MiniAppCommandEntity miniAppCommandEntity = command.toEntity(environment.getProperty(APPLICATION_NAME), miniAppName, id);
        return miniAppCommandsRepository.save(miniAppCommandEntity)
                .flatMapMany(savedEntity -> {
                    logger.info("MiniAppCommand created: {}", savedEntity.toBoundary());
                    return handleCommand(savedEntity);
                });
    }


    private Flux<Object> handleCommand(MiniAppCommandEntity command) {
        String miniAppName = command.getCommandId().getMiniApp();
        switch (miniAppName) {
            case "FitnessCalculator" -> {
                this.miniAppService = this.context.getBean("FitnessCalculator", FitnessCalculatorServiceImpl.class);
            }

            default -> { throw new InvalidInputException("Unknown miniapp"); }
        }
        return this.miniAppService.handleCommand(command);
    }

    private void checkInvokedCommand(MiniAppCommandBoundary command) {
        if (command.getInvokedBy() == null ||
                command.getInvokedBy().getUserId() == null ||
                command.getInvokedBy().getUserId().getSuperapp() == null ||
                command.getInvokedBy().getUserId().getEmail() == null ||
                command.getInvokedBy().getUserId().getSuperapp().isBlank() ||
                command.getInvokedBy().getUserId().getEmail().isBlank())
            throw new InvalidInputException("Invoked by fields cannot be missing or empty");

        if (!EmailChecker.isValidEmail(command.getInvokedBy().getUserId().getEmail()))
            throw new InvalidInputException("Invalid invoking user email");

        if (command.getTargetObject() == null ||
                command.getTargetObject().getObjectId() == null ||
                command.getTargetObject().getObjectId().getSuperapp() == null ||
                command.getTargetObject().getObjectId().getId() == null ||
                command.getTargetObject().getObjectId().getSuperapp().isBlank() ||
                command.getTargetObject().getObjectId().getId().isBlank())
            throw new InvalidInputException("Target object fields cannot be missing or empty");

        if (command.getCommand() == null || command.getCommand().isEmpty())
            throw new InvalidInputException("Command attribute cannot be missing or empty");
    }
}
