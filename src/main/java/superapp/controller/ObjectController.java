package superapp.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.ObjectBoundary;
import superapp.service.ObjectService;
import superapp.service.impl.ObjectServiceImpl;


@RestController
@RequestMapping(path = "/objects")
public class ObjectController {
	private static final Logger logger = LoggerFactory.getLogger(ObjectServiceImpl.class);
    @Autowired
    private ObjectService objectService;

    
	public ObjectController(ObjectService objectService) {
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
                                    @PathVariable("id") String id) {
        logger.info("In controller get method - superApp: {} and ID: {}", superApp, id);

        return objectService.get(superApp, id);
    }
    
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ObjectBoundary> getAll() {
        logger.info("In controller getAll method");

        return objectService.getAll();
    }

    @PutMapping(
		path = "/{superapp}/{id}",
		consumes = APPLICATION_JSON_VALUE)
    public Mono<Void> updateObject(@PathVariable("superapp") String superApp,
                                        @PathVariable("id") String id,
                                        @RequestBody ObjectBoundary objectToUpdate) {
    	logger.info("In controller updateObject method - superApp: {}, ID: {}, userToUpdate: {}"
                ,superApp,id, objectToUpdate);
        return objectService.update(superApp, objectToUpdate, id);
    }
}

