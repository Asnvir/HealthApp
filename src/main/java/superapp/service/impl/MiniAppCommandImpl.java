package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import superapp.boundary.MiniAppCommandBoundary;
import superapp.entity.user.UserId;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.exception.NotFoundException;
import superapp.repository.MiniAppCommandsRepository;
import superapp.service.MiniAppCommandsService;


import static superapp.common.Consts.APPLICATION_NAME;

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
    public Mono<MiniAppCommandBoundary> create(@RequestBody MiniAppCommandBoundary miniApp) {
        logger.info("Invoking a miniApp command {}", miniApp);
        MiniAppCommandEntity miniAppCommandEntity = miniAppCommandEntity.toEntity(environment.getProperty(APPLICATION_NAME));
        return miniAppCommandsRepository.save(userEntity)
                .map(MiniAppCommandBoundary::new)
                .doOnNext(miniAppBoundary -> logger.info("MiniAppCommand created: {}", miniAppBoundary))
                .log();
    }
}
