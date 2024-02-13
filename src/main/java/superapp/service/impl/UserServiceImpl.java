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


import java.util.Arrays;
import java.util.regex.Pattern;


import static ch.qos.logback.core.util.OptionHelper.isNullOrEmpty;
import static superapp.common.Consts.APPLICATION_NAME;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment environment;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Mono<UserBoundary> create(@RequestBody NewUserBoundary user) {
        logger.info("Creating user {}", user);
        validateUser(user);
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
                .doOnSuccess(unused -> logger.info("User details updated for userId: {}", userId))
                .log()
                .then();
    }

    @Override
    public Mono<UserBoundary> getUserById(UserId userId) {

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("User with %s not found", userId))))
                .map(UserBoundary::new)
                .doOnNext(userBoundary -> logger.info("User found: {}", userBoundary))
                .log();
    }

    private Mono<UserEntity> updateUserEntity(UserEntity userEntity, UserBoundary userToUpdate) {
        if(userToUpdate.getAvatar() != null){
            userEntity.setAvatar(userToUpdate.getAvatar());
        }
        if(userToUpdate.getUsername() != null){
            userEntity.setUserName(userToUpdate.getUsername());
        }
        return Mono.just(userEntity).log();
    }

    private void validateUser(NewUserBoundary userBoundary) {
        if (!isValidEmail(userBoundary.getEmail())) {
            throw new IllegalArgumentException(userBoundary.getEmail() + " is invalid email address");
        }
        if (isNullOrEmpty(userBoundary.getUsername())) {
            throw new  IllegalArgumentException("Username must be provided and not be empty");
        }
        if (!isValidUserRole(userBoundary.getRole())) {
            throw new IllegalArgumentException(userBoundary.getRole() + " is invalid user role");
        }
        if(isNullOrEmpty(userBoundary.getAvatar())){
            throw new IllegalArgumentException("Avatar must be provided and not be empty");
        }

    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }




    private boolean isValidUserRole(String role) {
        return Arrays.asList("MINIAPP_USER", "SUPERAPP_USER", "ADMIN").contains(role);
    }
}
