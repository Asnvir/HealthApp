package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.user.NewUserBoundary;
import superapp.boundary.user.UserBoundary;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;


public interface UserService {
    Mono<UserBoundary> create(NewUserBoundary user);

    Mono<UserBoundary> login(String userSuperApp, String email);

    Mono<Void> updateUserDetails(String userSuperApp, String userEmail, UserBoundary userToUpdate);

    Mono<UserBoundary> getUserById(UserId userId);

    Mono<Void> deleteUser(String superApp, String userEmail);

    Mono<Void> deleteAllUser();

    Mono<Boolean> isValidUserCredentials(String email, String superApp, UserRole role);

    Flux<UserBoundary> getAllUsers();
}
