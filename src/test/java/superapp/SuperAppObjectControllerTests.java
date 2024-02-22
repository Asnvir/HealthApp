package superapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.boundary.object.SuperAppObjectIdBoundary;
import superapp.boundary.user.NewUserBoundary;
import superapp.boundary.user.UserBoundary;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SuperAppObjectControllerTests {

	private String baseUrl;
    private WebClient webClient;
    
    private String superApp;
    private String email;

    private String userName;

    private String avatar;
    private RestTemplate client;
    
    @BeforeEach
    public void setUp()
    {
    	this.baseUrl = "http://localhost:8085/superapp";
    	this.webClient = WebClient.create(this.baseUrl);
    	this.superApp  = "2024a.Anna.Telisov";
    	this.email = "yyy@gmail.com";
        this.avatar = "avatar";
        this.userName = "userName";
    	this.client = new RestTemplate();
    }
    
    @Test
    public void testCreateObjectWithEmptyEmail() {
        SuperAppObjectBoundary object = createDummyObject();
        object.setCreatedBy(new superapp.entity.object.CreatedBy(new UserId("2024a.Anna.Telisov", "")));
        assertThrows(WebClientResponseException.BadRequest.class, () -> {
	        SuperAppObjectBoundary createdObject = this.webClient.post()
	        		.uri("/objects")
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(Mono.just(object),SuperAppObjectBoundary.class)
	                .retrieve()
	                .bodyToMono(SuperAppObjectBoundary.class)
	                .block();
	        // THEN verify that the returned object has the expected properties
	        assertThat(createdObject).isNotNull();
	        assertThat(createdObject.getCreatedBy().getUserId().getEmail()).isEqualTo(object.getCreatedBy().getUserId().getEmail());
	        assertThat(createdObject.getObjectId()).isEqualTo(object.getObjectId());
        }, "User creating object must have an email.");
    }
    
    @Test
    public void testCreateObjectWithWrongEmail() {
        SuperAppObjectBoundary object = createDummyObject();
        object.setCreatedBy(new superapp.entity.object.CreatedBy(new UserId("2024a.Anna.Telisov", "abcd")));
        assertThrows(WebClientResponseException.BadRequest.class, () -> {
	        SuperAppObjectBoundary createdObject = this.webClient.post()
	        		.uri("/objects")
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(Mono.just(object),SuperAppObjectBoundary.class)
	                .retrieve()
	                .bodyToMono(SuperAppObjectBoundary.class)
	                .block();
	        // THEN verify that the returned object has the expected properties
	        assertThat(createdObject).isNotNull();
	        assertThat(createdObject.getCreatedBy().getUserId().getEmail()).isEqualTo(object.getCreatedBy().getUserId().getEmail());
	        assertThat(createdObject.getObjectId()).isEqualTo(object.getObjectId());
        }, "User creating object must have a valid email address.");
    }

    private ResponseEntity<UserBoundary> postUser(NewUserBoundary user) {
        return this.webClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON).body(Mono.just(user), UserBoundary.class).retrieve().toEntity(UserBoundary.class).block();
    }

    @Test
    public void testGetObjectsByType() {
    	System.out.println("Test: testGetObjectsByType");
        // 1. Prepare test data
        String type = "example-type";

        NewUserBoundary newUserBoundary = new NewUserBoundary(email, UserRole.SUPERAPP_USER.toString(),userName,avatar);
        postUser(newUserBoundary);
        // Construct the URL string using String.format()
        String url = String.format("%s/objects/search/byType/%s?userSuperapp=%s&userEmail=%s",
                this.baseUrl, type, this.superApp, email);
        
        SuperAppObjectBoundary[] output = this.client.getForObject(url, SuperAppObjectBoundary[].class);
        // 2. Iterate over each SuperAppObjectBoundary object and verify its getType() value
        for (SuperAppObjectBoundary object : output) {
            assertEquals(object.getType(),type);
        }
    }
    
    @Test
    public void testGetObjectsByAlias() {
    	System.out.println("Test: testGetObjectsByAlias");
    	// 1. Prepare test data
        String alias = "example-alias";
        // Construct the URL string using String.format()
        String url = String.format("%s/objects/search/byAlias/%s?userSuperapp=%s&userEmail=%s",
                this.baseUrl, alias, this.superApp, this.email);
        NewUserBoundary newUserBoundary = new NewUserBoundary(email, UserRole.SUPERAPP_USER.toString(),userName,avatar);
        postUser(newUserBoundary);
        SuperAppObjectBoundary[] output = this.client.getForObject(url, SuperAppObjectBoundary[].class);
        // 2. Iterate over each SuperAppObjectBoundary object and verify its getType() value
        for (SuperAppObjectBoundary object : output) {
            assertEquals(object.getAlias(),alias);
        }
    }

    @Test
    public void testGetObjectsByAliasPattern() {
    	System.out.println("Test: testGetObjectsByAliasPattern");
    	// 1. Prepare test data
        String alias = "ple-al";

        // Construct the URL string using String.format()
        String url = String.format("%s/objects/search/byAliasPattern/%s?userSuperapp=%s&userEmail=%s",
                this.baseUrl, alias, this.superApp, this.email);
        NewUserBoundary newUserBoundary = new NewUserBoundary(email, UserRole.SUPERAPP_USER.toString(),userName,avatar);
        postUser(newUserBoundary);
        SuperAppObjectBoundary[] output = this.client.getForObject(url, SuperAppObjectBoundary[].class);
        // 2. Iterate over each SuperAppObjectBoundary object and verify its getType() value
        for (SuperAppObjectBoundary object : output) {
        	assertThat(object.getAlias(), containsString(alias));
        }
    }
    
    
    private SuperAppObjectBoundary createDummyObject() {
        // Create the SuperAppObjectIdBoundary
        SuperAppObjectIdBoundary objectId = new SuperAppObjectIdBoundary(this.superApp, "objectId");

        // Create the object details map
        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("key1", "value1");
        objectDetails.put("key2", 123);
        SuperAppObjectBoundary created = new SuperAppObjectBoundary(objectId,
        		"example-type",
        		"example-alias",
        		true, 
        		new superapp.entity.object.CreatedBy(
        				new UserId("2024a.Anna.Telisov", "yyy@email.com")),
        		null,
        		objectDetails);
     
        return created;
    }
    
    @AfterTestClass
    public void cleanup() {
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
}
