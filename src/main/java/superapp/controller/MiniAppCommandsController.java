package superapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import superapp.boundary.MiniAppCommandBoundary;
import superapp.service.impl.MiniAppCommandImpl;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/miniapp/{miniAppName}")
public class MiniAppCommandsController {
    private static final Logger logger = LoggerFactory.getLogger(MiniAppCommandsController.class);

    @Autowired
    private MiniAppCommandImpl miniAppCommandService;
    
	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Mono<MiniAppCommandBoundary> invokeMiniAppCommand(
			@PathVariable String miniAppName,
			@RequestBody MiniAppCommandBoundary newMiniAppCommand)
	{
		logger.info("Received a request to invoke a new command {}", newMiniAppCommand);
		return miniAppCommandService.invokeACommand(miniAppName,newMiniAppCommand);
	}
}
