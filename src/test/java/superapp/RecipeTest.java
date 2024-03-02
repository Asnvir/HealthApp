package superapp;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import superapp.boundary.menu.Recipe;
import superapp.boundary.object.SuperAppObjectBoundary;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeTest {

    @Test
    public void testFromObjectDetails() {
        String jsonString = """
                {
                 "objectId": {
                    "superapp": "2024a.demo",
                    "id": "2"
                 },
                 "type": "recipe",
                 "alias": "Peach and Berry Puffs",
                 "active": true,
                 "creationTimestamp": "2024-02-28T12:00:00.000+0000",
                 "createdBy": {
                    "userId": {
                      "superapp": "2024a.demo",
                      "email": "chef@demo.org"
                    }
                 },
                 "objectDetails": {
                    "name": "Рецепт",
                    "description": "Описание рецепта",
                    "ingredients": [
                      {
                        "name": "Мандарины",
                        "quantity": "1 кг",
                        "unit": "шт",
                        "category": "Fruit"
                      },
                      {
                        "name": "Клубника",
                        "quantity": "200 г",
                        "unit": "г",
                        "category": "Fruit"
                      }
                    ],
                    "instructions": [
                      "Протрите мягкое масло в миске.",
                      "Добавьте сахар и размешайте."
                    ],
                    "cookingTime": "20 минут",
                    "servingSize": "4 порции",
                    "nutritionalInfo": {
                      "calories": 250,
                      "protein": 5,
                      "fat": 10,
                      "carbohydrates": 50
                    }
                 }
                }"""
               ;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SuperAppObjectBoundary superAppObjectBoundary = objectMapper.readValue(jsonString, SuperAppObjectBoundary.class);
            Map<String, Object> details = superAppObjectBoundary.getObjectDetails();
            Recipe recipe = Recipe.fromObjectDetailstoRecipe(details);
            System.out.println(recipe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
