package superapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.MiniAppCommandBoundary;
import superapp.boundary.UserBoundary;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.service.AdminService;
import superapp.service.UserService;


@RestController
@RequestMapping(path = "${apiPrefix}/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {

        this.adminService = adminService;
        this.userService = userService;
    }

    @DeleteMapping("/users")
    public Mono<Void> deleteAllUsers(@RequestParam("userSuperapp") String superApp,
                                     @RequestParam("userEmail") String email
                                     ) {
        logger.info("Deleting all users in AdminController");
        return isAdmin(superApp, email)
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return adminService.deleteAllUsers()
                                .doOnSuccess(success -> logger.info("All users deleted successfully"))
                                .doOnError(error -> logger.error("Error deleting users: {}", error.getMessage()));
                    } else {
                        return Mono.error(new IllegalAccessException("User does not have admin rights"));
                    }
                });
    }

    @DeleteMapping("/objects")
    public Mono<Void> deleteAllObjects(@RequestParam("userSuperapp") String superApp,
                                       @RequestParam("userEmail") String email) {
        return isAdmin(superApp, email)
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return adminService.deleteAllObjects()
                                .doOnSuccess(success -> logger.info("All objects in MiniApp deleted successfully"))
                                .doOnError(error -> logger.error("Error deleting objects in MiniApp: {}", error.getMessage()));
                    } else {
                        return Mono.error(new IllegalAccessException("User does not have admin rights"));
                    }
                });
    }

    @DeleteMapping("/miniapp")
    public Mono<Void> deleteAllCommandsHistory(@RequestParam("userSuperapp") String superApp,
                                               @RequestParam("userEmail") String email) {
        return isAdmin(superApp, email)
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return adminService.deleteAllCommandsHistory()
                                .doOnSuccess(success -> logger.info("All commands history deleted successfully"))
                                .doOnError(error -> logger.error("Error deleting commands history: {}", error.getMessage()));
                    } else {
                        return Mono.error(new IllegalAccessException("User does not have admin rights"));
                    }
                });
    }

    @GetMapping("/users")
    public Flux<UserBoundary> exportAllUsers() {
        return adminService.exportAllUsers()
                .doOnNext(user -> logger.info("Exported user: {}", user))
                .doOnComplete(() -> logger.info("All users exported successfully"))
                .doOnError(error -> logger.error("Error exporting users: {}", error.getMessage()));
    }

    @GetMapping("/miniapp")
    public Flux<MiniAppCommandBoundary> exportAllMiniAppsCommandsHistory() {
        return adminService.exportAllMiniAppsCommandsHistory()
                .doOnNext(command -> logger.info("Exported commands history: {}", command))
                .doOnComplete(() -> logger.info("All commands history exported successfully"))
                .doOnError(error -> logger.error("Error exporting commands history: {}", error.getMessage()));
    }

    @GetMapping("/miniapp/{miniAppName}")
    public Flux<MiniAppCommandBoundary> exportMiniAppCommandsHistory(@PathVariable("miniAppName") String miniAppName) {
        return adminService.exportMiniAppCommandsHistory(miniAppName)
                .doOnNext(command -> logger.info("Exported this miniapp commands history: {}", command))
                .doOnComplete(() -> logger.info("This miniapp commands history exported successfully"))
                .doOnError(error -> logger.error("Error exporting this miniapp commands history: {}", error.getMessage()));
    }

    private Mono<Boolean> isAdmin(String superApp, String email) {
        UserId userId = new UserId(superApp, email);
        return userService.getUserById(userId)
                .map(user -> UserRole.ADMIN.toString().equals(user.getRole()))
                .onErrorResume(error -> {
                    logger.error("Error fetching user: {}", error.getMessage());
                    return Mono.just(false);
                });
    }

}
