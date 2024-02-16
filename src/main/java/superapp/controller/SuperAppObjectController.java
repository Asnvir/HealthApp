package superapp.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.ObjectBoundary;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.service.ObjectService;
import superapp.service.UserService;
import superapp.service.impl.ObjectServiceImpl;


@RestController
@RequestMapping(path = "${apiPrefix}/objects")
public class SuperAppObjectController {
    private static final Logger logger = LoggerFactory.getLogger(ObjectServiceImpl.class);
    @Autowired
    private ObjectService objectService;
    @Autowired
    private UserService userService;


    public SuperAppObjectController(ObjectService objectService) {

        this.objectService = objectService;

    }

    @PostMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public Mono<ObjectBoundary> create(
            @RequestBody ObjectBoundary object) {
        logger.info("Received a request to create a object {}", object);
        return this.objectService.create(object);
    }

    @GetMapping(path = "{superapp}/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<ObjectBoundary> get(@PathVariable("superapp") String superApp,
                                    @PathVariable("id") String id,
                                    @RequestParam("userSuperapp") String userSuperapp,
                                    @RequestParam("userEmail") String email
                                    ) {
        logger.info("In controller get method - superApp: {} and ID: {}", superApp, id);

        return isSuperAppUser(userSuperapp,email)
                .flatMap(isSuperAppUser -> {
                    if (isSuperAppUser) {
                        return objectService.get(superApp, id)
                                .doOnSuccess(success -> logger.info("Object details fetched successfully for userId: {}", id))
                                .doOnError(error -> logger.error("Error fetching object details for userId: {}", id));
                    } else {
                        return Mono.error(new IllegalAccessException("User does not have superapp rights"));
                    }
                });
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ObjectBoundary> getAll(@RequestParam("userSuperapp") String userSuperapp,
                                       @RequestParam("userEmail") String email) {
        logger.info("In controller getAll method");

        return isSuperAppUser(userSuperapp, email)
                .flatMapMany(hasAccess -> {
                    if (hasAccess) {
                        return objectService.getAll()
                                .doOnNext(success -> logger.info("Objects fetched successfully"))
                                .doOnError(error -> logger.error("Error fetching objects"));
                    } else {
                        return Flux.error(new IllegalAccessException("User does not have superapp rights"));
                    }
                });
    }

    @PutMapping(
            path = "/{superapp}/{id}",
            consumes = APPLICATION_JSON_VALUE)
    public Mono<Void> updateObject(@PathVariable("superapp") String superApp,
                                   @PathVariable("id") String id,
                                   @RequestBody ObjectBoundary objectToUpdate) {
        logger.info("In controller updateObject method - superApp: {}, ID: {}, userToUpdate: {}"
                , superApp, id, objectToUpdate);
        return objectService.update(superApp, objectToUpdate, id);
    }

    @GetMapping("/search/byType/{type}")
    public Flux<ObjectBoundary> getObjectsByType(@PathVariable("type") String type,
                                                 @RequestParam("userSuperapp") String superApp,
                                                 @RequestParam("userEmail") String email
    ) {
        return isSuperAppUser(superApp,email)
                .flatMapMany(hasAccess -> {
                    if (hasAccess) {
                        return objectService.getObjectsByType(type)
                                .doOnNext(success -> logger.info("Objects fetched successfully for type: {}", type))
                                .doOnError(error -> logger.error("Error fetching objects for type: {}", type));
                    } else {
                        return Flux.error(new IllegalAccessException("User does not have superapp rights"));
                    }
                });
    }

    @GetMapping("/search/byAlias/{alias}")
    public Flux<ObjectBoundary> getObjectsByAlias(@PathVariable("alias") String alias,
                                                 @RequestParam("userSuperapp") String superApp,
                                                 @RequestParam("userEmail") String email
    ) {
        return isSuperAppUser(superApp,email)
                .flatMapMany(hasAccess -> {
                    if (hasAccess) {
                        return objectService.getObjectsByAlias(alias)
                                .doOnNext(success -> logger.info("Objects fetched successfully for alias: {}", alias))
                                .doOnError(error -> logger.error("Error fetching objects for alias: {}", alias));
                    } else {
                        return Flux.error(new IllegalAccessException("User does not have superapp rights"));
                    }
                });

    }

    private Mono<Boolean> isSuperAppUser(String superApp, String email) {
        UserId userId = new UserId(superApp, email);
        return userService.getUserById(userId)
                .map(user -> UserRole.SUPERAPP_USER.toString().equals(user.getRole()))
                .onErrorResume(error -> {
                    logger.error("Error fetching user: {}", error.getMessage());
                    return Mono.just(false);
                });
    }
}

