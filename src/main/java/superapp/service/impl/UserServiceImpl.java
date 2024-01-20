package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import superapp.boundary.NewUserBoundary;
import superapp.boundary.UserBoundary;
import superapp.entity.user.UserId;
import superapp.entity.user.UserEntity;
import superapp.exception.NotFoundException;
import superapp.repository.UserRepository;
import superapp.service.UserService;


import static superapp.common.Consts.APPLICATION_NAME;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment environment;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }


    @Override
    public Mono<UserBoundary> create(@RequestBody NewUserBoundary user) {
        logger.info("Creating user {}", user);
        UserEntity userEntity = user.toEntity(environment.getProperty(APPLICATION_NAME));
        return userRepository.save(userEntity)
                .map(UserBoundary::new)
                .doOnNext(userBoundary -> logger.info("User created: {}", userBoundary))
                .log();
    }

    @Override
    public Mono<UserBoundary> login(String userSuperApp, String userEmail) {
        UserId userId = new UserId(userSuperApp, userEmail);
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("User with %s not found", userId))))
                .map(UserBoundary::new)
                .doOnNext(userBoundary -> logger.info("User logged in: {}", userBoundary))
                .log();
    }

    @Override
    public Mono<Void> updateUserDetails(String userSuperApp, String userEmail, UserBoundary userToUpdate) {
        UserId userId = new UserId(userSuperApp, userEmail);

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("User with %s not found", userId))))
                .flatMap(userEntity -> updateUserEntity(userEntity, userToUpdate))
                .flatMap(userRepository::save)
                .doOnNext(unused -> logger.info("User details updated for userId: {}", userId))
                .log()
                .then();
    }

    private Mono<UserEntity> updateUserEntity(UserEntity userEntity, UserBoundary userToUpdate) {
        userEntity.setAvatar(userToUpdate.getAvatar());
        userEntity.setUserName(userToUpdate.getUserName());
        return Mono.just(userEntity);
    }

}
