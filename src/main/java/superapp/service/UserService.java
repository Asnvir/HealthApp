package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.NewUserBoundary;
import superapp.boundary.UserBoundary;
import superapp.entity.user.UserId;

public interface UserService {
    Mono<UserBoundary> create(NewUserBoundary user);

    Mono<UserBoundary> login(String userSuperApp, String email);

    Mono<Void> updateUserDetails(String userSuperApp, String userEmail, UserBoundary userToUpdate);

    Mono<UserBoundary> getUserById(UserId userId);

    Mono<Void> deleteUser(String superApp, String userEmail);
}
