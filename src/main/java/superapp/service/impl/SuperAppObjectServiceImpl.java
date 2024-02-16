package superapp.service.impl;

import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.boundary.object.SuperAppObjectIdBoundary;
import superapp.convertors.SuperAppObjectConverter;
import superapp.entity.object.SuperAppObjectEntity;
import superapp.entity.object.ObjectId;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.repository.UserRepository;
import superapp.service.AbstractService;
import superapp.service.SuperAppObjectService;
import superapp.utils.Validator;
import static superapp.common.Consts.APPLICATION_NAME;
import static superapp.exception.Consts.SUPER_APP_PERMISSION_EXCEPTION;

@Service
public class SuperAppObjectServiceImpl extends AbstractService implements SuperAppObjectService {
    private static final Logger logger = LoggerFactory.getLogger(SuperAppObjectServiceImpl.class);
    private final ObjectRepository objectRep;

    private  final  UserRepository userRepository;

    private  final SuperAppObjectConverter superAppObjectConverter;

    @Autowired
    private Environment environment;

    public SuperAppObjectServiceImpl(ObjectRepository objectRep, UserRepository userRepository, SuperAppObjectConverter superAppObjectConverter) {
        super();
        this.objectRep = objectRep;
        this.userRepository = userRepository;
        this.superAppObjectConverter = superAppObjectConverter;
    }

    @Override
    public Mono<SuperAppObjectBoundary> create(SuperAppObjectBoundary object) {
        logger.info("Creating object {}", object);
        validateObject(object);
        object.setObjectId(new SuperAppObjectIdBoundary(environment.getProperty(APPLICATION_NAME), UUID.randomUUID().toString()));
        object.setCreationTimestamp(new Date());
        return this.objectRep
                .save(superAppObjectConverter.toEntity(object))
                .map(superAppObjectConverter::toBoundary)
                .log();
    }

    @Override
    public Mono<Void> update(String superApp, SuperAppObjectBoundary objectToUpdate, String id, String userSuperapp, String email) {
        UserId userId = new UserId(userSuperapp, email);
        ObjectId objectId = new ObjectId(superApp, id);
        validateObject(objectToUpdate); // Assuming this is a synchronous validation method

        return isValidUserCredentials(userId, UserRole.SUPERAPP_USER, userRepository)
                .flatMap(isValid -> {
                    if (!Boolean.TRUE.equals(isValid)) {
                        return Mono.error(new IllegalAccessException(SUPER_APP_PERMISSION_EXCEPTION));
                    }
                    return this.objectRep.findById(objectId);
                })
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Object with %s not found", objectId))))
                .flatMap(entity -> updateObjectEntity(entity, objectToUpdate))
                .flatMap(this.objectRep::save)
                .doOnSuccess(unused -> logger.info("Object details updated for userId: {}", objectId))
                .log()
                .then();
    }

    private Mono<SuperAppObjectEntity> updateObjectEntity(SuperAppObjectEntity superAppObjectEntity,
                                                          SuperAppObjectBoundary objectToUpdate) {
        superAppObjectEntity.setType(objectToUpdate.getType());
        superAppObjectEntity.setObjectDetails(objectToUpdate.getObjectDetails().toString());
        superAppObjectEntity.setAlias(objectToUpdate.getAlias());
        superAppObjectEntity.setActive(objectToUpdate.getActive());
        return Mono.just(superAppObjectEntity).log();
    }

    @Override
    public Mono<SuperAppObjectBoundary> get(String superapp, String id, String userSuperapp, String email) {
        ObjectId objectId = new ObjectId(superapp, id);
        UserId userId = new UserId(userSuperapp, email);
        return isValidUserCredentials(userId, UserRole.SUPERAPP_USER, userRepository)
                .flatMap(isSuperAppUser -> {
                    if (Boolean.TRUE.equals(isSuperAppUser)) {
                        // If the user is a SUPERAPP_USER, return the object directly
                        return this.objectRep.findById(objectId)
                                .map(superAppObjectConverter::toBoundary)
                                .log();
                    } else {
                        // Check if the user is a MINIAPP_USER
                        return isValidUserCredentials(userId, UserRole.MINIAPP_USER, userRepository)
                                .flatMap(isMiniAppUser -> {
                                    if (Boolean.TRUE.equals(isMiniAppUser)) {
                                        // If the user is a MINIAPP_USER, check if the object is active
                                        return this.objectRep.findById(objectId)
                                                .filter(SuperAppObjectEntity::getActive)
                                                .map(superAppObjectConverter::toBoundary)
                                                .log()
                                                .switchIfEmpty(Mono.error(new IllegalAccessException("Object is not active or does not exist")));
                                    } else {
                                        // If the user is neither, deny access
                                        return Mono.error(new IllegalAccessException("Permission Denied"));
                                    }
                                });
                    }
                });
    }

    @Override
    public Flux<SuperAppObjectBoundary> getAll(String superapp, String email) {
        UserId userId = new UserId(superapp, email);
        Flux<SuperAppObjectBoundary> objects = this.objectRep.findAll().map(superAppObjectConverter::toBoundary).log();
        return filterObjectsBasedOnUserRole(userId, objects);
    }

    @Override
    public Flux<SuperAppObjectBoundary> getObjectsByType(String type, String superApp, String email) {
        UserId userId = new UserId(superApp, email);
        Flux<SuperAppObjectBoundary> objects = this.objectRep.findByType(type)
                .map(superAppObjectConverter::toBoundary)
                .log();

        return filterObjectsBasedOnUserRole(userId, objects);
    }

    @Override
    public Flux<SuperAppObjectBoundary> getObjectsByAlias(String alias, String superApp, String email) {
        UserId userId = new UserId(superApp, email);
        Flux<SuperAppObjectBoundary> objects = this.objectRep.findByAlias(alias)
                .map(superAppObjectConverter::toBoundary)
                .log();

        return filterObjectsBasedOnUserRole(userId, objects);
    }

    private Flux<SuperAppObjectBoundary> filterObjectsBasedOnUserRole(UserId userId, Flux<SuperAppObjectBoundary> objects) {
        Mono<UserRole> userRoleMono = isValidUserCredentials(userId, UserRole.SUPERAPP_USER, userRepository)
                .flatMap(isSuperAppUser -> isSuperAppUser ? Mono.just(UserRole.SUPERAPP_USER) : Mono.empty())
                .switchIfEmpty(
                        isValidUserCredentials(userId, UserRole.MINIAPP_USER, userRepository)
                                .flatMap(isMiniAppUser -> isMiniAppUser ? Mono.just(UserRole.MINIAPP_USER) : Mono.empty())
                );

        return userRoleMono.flatMapMany(role -> {
            if (UserRole.SUPERAPP_USER.equals(role)) {
                return objects;
            } else if (UserRole.MINIAPP_USER.equals(role)) {
                return objects.filter(SuperAppObjectBoundary::getActive);
            } else {
                return Flux.error(new IllegalAccessException(SUPER_APP_PERMISSION_EXCEPTION));
            }
        });
    }


    private void validateObject(SuperAppObjectBoundary superAppObjectBoundary) {
        if (Validator.isNullOrEmpty(superAppObjectBoundary.getType())) {
            throw new IllegalArgumentException("Object type is required");
        }
        if (Validator.isNullOrEmpty(superAppObjectBoundary.getAlias())) {
            throw new IllegalArgumentException("Object alias is required");
        }
        if (superAppObjectBoundary.getObjectDetails() == null) {
            throw new IllegalArgumentException("Object details is required");
        }
        if (superAppObjectBoundary.getCreatedBy() == null ||
                superAppObjectBoundary.getCreatedBy().getUserId() == null ||
                superAppObjectBoundary.getCreatedBy().getUserId().getEmail() == null ||
                superAppObjectBoundary.getCreatedBy().getUserId().getSuperapp() == null ||
                !Validator.isValidEmail(superAppObjectBoundary.getCreatedBy().getUserId().getEmail())
        ) {

            {
                throw new IllegalArgumentException("Object createdBy is required");
            }
        }
    }

}