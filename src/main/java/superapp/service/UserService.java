package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.NewUserBoundary;
import superapp.boundary.UserBoundary;

public interface UserService {
    Mono<UserBoundary> create(NewUserBoundary user);

    Mono<UserBoundary> get();

    Mono<Void> update(UserBoundary user);
}
