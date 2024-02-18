package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.boundary.user.UserBoundary;


import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.repository.MiniAppCommandsRepository;
import superapp.repository.ObjectRepository;
import superapp.repository.UserRepository;
import superapp.service.AbstractService;
import superapp.service.AdminService;

import static superapp.exception.Consts.ADMIN_PERMISSION_EXCEPTION;


@Service
public class AdminServiceImpl extends AbstractService implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final UserRepository userRepository;
    private final ObjectRepository objectRepository;
    private final MiniAppCommandsRepository miniAppCommandsRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            MiniAppCommandsRepository miniAppCommandsRepository,
                            ObjectRepository objectRepository
    ) {
        this.userRepository = userRepository;
        this.miniAppCommandsRepository = miniAppCommandsRepository;
        this.objectRepository = objectRepository;
    }


    @Override
    public Mono<Void> deleteAllUsers(String superApp, String email) {
        logger.info("Deleting all users in AdminServiceImpl");
        UserId userId = new UserId(superApp, email);
        return isValidUserCredentials(userId, UserRole.ADMIN, userRepository)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userRepository.deleteAll().log();
                });
    }

    @Override
    public Mono<Void> deleteUser(String superApp, String emailAdmin, String deleteUserEmail) {
        logger.info("Deleting user in AdminServiceImpl, userId: " + deleteUserEmail);
        UserId adminId = new UserId(superApp, emailAdmin);
        return isValidUserCredentials(adminId, UserRole.ADMIN, userRepository)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userRepository.deleteByUserId(new UserId(superApp, deleteUserEmail)).log();
                });
    }

    @Override
    public Mono<UserBoundary> exportUser(String userId, String superApp, String email) {
        logger.info("Exporting user in AdminServiceImpl, userId: " + userId);
        UserId adminId = new UserId(superApp, email);
        return isValidUserCredentials(adminId, UserRole.ADMIN, userRepository)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userRepository
                            .findByUserId(new UserId(superApp, userId))
                            .map(UserBoundary::new)
                            .log();
                });
    }

    @Override
    public Mono<Boolean> hasUsers(String superApp, String email) {
        logger.info("Checking if there are users in AdminServiceImpl");
        UserId userId = new UserId(superApp, email);
        return isValidUserCredentials(userId, UserRole.ADMIN, userRepository)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userRepository.count()
                            .map(count -> count > 0)
                            .log();
                });
    }

    @Override
    public Mono<Void> deleteAllObjects(String superApp, String email) {
        logger.info("Deleting all objects in AdminServiceImpl");
        UserId userId = new UserId(superApp, email);
        return isValidUserCredentials(userId, UserRole.ADMIN, userRepository)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return objectRepository.deleteAll().log();
                });
    }

    @Override
    public Mono<Void> deleteAllCommandsHistory(String superApp, String email) {
        logger.info("Deleting all commands history in AdminServiceImpl");
        UserId userId = new UserId(superApp, email);
        return isValidUserCredentials(userId, UserRole.ADMIN, userRepository)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return miniAppCommandsRepository.deleteAll().log();
                });
    }

    @Override
    public Flux<UserBoundary> exportAllUsers(String superApp, String email) {
        logger.info("Exporting all users");
        UserId userId = new UserId(superApp, email);
        return isValidUserCredentials(userId, UserRole.ADMIN, userRepository)
                .flatMapMany(isValid -> {
                    if (!isValid) {
                        return Flux.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userRepository.findAll().map(UserBoundary::new).log();
                });
    }

    @Override
    public Flux<MiniAppCommandBoundary> exportAllMiniAppsCommandsHistory(String superApp, String email) {
        logger.info("Exporting all Miniapps commands history");
        UserId userId = new UserId(superApp, email);
        return isValidUserCredentials(userId, UserRole.ADMIN, userRepository)
                .flatMapMany(isValid -> {
                    if (!isValid) {
                        return Flux.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return miniAppCommandsRepository.findAll().map(MiniAppCommandBoundary::new).log();
                });
    }


    @Override
    public Flux<MiniAppCommandBoundary> exportMiniAppCommandsHistory(String miniAppName, String superApp, String email) {
        logger.info("Exporting history of miniapp: " + miniAppName);
        UserId userId = new UserId(superApp, email);
        return isValidUserCredentials(userId, UserRole.ADMIN, userRepository)
                .flatMapMany(isValid -> {
                    if (!Boolean.TRUE.equals(isValid)) {
                        return Flux.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return miniAppCommandsRepository.findByCommandIdMiniApp(miniAppName).map(MiniAppCommandBoundary::new).log();
                });
    }


}
