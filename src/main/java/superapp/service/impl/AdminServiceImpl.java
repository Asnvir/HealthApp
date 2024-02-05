package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.MiniAppCommandBoundary;
import superapp.boundary.UserBoundary;
import superapp.entity.command.CommandId;
import superapp.entity.user.UserId;
import superapp.repository.MiniAppCommandsRepository;
import superapp.repository.UserRepository;
import superapp.service.AdminService;

import static superapp.common.Consts.APPLICATION_NAME;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final UserRepository userRepository;
    private final MiniAppCommandsRepository miniAppCommandsRepository;
    //private final ObjectsRepository objectsRepository;
    @Autowired
    private Environment environment;

    public AdminServiceImpl(UserRepository userRepository, MiniAppCommandsRepository miniAppCommandsRepository) {
        this.userRepository = userRepository;
        this.miniAppCommandsRepository = miniAppCommandsRepository;
        //this.objectsRepository = objectsRepository;
    }


    @Override
    public Mono<Void> deleteAllUsers() {
        logger.info("Deleting all users in AdminServiceImpl");
        return userRepository.deleteAll();
    }

    /*@Override
    public Mono<Void> deleteAllObjects() {
        logger.info("Deleting all objects in AdminServiceImpl");
        return objectsRepository.deleteAll();
    }*/

    @Override
    public Mono<Void> deleteAllCommandsHistory() {
        logger.info("Deleting all commands history in AdminServiceImpl");
        return miniAppCommandsRepository.deleteAll();
    }

    @Override
    public Flux<UserBoundary> exportAllUsers() {
        logger.info("Exporting all users");
        return userRepository.findAll().map(UserBoundary::new);
    }

    @Override
    public Flux<MiniAppCommandBoundary> exportAllMiniAppsCommandsHistory() {
        logger.info("Exporting all Miniapps commands history");
        return miniAppCommandsRepository.findAll().map(MiniAppCommandBoundary::new);
    }

    @Override
    public Flux<MiniAppCommandBoundary> exportMiniAppCommandsHistory(String miniAppName) {
        logger.info("Exporting history of miniapp: " + miniAppName);
        return miniAppCommandsRepository.findAllByMiniApp(miniAppName).map(MiniAppCommandBoundary:: new);
    }
}
