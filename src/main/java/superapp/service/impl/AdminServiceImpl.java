package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.UserBoundary;
import superapp.repository.UserRepository;
import superapp.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> deleteAllUsers() {
        logger.info("Deleting all users in AdminServiceImpl");
        return userRepository.deleteAll();
    }

    @Override
    public Mono<Void> deleteAllObjects() {
        //TODO: Implement logic to delete all objects
        return Mono.empty(); // Placeholder
    }

    @Override
    public Mono<Void> deleteAllCommandsHistory() {
        // TODO: Implement logic to delete all commands history
        return Mono.empty(); // Placeholder
    }

    @Override
    public Flux<UserBoundary> exportAllUsers() {
        logger.info("Exporting all users");
        return userRepository.findAll().map(UserBoundary:: new);
    }
}
