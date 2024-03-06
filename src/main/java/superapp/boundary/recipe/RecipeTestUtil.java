package superapp.boundary.recipe;

public class RecipeTestUtil {

    public static String dummyRecipeString = """
            {
               "objectId": {
                 "superapp": "2024a.Anna.Telisov",
                 "id": "550e8400-e29b-41d4-a716-446655441234"
               },
               "type": "recipe",
               "alias": "dummyRecipe",
               "active": true,
               "creationTimestamp": "2024-03-01T12:00:00.000+0000",
               "createdBy": {
                 "userId": {
                   "superapp": "2024a.Anna.Telisov",
                   "email": "superappusertest@superappusertest.com"
                 }
               },
               "objectDetails": {
                 "name": "dummyRecipe",
                 "description": "dummyRecipe",
                 "ingredients": [
                   {
                     "name": "dummyRecipe",
                     "quantity": "4",
                     "unit": "pieces",
                     "category": "dummyRecipe"
                   }
                 ],
                 "instructions": [
                   "dummyRecipe"
                 ],
                 "cookingTime": {
                   "duration": "20",
                   "unit": "seconds"
                 },
                 "servingSize": "4",
                 "nutritionalInfo": {
                   "calories": 100000,
                   "protein": 5,
                   "fat": 10,
                   "carbohydrates": 50
                 }
               }
             }""";

    public static String appliPieRecipeString = """
            {
              "objectId": {
                "superapp": "2024a.Anna.Telisov",
                "id": "550e8400-e29b-41d4-a716-446655441234"
              },
              "type": "recipe",
              "alias": "applePie",
              "active": true,
              "creationTimestamp": "2024-03-01T12:00:00.000+0000",
              "createdBy": {
                "userId": {
                  "superapp": "2024a.Anna.Telisov",
                  "email": "superappusertest@superappusertest.com"
                }
              },
              "objectDetails": {
                "name": "applePie",
                "description": "applePie",
                "ingredients": [
                  {
                    "name": "dummyRecipe",
                    "quantity": "4",
                    "unit": "pieces",
                    "category": "dummyRecipe"
                  }
                ],
                "instructions": [
                  "dummyRecipe"
                ],
                "cookingTime": {
                  "duration": "20",
                  "unit": "seconds"
                },
                "servingSize": "4",
                "nutritionalInfo": {
                  "calories": 100000,
                  "protein": 5,
                  "fat": 10,
                  "carbohydrates": 50
                }
              }
            }
            """;

    public static String chocolateCakeString = """
            {
              "objectId": {
                "superapp": "2024a.Anna.Telisov",
                "id": "550e8400-e29b-41d4-a716-446655441234"
              },
              "type": "recipe",
              "alias": "chocolateCake",
              "active": true,
              "creationTimestamp": "2024-03-01T12:00:00.000+0000",
              "createdBy": {
                "userId": {
                  "superapp": "2024a.Anna.Telisov",
                  "email": "superappusertest@superappusertest.com"
                }
              },
              "objectDetails": {
                "name": "chocolateCake",
                "description": "chocolateCake",
                "ingredients": [
                  {
                    "name": "dummyRecipe",
                    "quantity": "4",
                    "unit": "pieces",
                    "category": "dummyRecipe"
                  }
                ],
                "instructions": [
                  "dummyRecipe"
                ],
                "cookingTime": {
                  "duration": "20",
                  "unit": "seconds"
                },
                "servingSize": "4",
                "nutritionalInfo": {
                  "calories": 100000,
                  "protein": 5,
                  "fat": 10,
                  "carbohydrates": 50
                }
              }
            }
            """;

    public static String spaghettiBologneseString = """
            {
              "objectId": {
                "superapp": "2024a.Anna.Telisov",
                "id": "550e8400-e29b-41d4-a716-446655441234"
              },
              "type": "recipe",
              "alias": "spaghettiBolognese",
              "active": true,
              "creationTimestamp": "2024-03-01T12:00:00.000+0000",
              "createdBy": {
                "userId": {
                  "superapp": "2024a.Anna.Telisov",
                  "email": "superappusertest@superappusertest.com"
                }
              },
              "objectDetails": {
                "name": "spaghettiBolognese",
                "description": "spaghettiBolognese",
                "ingredients": [
                  {
                    "name": "dummyRecipe",
                    "quantity": "4",
                    "unit": "pieces",
                    "category": "dummyRecipe"
                  }
                ],
                "instructions": [
                  "dummyRecipe"
                ],
                "cookingTime": {
                  "duration": "20",
                  "unit": "seconds"
                },
                "servingSize": "4",
                "nutritionalInfo": {
                  "calories": 100000,
                  "protein": 5,
                  "fat": 10,
                  "carbohydrates": 50
                }
              }
            }
            """;

    public static String bananaSmoothieString = """
            {
              "objectId": {
                "superapp": "2024a.Anna.Telisov",
                "id": "550e8400-e29b-41d4-a716-446655441234"
              },
              "type": "recipe",
              "alias": "bananaSmoothie",
              "active": true,
              "creationTimestamp": "2024-03-01T12:00:00.000+0000",
              "createdBy": {
                "userId": {
                  "superapp": "2024a.Anna.Telisov",
                  "email": "superappusertest@superappusertest.com"
                }
              },
              "objectDetails": {
                "name": "bananaSmoothie",
                "description": "bananaSmoothie",
                "ingredients": [
                  {
                    "name": "dummyRecipe",
                    "quantity": "4",
                    "unit": "pieces",
                    "category": "dummyRecipe"
                  }
                ],
                "instructions": [
                  "dummyRecipe"
                ],
                "cookingTime": {
                  "duration": "20",
                  "unit": "seconds"
                },
                "servingSize": "4",
                "nutritionalInfo": {
                  "calories": 10,
                  "protein": 5,
                  "fat": 10,
                  "carbohydrates": 50
                }
              }
            }
            """;

    public static String commandSearchByNutritionInfoAttributesString = """
            {
              "commandId": {
                "superapp": "2024a.Anna.Telisov",
                "miniapp": "RecipeManager",
                "id": "550e8400-e29b-41d4-a716-441655440000"
              },
              "command": "performSearchByCommandAttributes",
              "targetObject": {
                "objectId": {
                  "superapp": "2024a.Anna.Telisov",
                  "id": "%s"
                }
              },
              "invocationTimestamp": "2024-03-04T10:00:00.000+0000",
              "invokedBy": {
                "userId": {
                  "superapp": "2024a.Anna.Telisov",
                  "email": "%s"
                }
              },
              "commandAttributes": {
                "searchType": "%s",
                "value": "%s",
                "operator": "%s"
              }
            }""";


    public static String commandSearchByRecipeNameString = """
            {
              "commandId": {
                "superapp": "2024a.Anna.Telisov",
                "miniapp": "RecipeManager",
                "id": "550e8400-e29b-41d4-a716-441655440000"
              },
              "command": "performSearchByCommandAttributes",
              "targetObject": {
                "objectId": {
                  "superapp": "2024a.Anna.Telisov",
                  "id": "%s"
                }
              },
              "invocationTimestamp": "2024-03-04T10:00:00.000+0000",
              "invokedBy": {
                "userId": {
                  "superapp": "2024a.Anna.Telisov",
                  "email": "%s"
                }
              },
              "commandAttributes": {
                "searchType": "%s",
                "value": "%s"
              }
            }""";


}
