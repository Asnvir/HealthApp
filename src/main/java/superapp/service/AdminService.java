package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.boundary.user.UserBoundary;

public interface AdminService {
    Mono<Void> deleteAllUsers(String superApp, String email);
    Mono<Void> deleteAllObjects(String superApp, String email);
    Mono<Void> deleteAllCommandsHistory(String superApp, String email);
    Flux<UserBoundary> exportAllUsers(String superApp, String email);
    Flux<MiniAppCommandBoundary> exportAllMiniAppsCommandsHistory(String superApp, String email);
    Flux<MiniAppCommandBoundary> exportMiniAppCommandsHistory(String miniAppName, String superApp, String email);


    Mono<Void> deleteUser(String superApp, String email, String deleteUserEmail);

    Mono<UserBoundary> exportUser(String userId, String superApp, String email);

    Mono<Boolean> hasUsers(String superApp, String email);
}