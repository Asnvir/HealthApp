package superapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import superapp.boundary.MiniAppCommandBoundary;
import superapp.entity.command.CommandId;
import superapp.entity.user.UserId;
import superapp.service.MiniAppCommandService;
import superapp.service.impl.MiniAppCommandImpl;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MiniAppCommandsController {
    private static final Logger logger = LoggerFactory.getLogger(MiniAppCommandServiceImpl.class);

    @Autowired
    private MiniAppCommandImpl miniAppCommandService;
    
	@RequestMapping(path = { "/superapp/miniapp/{miniAppName}" }, method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE })
	public Mono<MiniAppCommandBoundary> invokeMiniAppCommand(@RequestBody MiniAppCommandBoundary miniAppCommand,
	{
		logger.info("Received a request to invoke a new command {}", miniAppCommand);
		return miniAppCommandService.invokeCommand(miniAppCommand);
	}
}
