package superapp;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.boundary.user.NewUserBoundary;
import superapp.boundary.user.UserBoundary;
import superapp.common.Consts;
import superapp.boundary.recipe.RecipeTestUtil;
import superapp.entity.command.TargetObject;
import superapp.entity.object.ObjectId;

import java.util.*;
import java.util.function.BiPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static superapp.common.Consts.*;
import static superapp.boundary.recipe.RecipeTestUtil.commandSearchByNutritionInfoAttributesString;
import static superapp.boundary.recipe.RecipeTestUtil.commandSearchByRecipeNameString;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeTest {
    private WebClient webClient;
    private final String superApp = "2024a.Anna.Telisov";
    private UserBoundary miniAppUser;
    private UserBoundary adminUser;
    private UserBoundary superAppUser;
    private SuperAppObjectBoundary recipeDummyObject;
    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        String baseUrl = "http://localhost:8085/superapp/";
        this.webClient = WebClient.create(baseUrl);

        miniAppUser = createUser(new NewUserBoundary("miniappusertest@miniappusertest.com", "MINIAPP_USER", "miniappusertest", "miniappusertest.img"));
        adminUser = createUser(new NewUserBoundary("admintest@admintest.com", "ADMIN", "admintest", "admin.img"));
        superAppUser = createUser(new NewUserBoundary("superappusertest@superappusertest.com", "SUPERAPP_USER", "superappusertest", "superappuser.img"));

        String[] recipes = {
                RecipeTestUtil.dummyRecipeString,
                RecipeTestUtil.appliPieRecipeString,
                RecipeTestUtil.chocolateCakeString,
                RecipeTestUtil.spaghettiBologneseString,
                RecipeTestUtil.bananaSmoothieString
        };


        try {
            for (String recipeString : recipes) {

                if (recipeString.equalsIgnoreCase(RecipeTestUtil.dummyRecipeString)) {
                    recipeDummyObject = createRecipe(objectMapper.readValue(recipeString, SuperAppObjectBoundary.class));
                } else {
                    createRecipe(objectMapper.readValue(recipeString, SuperAppObjectBoundary.class));
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload recipes: " + e.getMessage());
        }
    }


    @AfterEach
    public void cleanup() {
        deleteAllObjects(adminUser.getUserId().getEmail());
        deleteAllUsers(adminUser.getUserId().getEmail());
    }

    private void deleteAllUsers(String email) {
        webClient.delete().uri(uriBuilder -> uriBuilder.path("/admin/users").queryParam("userSuperapp", "2024a.Anna.Telisov").queryParam("userEmail", email).build())
                .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode()))
                .subscribe(statusCode -> assertThat(statusCode).isEqualTo(HttpStatus.OK));
    }

    private void deleteAllObjects(String email) {
        webClient.delete().uri(uriBuilder -> uriBuilder.path("/admin/objects").queryParam("userSuperapp", "2024a.Anna.Telisov").queryParam("userEmail", email).build())
                .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode()))
                .subscribe(statusCode -> assertThat(statusCode).isEqualTo(HttpStatus.OK));
    }


    private SuperAppObjectBoundary createRecipe(SuperAppObjectBoundary object) {
        ResponseEntity<SuperAppObjectBoundary> responseEntity = postRecipe(object);
        assertResponseSuperAppObjectEntity(responseEntity, object);
        return responseEntity.getBody();
    }

    private void assertResponseSuperAppObjectEntity(ResponseEntity<SuperAppObjectBoundary> responseEntity, SuperAppObjectBoundary object) {
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        SuperAppObjectBoundary receivedObject = responseEntity.getBody();

        assertThat(receivedObject.getObjectId().getSuperapp()).isEqualTo(object.getObjectId().getSuperapp());
        assertThat(receivedObject.getType()).isEqualTo(object.getType());
        assertThat(receivedObject.getAlias()).isEqualTo(object.getAlias());
        assertThat(receivedObject.getCreatedBy().getUserId().getSuperapp()).isEqualTo(object.getCreatedBy().getUserId().getSuperapp());
        assertThat(receivedObject.getCreatedBy().getUserId().getEmail()).isEqualTo(object.getCreatedBy().getUserId().getEmail());
        assertThat(receivedObject.getObjectDetails()).isEqualTo(object.getObjectDetails());
    }


    private ResponseEntity<SuperAppObjectBoundary> postRecipe(SuperAppObjectBoundary object) {
        return webClient.post().uri("/objects").contentType(MediaType.APPLICATION_JSON).body(Mono.just(object), SuperAppObjectBoundary.class).retrieve().toEntity(SuperAppObjectBoundary.class).block();
    }


    private UserBoundary createUser(NewUserBoundary user) {
        ResponseEntity<UserBoundary> responseEntity = postUser(user);
        assertResponseUserEntity(responseEntity, user);
        return responseEntity.getBody();
    }

    private ResponseEntity<UserBoundary> postUser(NewUserBoundary user) {
        return webClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON).body(Mono.just(user), UserBoundary.class).retrieve().toEntity(UserBoundary.class).block();
    }

    private void assertResponseUserEntity(ResponseEntity<UserBoundary> responseEntity, NewUserBoundary user) {
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        UserBoundary receivedUser = responseEntity.getBody();
        assertThat(receivedUser.getUserId().getEmail()).isEqualTo(user.getEmail());
        assertThat(receivedUser.getRole()).isEqualTo(user.getRole());
        assertThat(receivedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(receivedUser.getAvatar()).isEqualTo(user.getAvatar());

    }

    @Test
    public void testRecipeSearchByNutritionalInfoWithOperator() {
        List<Arguments> testCases = new ArrayList<>();


        String number = "100000";
        for (String operator : Arrays.asList(SEARCH_OPERATOR_LESS_THAN, SEARCH_OPERATOR_GREATER_THAN, SEARCH_OPERATOR_EQUAL_TO)) {
            testCases.add(Arguments.of(NUTRITIONAL_INFO_CALORIES, number, operator));
            testCases.add(Arguments.of(NUTRITIONAL_INFO_PROTEIN, number, operator));
            testCases.add(Arguments.of(NUTRITIONAL_INFO_FAT, number, operator));
            testCases.add(Arguments.of(NUTRITIONAL_INFO_CARBS, number, operator));
        }


        for (int i = 0; i < testCases.size(); i++) {
            Arguments testCase = testCases.get(i);
            String nutritionalInfoType = (String) testCase.get()[0];
            String value = (String) testCase.get()[1];
            String operator = (String) testCase.get()[2];

            MiniAppCommandBoundary command = generateCommandSearchByNutritionCriterias(nutritionalInfoType, value, operator);

            Flux<SuperAppObjectBoundary> responseFlux = webClient.post()
                    .uri("/miniapp/{miniAppName}", Consts.MINI_APP_NAME_RECIPE_MANAGER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(command), MiniAppCommandBoundary.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Error status code received: " + clientResponse.statusCode().value())))
                    .bodyToFlux(SuperAppObjectBoundary.class);

            StepVerifier.create(responseFlux)
                    .thenConsumeWhile(object -> verifyAttributeWithOperator(object, nutritionalInfoType, Double.parseDouble(value), operator))
                    .expectComplete()
                    .verify();


            int index = i + 1;
            System.out.println("[" + index + "] - V - " + nutritionalInfoType + " " + operator + " " + value);

        }

    }

    private MiniAppCommandBoundary generateCommandSearchByNutritionCriterias(String attribute, String value, String operator) {
        String commandJson = commandSearchByNutritionInfoAttributesString.formatted(recipeDummyObject.getObjectId().getId(), miniAppUser.getUserId().getEmail(), attribute, value, operator);
        MiniAppCommandBoundary command = null;
        try {
            JsonNode rootNode = objectMapper.readTree(commandJson);
            command = objectMapper.treeToValue(rootNode, MiniAppCommandBoundary.class);
            command.setTargetObject(new TargetObject(new ObjectId(superApp, recipeDummyObject.getObjectId().getId())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }

    private boolean verifyAttributeWithOperator(SuperAppObjectBoundary receivedObject, String attribute, double value, String operator) {
        JsonNode rootNode = objectMapper.valueToTree(receivedObject);
        JsonNode nutritionalInfo = rootNode.get(OBJECT_DETAILS).get(Consts.RECIPE_NUTRITIONAL_INFO);

        if (nutritionalInfo != null && nutritionalInfo.isObject()) {
            JsonNode attributeNode = nutritionalInfo.get(attribute);

            if (attributeNode != null && attributeNode.isNumber()) {
                double attributeValue = attributeNode.asDouble();
                return switch (operator) {
                    case SEARCH_OPERATOR_LESS_THAN -> attributeValue < value;
                    case SEARCH_OPERATOR_GREATER_THAN -> attributeValue > value;
                    case SEARCH_OPERATOR_EQUAL_TO -> attributeValue == value;
                    default -> {
                        System.out.println("Invalid operator: " + operator);
                        yield false;
                    }
                };
            } else {
                System.out.println(attribute + " value node is not found or not a number node.");
            }
        } else {
            System.out.println("Nutritional info node is not found or not an object node.");
        }

        return false;
    }

    private boolean verifyRecipeName(SuperAppObjectBoundary receivedObject, String recipeName) {
        JsonNode rootNode = objectMapper.valueToTree(receivedObject);
        JsonNode recipeNameNode = rootNode.get(OBJECT_DETAILS).get(Consts.RECIPE_NAME);

        if (recipeNameNode != null && recipeNameNode.isTextual()) {
            return recipeNameNode.asText().equalsIgnoreCase(recipeName);
        } else {
            System.out.println("Recipe name node is not found or not a text node.");
        }

        return false;
    }

    private boolean verifyRecipeDescription(SuperAppObjectBoundary receivedObject, String recipeDescription) {
        JsonNode rootNode = objectMapper.valueToTree(receivedObject);
        JsonNode recipeDescriptionNode = rootNode.get(OBJECT_DETAILS).get(Consts.RECIPE_DESCRIPTION);

        if (recipeDescriptionNode != null && recipeDescriptionNode.isTextual()) {
            return recipeDescriptionNode.asText().equalsIgnoreCase(recipeDescription);
        } else {
            System.out.println("Recipe description node is not found or not a text node.");
        }

        return false;
    }

    private boolean verifyIngredient(SuperAppObjectBoundary receivedObject, String ingredient) {
        JsonNode rootNode = objectMapper.valueToTree(receivedObject);
        JsonNode ingredientsNode = rootNode.get(OBJECT_DETAILS).get(RECIPE_INGREDIENTS);

        if (ingredientsNode != null && ingredientsNode.isArray()) {
            for (JsonNode ingredientNode : ingredientsNode) {
                if (ingredientNode.isTextual() && ingredientNode.asText().equalsIgnoreCase(ingredient)) {
                    return true;
                }
            }
        }

        System.out.println("Ingredient: " + ingredient + " is not found.");
        return false;
    }


    private MiniAppCommandBoundary generateSearchCommand(String searchType, String value) {
        MiniAppCommandBoundary command = null;
        try {
            String commandJson = commandSearchByRecipeNameString.formatted(recipeDummyObject.getObjectId().getId(), miniAppUser.getUserId().getEmail(), searchType, value);
            JsonNode rootNode = objectMapper.readTree(commandJson);
            command = objectMapper.treeToValue(rootNode, MiniAppCommandBoundary.class);
            command.setTargetObject(new TargetObject(new ObjectId(superApp, recipeDummyObject.getObjectId().getId())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }


    private void performSearchTest(String searchType, String value, BiPredicate<SuperAppObjectBoundary, String> verifier) {
        MiniAppCommandBoundary command = generateSearchCommand(searchType, value);

        Flux<SuperAppObjectBoundary> responseFlux = webClient.post()
                .uri("/miniapp/{miniAppName}", Consts.MINI_APP_NAME_RECIPE_MANAGER)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(command), MiniAppCommandBoundary.class)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Error status code received: " + clientResponse.statusCode().value())))
                .bodyToFlux(SuperAppObjectBoundary.class);

        StepVerifier.create(responseFlux)
                .thenConsumeWhile(object -> verifier.test(object, value))
                .expectComplete()
                .verify();

        System.out.println("Search by " + searchType + ": " + value + " is found.");
    }

    @Test
    public void testRecipeSearchByRecipeName() {
        String recipeName = "Apple Pie";
        performSearchTest(COMMAND_ATTRIBUTES_SEARCH_TYPE_RECIPE_NAME, recipeName, this::verifyRecipeName);
    }

    @Test
    public void testRecipeSearchByRecipeDescription() {
        String recipeDescription = "A delicious apple pie recipe.";
        performSearchTest(COMMAND_ATTRIBUTES_SEARCH_TYPE_RECIPE_DESCRIPTION, recipeDescription, this::verifyRecipeDescription);
    }

    @Test
    public void testRecipeSearchByRecipeIngredient() {
        String ingredient = "apple";
        performSearchTest(COMMAND_ATTRIBUTES_SEARCH_TYPE_INGREDIENT, ingredient, this::verifyIngredient);
    }


}
