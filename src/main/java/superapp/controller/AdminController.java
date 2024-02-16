package superapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.boundary.user.UserBoundary;
import superapp.service.AdminService;


@RestController
@RequestMapping(path = "${apiPrefix}/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @DeleteMapping("/users")
    public Mono<Void> deleteAllUsers(@RequestParam("userSuperapp") String superApp,
                                     @RequestParam("userEmail") String email
    ) {
        return adminService.deleteAllUsers(superApp, email);
    }

    @DeleteMapping("/objects")
    public Mono<Void> deleteAllObjects(@RequestParam("userSuperapp") String superApp,
                                       @RequestParam("userEmail") String email) {

        return adminService.deleteAllObjects(superApp, email);

    }

    @DeleteMapping("/miniapp")
    public Mono<Void> deleteAllCommandsHistory(@RequestParam("userSuperapp") String superApp,
                                               @RequestParam("userEmail") String email) {
        logger.info("Deleting all commands history in AdminController");
        return adminService.deleteAllCommandsHistory(superApp, email);

    }

    @GetMapping("/users")
    public Flux<UserBoundary> exportAllUsers(@RequestParam("userSuperapp") String superApp,
                                             @RequestParam("userEmail") String email) {
        return adminService.exportAllUsers(superApp, email);

    }

    @GetMapping("/miniapp")
    public Flux<MiniAppCommandBoundary> exportAllMiniAppsCommandsHistory(@RequestParam("userSuperapp") String superApp,
                                                                         @RequestParam("userEmail") String email) {
        return adminService.exportAllMiniAppsCommandsHistory(superApp, email);

    }

    @GetMapping("/miniapp/{miniAppName}")
    public Flux<MiniAppCommandBoundary> exportMiniAppCommandsHistory(@PathVariable("miniAppName") String miniAppName,
                                                                     @RequestParam("userSuperapp") String superApp,
                                                                     @RequestParam("userEmail") String email
    ) {
        return adminService.exportMiniAppCommandsHistory(miniAppName, superApp, email);

    }

}
