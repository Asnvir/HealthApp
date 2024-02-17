package superapp.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.service.SuperAppObjectService;
import superapp.service.impl.SuperAppObjectServiceImpl;


@RestController
@RequestMapping(path = "${apiPrefix}/objects")
public class SuperAppObjectController {
    private static final Logger logger = LoggerFactory.getLogger(SuperAppObjectServiceImpl.class);
    @Autowired
    private SuperAppObjectService superAppObjectService;



    public SuperAppObjectController(SuperAppObjectService superAppObjectService) {

        this.superAppObjectService = superAppObjectService;

    }

    @PostMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public Mono<SuperAppObjectBoundary> create(
            @RequestBody SuperAppObjectBoundary object) {
        logger.info("Received a request to create a object {}", object);
        return this.superAppObjectService.create(object)
                .doOnSuccess(success -> logger.info("Object created successfully"))
                .doOnError(error -> logger.error("Error creating object"));
    }

    @GetMapping(path = "{superapp}/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<SuperAppObjectBoundary> get(@PathVariable("superapp") String superApp,
                                            @PathVariable("id") String id,
                                            @RequestParam("userSuperapp") String userSuperapp,
                                            @RequestParam("userEmail") String email) {
        logger.info("In controller get method - superApp: {} and ID: {}", superApp, id);
                        return superAppObjectService.get(superApp, id, userSuperapp, email)
                                .doOnSuccess(success -> logger.info("Object details fetched successfully for userId: {}", id))
                                .doOnError(error -> logger.error("Error fetching object details for userId: {}", id));

    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<SuperAppObjectBoundary> getAll(@RequestParam("userSuperapp") String userSuperapp,
                                               @RequestParam("userEmail") String email) {
        logger.info("In controller getAll method");
                        return superAppObjectService.getAll(userSuperapp, email)
                                .doOnNext(success -> logger.info("Objects fetched successfully"))
                                .doOnError(error -> logger.error("Error fetching objects"));
    }

    @PutMapping(
            path = "/{superapp}/{id}",
            consumes = APPLICATION_JSON_VALUE)
    public Mono<Void> updateObject(@PathVariable("superapp") String superApp,
                                   @PathVariable("id") String id,
                                   @RequestBody SuperAppObjectBoundary objectToUpdate,
                                   @RequestParam String userSuperapp,
                                      @RequestParam String userEmail
    ) {
        logger.info("In controller updateObject method - superApp: {}, ID: {}, userToUpdate: {}"
                , superApp, id, objectToUpdate);
        return superAppObjectService.update(superApp, objectToUpdate, id, userSuperapp, userEmail)
                .doOnSuccess(success -> logger.info("Object updated successfully for userId: {}", id))
                .doOnError(error -> logger.error("Error updating object for userId: {}", id));
    }

    @GetMapping("/search/byType/{type}")
    public Flux<SuperAppObjectBoundary> getObjectsByType(@PathVariable("type") String type,
                                                         @RequestParam("userSuperapp") String superApp,
                                                         @RequestParam("userEmail") String email
    ) {

                        return superAppObjectService.getObjectsByType(type, superApp, email)
                                .doOnNext(success -> logger.info("Objects fetched successfully for type: {}", type))
                                .doOnError(error -> logger.error("Error fetching objects for type: {}", type));

    }

    @GetMapping("/search/byAlias/{alias}")
    public Flux<SuperAppObjectBoundary> getObjectsByAlias(@PathVariable("alias") String alias,
                                                          @RequestParam("userSuperapp") String superApp,
                                                          @RequestParam("userEmail") String email
    ) {

                        return superAppObjectService.getObjectsByAlias(alias, superApp, email)
                                .doOnNext(success -> logger.info("Objects fetched successfully for alias: {}", alias))
                                .doOnError(error -> logger.error("Error fetching objects for alias: {}", alias));


    }
    
    @GetMapping("/search/byAliasPattern/{pattern}")
    public Flux<SuperAppObjectBoundary> getObjectsByAliasPattern(@PathVariable("pattern") String pattern,
                                                          @RequestParam("userSuperapp") String superApp,
                                                          @RequestParam("userEmail") String email
    ) {

                        return superAppObjectService.findByAliasContaining(pattern, superApp, email)
                                .doOnNext(success -> logger.info("Objects fetched successfully for alias pattern: {}", pattern))
                                .doOnError(error -> logger.error("Error fetching objects for alias pattern: {}", pattern));


    }

}

