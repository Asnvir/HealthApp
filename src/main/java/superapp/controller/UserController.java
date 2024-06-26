package superapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import superapp.boundary.user.NewUserBoundary;
import superapp.boundary.user.UserBoundary;
import superapp.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "${apiPrefix}/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000") // Replace with the origin of the React app
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Mono<UserBoundary> createNewUser(@RequestBody NewUserBoundary user) {
        logger.info("Received a request to create a new user {}", user);
        return userService.create(user);
    }
    @CrossOrigin(origins = "http://localhost:3000") // Replace with the origin of the React app
    @GetMapping(path = "/login/{superapp}/{email}", produces = APPLICATION_JSON_VALUE)
    public Mono<UserBoundary> login(@PathVariable("superapp") String superApp,
                                    @PathVariable("email") String email) {
        logger.info("In controller login method - superApp: {} and email: {}", superApp, email);

        return userService.login(superApp, email);
    }

    @PutMapping(path = "/{superapp}/{userEmail}", consumes = APPLICATION_JSON_VALUE)
    public Mono<Void> updateUserDetails(@PathVariable("superapp") String superApp,
                                        @PathVariable("userEmail") String userEmail,
                                        @RequestBody UserBoundary userToUpdate) {
        logger.info("In controller updateUserDetails method - superApp: {}, userEmail: {}, userToUpdate: {}"
                , superApp, userEmail, userToUpdate);
        return userService.updateUserDetails(superApp, userEmail, userToUpdate);
    }

}
