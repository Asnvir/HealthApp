package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.NewUserBoundary;
import superapp.boundary.UserBoundary;

public interface UserService {
    Mono<UserBoundary> create(NewUserBoundary user);

    Mono<UserBoundary> login(String userSuperApp, String email);

    Mono<Void> updateUserDetails(String userSuperApp, String userEmail, UserBoundary userToUpdate);
}
