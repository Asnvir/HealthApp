package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.UserBoundary;

public interface AdminService {
    Mono<Void> deleteAllUsers();
    Mono<Void> deleteAllObjects();
    Mono<Void> deleteAllCommandsHistory();
    Flux<UserBoundary> exportAllUsers();

   //TODO: for these two methods wee need to create MiniAppCommandBoundary
    //Flux<MiniAppCommandBoundary> exportAllMiniAppsCommandsHistory();
   //Flux<MiniAppCommandBoundary> exportMiniAppCommandsHistory(String miniAppName)


}