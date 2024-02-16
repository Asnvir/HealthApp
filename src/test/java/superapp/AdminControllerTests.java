package superapp;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdminControllerTests {
    private WebClient webClient;
    private String baseUrl;

    @BeforeEach
    public void setup() {
        this.baseUrl = "http://localhost:8085/superapp/admin";
        this.webClient = WebClient.create(this.baseUrl);
    }
}
