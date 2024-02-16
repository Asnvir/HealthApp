package superapp;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import superapp.boundary.user.NewUserBoundary;
import superapp.boundary.user.UserBoundary;
import superapp.boundary.user.UserIdBoundary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserControllerTest {


    private WebClient webClient;
    private String baseUrl;


    @BeforeEach
    public void setup() {
        this.baseUrl = "http://localhost:8085/superapp";
        this.webClient = WebClient.create(this.baseUrl);
    }

    @AfterEach
    public void cleanup() {
        String superApp = "2024a.Anna.Telisov";
        String email = "admin@example.com";
        NewUserBoundary user = new NewUserBoundary(email, "ADMIN", "Admin", "admin_url");


       this.webClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), UserBoundary.class)
                .retrieve()
                .bodyToMono(UserBoundary.class)
                .block();


        this.webClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/users")
                        .queryParam("userSuperapp", superApp)
                        .queryParam("userEmail", email)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Test
    public void testCreateNewUser() {

        NewUserBoundary user = new NewUserBoundary("user@example.com", "ADMIN", "John Doe", "avatar_url");


        UserBoundary createdUser = this.webClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), UserBoundary.class)
                .retrieve()
                .bodyToMono(UserBoundary.class)
                .block();

        // THEN verify that the returned object has the expected properties
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getUserId().getEmail()).isEqualTo(user.getEmail());
        assertThat(createdUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void testCreateNewUserWithEmptyEmail() {

        NewUserBoundary user = new NewUserBoundary("", "ADMIN", "John Doe", "avatar_url");


        assertThrows(WebClientResponseException.BadRequest.class, () -> {
            this.webClient.post()
                    .uri("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(user), UserBoundary.class)
                    .retrieve()
                    .bodyToMono(UserBoundary.class)
                    .block();
        }, "Creating a user with empty email should fail");
    }

    @Test
    public void testCreateNewUserWithWrongEmail() {

        NewUserBoundary user = new NewUserBoundary("2gsgsd", "ADMIN", "John Doe", "avatar_url");


        assertThrows(WebClientResponseException.BadRequest.class, () -> {
            this.webClient.post()
                    .uri("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(user), UserBoundary.class)
                    .retrieve()
                    .bodyToMono(UserBoundary.class)
                    .block();
        }, "Creating a user with wrong format email should fail");
    }

    @Test
    public void testCreateNewUserWithEmptyName() {

        NewUserBoundary user = new NewUserBoundary("2gsgsd", "ADMIN", "", "avatar_url");


        assertThrows(WebClientResponseException.BadRequest.class, () -> {
            this.webClient.post()
                    .uri("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(user), UserBoundary.class)
                    .retrieve()
                    .bodyToMono(UserBoundary.class)
                    .block();
        }, "Creating a user with empty name should fail");
    }

    @Test
    public void testCreateNewUserWithEmptyAvatar() {

        NewUserBoundary user = new NewUserBoundary("2gsgsd", "ADMIN", "", "avatar_url");


        assertThrows(WebClientResponseException.BadRequest.class, () -> {
            this.webClient.post()
                    .uri("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(user), UserBoundary.class)
                    .retrieve()
                    .bodyToMono(UserBoundary.class)
                    .block();
        }, "Creating a user with empty avatar should fail");
    }

    @Test
    public void testLoginUser() {

        String superApp = "2024a.Anna.Telisov";
        String email = "user@example.com";
        NewUserBoundary newUser = new NewUserBoundary(email, "ADMIN", "John Doe", "avatar_url");

        // AND the user is created
        this.webClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newUser), UserBoundary.class)
                .retrieve()
                .bodyToMono(UserBoundary.class)
                .block();


        UserBoundary loggedInUser = this.webClient.get()
                .uri("/users/login/{superapp}/{email}", superApp, email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserBoundary.class)
                .block();


        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getUserId().getEmail()).isEqualTo(email);
    }

    @Test
    public void testUpdateUserDetails() {

        String superapp = "2024a.Anna.Telisov";
        String userEmail = "user@example.com";
        UserBoundary userToUpdate = new UserBoundary(new UserIdBoundary(superapp, userEmail), "ADMIN", "Jane Doe", "new_avatar_url");

        NewUserBoundary user = new NewUserBoundary(userEmail, "ADMIN", "John Doe", "avatar_url");


        this.webClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), UserBoundary.class)
                .retrieve()
                .bodyToMono(UserBoundary.class)
                .block();


        this.webClient.put()
                .uri("/users/{superapp}/{userEmail}", superapp, userEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userToUpdate), UserBoundary.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();


        UserBoundary updatedUser = this.webClient.get()
                .uri("/users/login/{superapp}/{email}", superapp, userEmail)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserBoundary.class)
                .block();


        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUserId().getEmail()).isEqualTo(userEmail);
        assertThat(updatedUser.getUsername()).isEqualTo(userToUpdate.getUsername());
        assertThat(updatedUser.getAvatar()).isEqualTo(userToUpdate.getAvatar());
        assertThat(updatedUser.getRole()).isEqualTo(userToUpdate.getRole());
    }


}
