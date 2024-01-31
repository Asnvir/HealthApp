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
import superapp.entity.command.MiniAppCommandEntity;
import superapp.repository.MiniAppCommandsRepository;
import superapp.service.MiniAppCommandService;

import static superapp.common.Consts.APPLICATION_NAME;

import java.util.Date;

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
        MiniAppCommandEntity miniAppCommandEntity = command.toEntity(environment.getProperty(APPLICATION_NAME));
        return miniAppCommandsRepository.save(miniAppCommandEntity)
        		.map(savedEntity -> savedEntity.toBoundary())
                .doOnNext(miniAppBoundary -> logger.info("MiniAppCommand created: {}", miniAppBoundary))
                .log();
    }
}
