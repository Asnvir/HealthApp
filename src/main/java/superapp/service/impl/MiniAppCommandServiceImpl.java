package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import superapp.boundary.MiniAppCommandBoundary;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.repository.MiniAppCommandsRepository;
import superapp.service.MiniAppCommandService;

import static superapp.common.Consts.APPLICATION_NAME;

import java.util.Date;
import java.util.UUID;

@Service
public class MiniAppCommandServiceImpl implements MiniAppCommandService {

    private static final Logger logger = LoggerFactory.getLogger(MiniAppCommandServiceImpl.class);
    @Autowired
    private MiniAppCommandsRepository miniAppCommandsRepository;
    @Autowired
    private Environment environment;

    public MiniAppCommandServiceImpl(MiniAppCommandsRepository miniAppRepo) {
        super();
        this.miniAppCommandsRepository = miniAppRepo;
    }

    @Override
    public Mono<MiniAppCommandBoundary> invokeACommand(String miniAppName,MiniAppCommandBoundary command) {
        String id = UUID.randomUUID().toString();
        logger.info("Invoking a miniApp command {}", command);
        command.setInvocationTimestamp(new Date()); // Create new date
        command.getInvokedBy().getUserId().setSuperapp(environment.getProperty(APPLICATION_NAME)); // update superapp name
        command.getInvokedBy().getUserId().setEmail(command.getInvokedBy().getUserId().getEmail()); // get main from user
        command.getTargetObject().getObjectId().setSuperapp(environment.getProperty(APPLICATION_NAME));
        command.getTargetObject().getObjectId().setId(UUID.randomUUID().toString());
        MiniAppCommandEntity miniAppCommandEntity = command.toEntity(environment.getProperty(APPLICATION_NAME),miniAppName,id);
        return miniAppCommandsRepository.save(miniAppCommandEntity)
        		.map(MiniAppCommandEntity::toBoundary)
                .doOnNext(miniAppBoundary -> logger.info("MiniAppCommand created: {}", miniAppBoundary))
                .log();
    }
}
