package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.boundary.user.UserBoundary;
import superapp.entity.object.ObjectId;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.repository.MiniAppCommandsRepository;
import superapp.repository.ObjectRepository;
import superapp.service.AdminService;
import superapp.service.UserService;

import static superapp.exception.Consts.ADMIN_PERMISSION_EXCEPTION;


@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final UserService userService;
    private final ObjectRepository objectRepository;
    private final MiniAppCommandsRepository miniAppCommandsRepository;

    public AdminServiceImpl(MiniAppCommandsRepository miniAppCommandsRepository,
                            ObjectRepository objectRepository,
                            UserService userService
    ) {
        this.userService = userService;
        this.miniAppCommandsRepository = miniAppCommandsRepository;
        this.objectRepository = objectRepository;
    }


    @Override
    public Mono<Void> deleteAllUsers(String superApp, String email) {
        logger.info("Deleting all users in AdminServiceImpl");
        return userService.isValidUserCredentials(email, superApp, UserRole.ADMIN)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userService.deleteAllUser();
                });
    }

    @Override
    public Mono<Void> deleteUser(String superApp, String emailAdmin, String deleteUserEmail) {
        logger.info("Deleting user in AdminServiceImpl, userId: " + deleteUserEmail);
        return userService.isValidUserCredentials(emailAdmin, superApp, UserRole.ADMIN)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userService.deleteUser(superApp, deleteUserEmail).log();
                });
    }

    @Override
    public Mono<UserBoundary> exportUser(String userId, String superApp, String email) {
        logger.info("Exporting user in AdminServiceImpl, userId: " + userId);
        return userService.isValidUserCredentials(email, superApp, UserRole.ADMIN)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userService.getUserById(new UserId(superApp, userId))
                            .log();
                });
    }



    @Override
    public Mono<Void> deleteObject(String superApp, String email, String objectId) {
        logger.info("Deleting object in AdminServiceImpl, objectId: " + objectId);
        return userService.isValidUserCredentials(email,superApp, UserRole.ADMIN)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return objectRepository.deleteById(new ObjectId(superApp,objectId)).log();
                });
    }

    @Override
    public Mono<Void> deleteAllObjects(String superApp, String email) {
        logger.info("Deleting all objects in AdminServiceImpl");
        return userService.isValidUserCredentials(email,superApp, UserRole.ADMIN)
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
        return userService.isValidUserCredentials(email,superApp, UserRole.ADMIN)
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
        return userService.isValidUserCredentials(email,superApp, UserRole.ADMIN)
                .flatMapMany(isValid -> {
                    if (!isValid) {
                        return Flux.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return userService.getAllUsers();
                });
    }

    @Override
    public Flux<MiniAppCommandBoundary> exportAllMiniAppsCommandsHistory(String superApp, String email) {
        logger.info("Exporting all Miniapps commands history");
        return userService.isValidUserCredentials(email,superApp, UserRole.ADMIN)
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
        return userService.isValidUserCredentials(email,superApp, UserRole.ADMIN)
                .flatMapMany(isValid -> {
                    if (!isValid) {
                        return Flux.error(new IllegalAccessException(ADMIN_PERMISSION_EXCEPTION));
                    }
                    return miniAppCommandsRepository.findByCommandIdMiniApp(miniAppName).map(MiniAppCommandBoundary::new).log();
                });
    }


}
