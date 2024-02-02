package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Mono;
import superapp.boundary.MiniAppCommandBoundary;
import superapp.entity.command.CommandId;
import superapp.entity.command.InvokedBy;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.command.ObjectId;
import superapp.entity.command.TargetObject;
import superapp.entity.user.UserId;
import superapp.repository.MiniAppCommandsRepository;
import superapp.service.MiniAppCommandService;

import static superapp.common.Consts.APPLICATION_NAME;

import java.util.Date;
import java.util.UUID;

@Service
public class MiniAppCommandImpl implements MiniAppCommandService {

    private static final Logger logger = LoggerFactory.getLogger(MiniAppCommandImpl.class);
    @Autowired
    private MiniAppCommandsRepository miniAppCommandsRepository;
    @Autowired
    private Environment environment;

    public MiniAppCommandImpl(MiniAppCommandsRepository miniAppRepo) {
        super();
        this.miniAppCommandsRepository = miniAppRepo;
    }

    @Override
    public Mono<MiniAppCommandBoundary> invokeACommand(@RequestBody MiniAppCommandBoundary command) {
        logger.info("Invoking a miniApp command {}", command);
        command.setCommandId(new CommandId(environment.getProperty(APPLICATION_NAME), "dummyApp")); // set the command ID with creation of new ID
        command.setInvocationTimestamp(new Date()); // Create new date
        command.getInvokedBy().getUserId().setSuperapp(environment.getProperty(APPLICATION_NAME)); // update superapp name
        command.getInvokedBy().getUserId().setEmail(command.getInvokedBy().getUserId().getEmail()); // get main from user
        command.getTargetObject().getObjectId().setSuperapp(environment.getProperty(APPLICATION_NAME));
        command.getTargetObject().getObjectId().setID(UUID.randomUUID().toString());
        MiniAppCommandEntity miniAppCommandEntity = command.toEntity(environment.getProperty(APPLICATION_NAME));
        return miniAppCommandsRepository.save(miniAppCommandEntity)
        		.map(savedEntity -> savedEntity.toBoundary())
                .doOnNext(miniAppBoundary -> logger.info("MiniAppCommand created: {}", miniAppBoundary))
                .log();
    }
}
