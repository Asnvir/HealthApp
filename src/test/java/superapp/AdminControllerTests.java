package superapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.boundary.user.NewUserBoundary;
import superapp.boundary.user.UserBoundary;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdminControllerTests {
    private WebClient webClient;
    private final String superApp = "2024a.Anna.Telisov";
    private final NewUserBoundary testAdmin = new NewUserBoundary("testAdmin@admin.com", "ADMIN", "testAdmin", "testAdmin_url");
    private final NewUserBoundary testUser = new NewUserBoundary("testUser@user.com", "SUPERAPP_USER", "testUser", "testUser_url");


    @BeforeEach
    public void setup() {
        String baseUrl = "http://localhost:8085/superapp/";
        this.webClient = WebClient.create(baseUrl);

        createUser(testAdmin, "ADMIN");
        createUser(testUser, "SUPERAPP_USER");

    }

    @AfterEach
    public void cleanup() {
        deleteUser(testAdmin.getEmail());
        deleteUser(testUser.getEmail());
    }

    private void createUser(NewUserBoundary user, String role) {
        ResponseEntity<UserBoundary> responseEntity = postUser(user);
        assertResponseEntity(responseEntity, user, role);
    }

    private ResponseEntity<UserBoundary> postUser(NewUserBoundary user) {
        return this.webClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON).body(Mono.just(user), UserBoundary.class).retrieve().toEntity(UserBoundary.class).block();
    }

    private void assertResponseEntity(ResponseEntity<UserBoundary> responseEntity, NewUserBoundary user, String role) {
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        UserBoundary receivedUser = responseEntity.getBody();
        assertThat(receivedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(receivedUser.getAvatar()).isEqualTo(user.getAvatar());
        assertThat(receivedUser.getRole().toLowerCase()).isEqualTo(role.toLowerCase());
    }

    private void deleteUser(String userEmail) {
        if (usersExist(userEmail)) {
            ClientResponse response = this.webClient.delete().uri(uriBuilder -> uriBuilder.path("/admin/users/{deleteUserEmail}").queryParam("userSuperapp", superApp).queryParam("userEmail", userEmail).build(userEmail)).exchangeToMono(Mono::just).block();

            assertThat(response).isNotNull();
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    private boolean usersExist(String userEmail) {
        ClientResponse response = this.webClient.get().uri(uriBuilder -> uriBuilder.path("/admin/hasUsers").queryParam("userSuperapp", superApp).queryParam("userEmail", userEmail).build()).exchangeToMono(Mono::just).block();

        assertThat(response).isNotNull();

        if (response.statusCode().is2xxSuccessful()) {
            return Boolean.TRUE.equals(response.bodyToMono(Boolean.class).block());
        } else {
            return false;
        }
    }

    @Test
    public void testDeleteAllUsersWithAdmin() {
        testDeleteAllUsers(testAdmin.getEmail(), HttpStatus.OK);
    }

    @Test
    public void testDeleteAllUsersWithUser() {
        testDeleteAllUsers(testUser.getEmail(), HttpStatus.FORBIDDEN);
    }

    private void testDeleteAllUsers(String userEmail, HttpStatus expectedStatus) {
        ClientResponse response = this.webClient.delete().uri(uriBuilder -> uriBuilder.path("/admin/users").queryParam("userSuperapp", superApp).queryParam("userEmail", userEmail).build()).exchangeToMono(Mono::just).block();

        assertThat(response).isNotNull();
        assertThat(response.statusCode()).isEqualTo(expectedStatus);
    }


    @Test
    public void testDeleteAllCommandHistoryWithAdmin() {
        deleteAllCommandHistory(testAdmin.getEmail(), HttpStatus.OK);
    }

    @Test
    public void testDeleteAllCommandHistoryWithUser() {
        deleteAllCommandHistory(testUser.getEmail(), HttpStatus.FORBIDDEN);
    }

    private void deleteAllCommandHistory(String userEmail, HttpStatus expectedStatus) {
        ClientResponse response = this.webClient.delete().uri(uriBuilder -> uriBuilder.path("/admin/miniapp").queryParam("userSuperapp", superApp).queryParam("userEmail", userEmail).build()).exchangeToMono(Mono::just).block();

        assertThat(response).isNotNull();
        assertThat(response.statusCode()).isEqualTo(expectedStatus);
    }

    @Test
    public void testDeleteAllObjectsWithAdmin() {
        deleteAllObjects(testAdmin.getEmail(), HttpStatus.OK);
    }

    @Test
    public void testDeleteAllObjectsWithUser() {
        deleteAllObjects(testUser.getEmail(), HttpStatus.FORBIDDEN);
    }

    private void deleteAllObjects(String userEmail, HttpStatus expectedStatus) {
        ClientResponse response = this.webClient.delete().uri(uriBuilder -> uriBuilder.path("/admin/objects").queryParam("userSuperapp", superApp).queryParam("userEmail", userEmail).build()).exchangeToMono(Mono::just).block();

        assertThat(response).isNotNull();
        assertThat(response.statusCode()).isEqualTo(expectedStatus);
    }

    @Test
    public void testExportAllUsersWithAdmin() {
        exportAllUsers(testAdmin.getEmail(), testAdmin.getRole(), HttpStatus.OK);
    }

    @Test
    public void testExportAllUsersWithUser() {
        exportAllUsers(testUser.getEmail(), testUser.getRole(), HttpStatus.FORBIDDEN);
    }

    private void exportAllUsers(String userEmail, String role, HttpStatus expectedStatus) {
        ClientResponse response = this.webClient.get().uri(uriBuilder -> uriBuilder.path("/admin/users").queryParam("userSuperapp", superApp).queryParam("userEmail", userEmail).build()).exchangeToMono(Mono::just).block();

        assertThat(response).isNotNull();
        assertThat(response.statusCode()).isEqualTo(expectedStatus);

        if (role.equalsIgnoreCase("admin")) {
            {
                Flux<UserBoundary> userFlux = response.bodyToFlux(UserBoundary.class);
                if (Boolean.TRUE.equals(userFlux.hasElements().block())) {
                    userFlux.subscribe(user -> {
                        assertThat(user).isInstanceOf(UserBoundary.class);
                    });
                }
            }
        }
    }

    @Test
    public void testExportAllMiniAppsCommandsHistoryWithAdmin() {
        exportAllMiniAppsCommandsHistory(testAdmin.getEmail(), testAdmin.getRole(), HttpStatus.OK);
    }

    @Test
    public void testExportAllMiniAppsCommandsHistoryWithUser() {
        exportAllMiniAppsCommandsHistory(testUser.getEmail(), testUser.getEmail(), HttpStatus.FORBIDDEN);
    }

    private void exportAllMiniAppsCommandsHistory(String userEmail, String role, HttpStatus expectedStatus) {
        ClientResponse response = this.webClient.get().uri(uriBuilder -> uriBuilder.path("/admin/miniapp").queryParam("userSuperapp", superApp).queryParam("userEmail", userEmail).build()).exchangeToMono(Mono::just).block();

        assertThat(response).isNotNull();
        assertThat(response.statusCode()).isEqualTo(expectedStatus);

        if (role.equalsIgnoreCase("admin")) {
            {
                Flux<MiniAppCommandBoundary> miniAppCommandBoundaryFlux = response.bodyToFlux(MiniAppCommandBoundary.class);
                if (Boolean.TRUE.equals(miniAppCommandBoundaryFlux.hasElements().block())) {
                    miniAppCommandBoundaryFlux.subscribe(miniAppCommandBoundary -> {
                        assertThat(miniAppCommandBoundary).isInstanceOf(MiniAppCommandBoundary.class);
                    });
                }
            }

        }
    }
}
