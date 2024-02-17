package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.exception.InvalidInputException;
import superapp.repository.MiniAppCommandsRepository;
import superapp.repository.ObjectRepository;
import superapp.repository.UserRepository;
import superapp.service.AbstractService;
import superapp.service.MiniAppCommandService;
import superapp.utils.EmailChecker;

import static superapp.common.Consts.APPLICATION_NAME;
import static superapp.exception.Consts.MINI_APP_PERMISSION_EXCEPTION;

import java.util.Date;
import java.util.UUID;

@Service
public class MiniAppCommandServiceImpl extends AbstractService implements MiniAppCommandService {

    private static final Logger logger = LoggerFactory.getLogger(MiniAppCommandServiceImpl.class);
    @Autowired
    private MiniAppCommandsRepository miniAppCommandsRepository;
    private UserRepository userRepository;
    private ObjectRepository objectRepository;
    @Autowired
    private Environment environment;

    public MiniAppCommandServiceImpl(MiniAppCommandsRepository miniAppRepo, UserRepository userRepository, ObjectRepository objectRepository) {
        super();
        this.miniAppCommandsRepository = miniAppRepo;
        this.userRepository = userRepository;
        this.objectRepository = objectRepository;
    }

    @Override
    public Object invokeACommand(String miniAppName,MiniAppCommandBoundary command) {
    	this.checkInvokedCommand(command);
    	UserId userId = new UserId(command.getInvokedBy().getUserId().getSuperapp(), command.getInvokedBy().getUserId().getEmail());
        return isValidUserCredentials(userId, UserRole.MINIAPP_USER, userRepository)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(MINI_APP_PERMISSION_EXCEPTION));
                    }
                    return objectRepository.findById(command.getTargetObject().getObjectId())
                            .flatMap(object -> {
                                if (!object.getActive()) {
                                    return Mono.error(new IllegalAccessException("Object is not active"));
                                }
                                return Mono.just(object);
                            })
                            .switchIfEmpty(Mono.error(new IllegalAccessException("Object not found")))
                            .flatMap(activeObject -> {
                                String id = UUID.randomUUID().toString();
                                logger.info("Invoking a miniApp command {}", command);
                                command.setInvocationTimestamp(new Date());
                                command.getInvokedBy().getUserId().setSuperapp(environment.getProperty(APPLICATION_NAME));
                                command.getTargetObject().getObjectId().setId(UUID.randomUUID().toString());
                                MiniAppCommandEntity miniAppCommandEntity = command.toEntity(environment.getProperty(APPLICATION_NAME), miniAppName, id);
                                return miniAppCommandsRepository.save(miniAppCommandEntity)
                                        .map(MiniAppCommandEntity::toBoundary)
                                        .doOnNext(miniAppBoundary -> logger.info("MiniAppCommand created: {}", miniAppBoundary));
                            });
                })
                .log();
    }
    
    private void checkInvokedCommand(MiniAppCommandBoundary command) {
    	if(command.getInvokedBy() == null ||
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
