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
import superapp.entity.object.SuperAppObjectEntity;
import superapp.entity.object.ObjectId;
import superapp.entity.user.UserRole;
import superapp.exception.InvalidInputException;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.service.SuperAppObjectService;
import superapp.service.UserService;
import superapp.utils.EmailChecker;
import superapp.utils.Validator;
import static superapp.common.Consts.APPLICATION_NAME;
import static superapp.exception.Consts.SUPER_APP_PERMISSION_EXCEPTION;

@Service
public class SuperAppObjectServiceImpl implements SuperAppObjectService {
    private static final Logger logger = LoggerFactory.getLogger(SuperAppObjectServiceImpl.class);
    private final ObjectRepository objectRep;

    private final UserService userService;

    @Autowired
    private Environment environment;

    public SuperAppObjectServiceImpl(ObjectRepository objectRep,
                                     UserService userService
    ) {
        super();
        this.objectRep = objectRep;
        this.userService = userService;
    }

    @Override
    public Mono<SuperAppObjectBoundary> create(SuperAppObjectBoundary object) {
        logger.info("Creating object {}", object);
        validateObject(object);
        object.setObjectId(new SuperAppObjectIdBoundary(environment.getProperty(APPLICATION_NAME), UUID.randomUUID().toString()));
        object.setCreationTimestamp(new Date());
        return this.objectRep
                .save(object.toEntity())
                .map(SuperAppObjectBoundary::new)
                .log();
    }

    @Override
    public Mono<Void> update(String superApp, SuperAppObjectBoundary objectToUpdate, String id, String userSuperapp, String email) {
        ObjectId objectId = new ObjectId(superApp, id);
        validateObject(objectToUpdate);

        return userService.isValidUserCredentials(email,superApp, UserRole.SUPERAPP_USER)
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
        superAppObjectEntity.setObjectDetails(objectToUpdate.getObjectDetails());
        superAppObjectEntity.setAlias(objectToUpdate.getAlias());
        superAppObjectEntity.setActive(objectToUpdate.getActive());
        return Mono.just(superAppObjectEntity).log();
    }

    @Override
    public Mono<SuperAppObjectBoundary> get(String superapp, String id, String userSuperapp, String email) {
        ObjectId objectId = new ObjectId(superapp, id);
        return userService.isValidUserCredentials(email,userSuperapp, UserRole.SUPERAPP_USER)
                .flatMap(isSuperAppUser -> {
                    if (Boolean.TRUE.equals(isSuperAppUser)) {
                        return this.objectRep.findById(objectId)
                                .map(SuperAppObjectBoundary::new)
                                .log();
                    } else {
                        return userService.isValidUserCredentials(email,userSuperapp, UserRole.MINIAPP_USER)
                                .flatMap(isMiniAppUser -> {
                                    if (Boolean.TRUE.equals(isMiniAppUser)) {
                                        return this.objectRep.findById(objectId)
                                                .filter(SuperAppObjectEntity::getActive)
                                                .map(SuperAppObjectBoundary::new)
                                                .log()
                                                .switchIfEmpty(Mono.error(new IllegalAccessException("Object is not active or does not exist")));
                                    } else {
                                        return Mono.error(new IllegalAccessException("Permission Denied"));
                                    }
                                });
                    }
                });
    }

    @Override
    public Flux<SuperAppObjectBoundary> getAll(String superapp, String email) {
        Flux<SuperAppObjectBoundary> objects = this.objectRep.findAll().map(SuperAppObjectBoundary::new).log();
        return filterObjectsBasedOnUserRole(email,superapp, objects);
    }

    @Override
    public Flux<SuperAppObjectBoundary> getObjectsByType(String type, String superApp, String email) {
    	checkValidationBeforeGetCommands(type,superApp,email);
        Flux<SuperAppObjectBoundary> objects = this.objectRep.findByType(type)
                .map(SuperAppObjectBoundary::new)
                .log();

        return filterObjectsBasedOnUserRole(email,superApp, objects);
    }

    @Override
    public Flux<SuperAppObjectBoundary> getObjectsByAlias(String alias, String superApp, String email) {
    	checkValidationBeforeGetCommands(alias,superApp,email);
        Flux<SuperAppObjectBoundary> objects = this.objectRep.findByAlias(alias)
                .map(SuperAppObjectBoundary::new)
                .log();

        return filterObjectsBasedOnUserRole(email,superApp, objects);
    }

    private Flux<SuperAppObjectBoundary> filterObjectsBasedOnUserRole(String email,String superApp, Flux<SuperAppObjectBoundary> objects) {
        Mono<UserRole> userRoleMono = userService.isValidUserCredentials(email,superApp, UserRole.SUPERAPP_USER)
                .flatMap(isSuperAppUser -> isSuperAppUser ? Mono.just(UserRole.SUPERAPP_USER) : Mono.empty())
                .switchIfEmpty(
                        userService.isValidUserCredentials(email,superApp, UserRole.MINIAPP_USER)
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
                superAppObjectBoundary.getCreatedBy().getUserId().getSuperapp() == null
        ) {

            {
                throw new IllegalArgumentException("Object createdBy fields is missing");
            }
        }
        if (!Validator.isValidEmail(superAppObjectBoundary.getCreatedBy().getUserId().getEmail())) {
            throw new IllegalArgumentException("Invalid createdBy user email");
        }
    }
    
    private void checkValidationBeforeGetCommands(String type, String superApp, String email) {
    	if(type == null || superApp == null || email == null)
    		throw new InvalidInputException("One or more of the inputs are null.");
    	if(!EmailChecker.isValidEmail(email))
    		throw new InvalidInputException("Invalid user details.");
    }
	
	@Override
	public Flux<SuperAppObjectBoundary> findByAliasContaining(String pattern, String superApp, String email) {
	    checkValidationBeforeGetCommands(pattern, superApp, email);
	    Flux<SuperAppObjectBoundary> objects = this.objectRep.findByAliasRegex(".*" + pattern + ".*")
	            .map(SuperAppObjectBoundary::new)
	            .log();
	    
	    return filterObjectsBasedOnUserRole(email,superApp, objects);
	}

}