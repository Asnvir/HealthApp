package superapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.UserBoundary;
import superapp.service.AdminService;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @DeleteMapping("/users")
    public Mono<Void> deleteAllUsers() {
        logger.info("Deleting all users in AdminController");
        return adminService.deleteAllUsers()
                .doOnSuccess(success -> logger.info("All users deleted successfully"))
                .doOnError(error -> logger.error("Error deleting users: {}", error.getMessage()));
    }

    @DeleteMapping("/miniapp")
    public Mono<Void> deleteAllObjects() {
        return adminService.deleteAllObjects()
                .doOnSuccess(success -> logger.info("All objects in MiniApp deleted successfully"))
                .doOnError(error -> logger.error("Error deleting objects in MiniApp: {}", error.getMessage()));
    }

    @GetMapping("/users")
    public Flux<UserBoundary> exportAllUsers() {
        return adminService.exportAllUsers()
                .doOnNext(user -> logger.info("Exported user: {}", user))
                .doOnComplete(() -> logger.info("All users exported successfully"))
                .doOnError(error -> logger.error("Error exporting users: {}", error.getMessage()));
    }

}