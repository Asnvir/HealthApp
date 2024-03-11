package superapp.common;

public class Consts {
    public static final String APPLICATION_NAME = "spring.application.name";

    public static final String OBJECT_ID = "objectId";
    public static final String OBJECT_ID_SUPERAPP = "superapp";
    public static final String OBJECT_ID_ID = "id";

    public static final String OBJECT_TYPE = "type";
    public static final String OBJECT_ALIAS = "alias";
    public static final String OBJECT_ACTIVE = "active";
    public static final String OBJECT_CREATION_TIMESTAMP = "creationTimestamp";
    public static final String OBJECT_CREATED_BY = "createdBy";
    public static final String OBJECT_CREATED_BY_USERID = "userId";
    public static final String OBJECT_CREATED_BY_USERID_SUPERAPP = "superapp";
    public static final String OBJECT_CREATED_BY_USERID_EMAIL = "email";
    public static final String OBJECT_DETAILS = "objectDetails";

    ///////////////////////////////////////////////////////////////

    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_DESCRIPTION = "description";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_INSTRUCTIONS = "instructions";
    public static final String RECIPE_COOKING_TIME = "cookingTime";
    public static final String RECIPE_COOKING_TIME_DURATION = "duration";
    public static final String RECIPE_COOKING_TIME_UNIT = "unit";

    public static final String RECIPE_SERVING_SIZE = "servingSize";
    public static final String RECIPE_NUTRITIONAL_INFO = "nutritionalInfo";
    public static final String NUTRITIONAL_INFO_CALORIES = "calories";
    public static final String NUTRITIONAL_INFO_FAT = "fat";
    public static final String NUTRITIONAL_INFO_CARBS = "carbohydrates";
    public static final String NUTRITIONAL_INFO_PROTEIN = "protein";
    public static final String INGREDIENT_NAME = "name";
    public static final String INGREDIENT_QUANTITY = "quantity";
    public static final String INGREDIENT_UNIT = "unit";
    public static final String INGREDIENT_CATEGORY = "category";

  // COMMAND
    public static final String COMMAND_ID = "commandId";
    public static final String COMMAND_ID_SUPERAPP = "superapp";
    public static final String COMMAND_ID_MINIAPP = "miniapp";
    public static final String COMMAND_ID_ID = "id";
    public static final String COMMAND_COMMAND = "command";
    public static final String COMMAND_TARGETOBJECT = "targetObject";
    public static final String COMMAND_TARGETOBJECT_OBJECTID = "objectId";
    public static final String COMMAND_TARGETOBJECT_OBJECTID_SUPERAPP = "superapp";
    public static final String COMMAND_TARGETOBJECT_OBJECTID_ID = "id";
    public static final String COMMAND_INVOCATIONTEIMESTAMP = "invocationTimestamp";
    public static final String COMMAND_INVOKEDBY = "invokedBy";
    public static final String COMMAND_INVOKEDBY_USERID = "userId";
    public static final String COMMAND_INVOKEDBY_USERID_SUPERAPP= "superapp";
    public static final String COMMAND_INVOKEDBY_USERID_EMAIL= "email";
    public static final String COMMAND_COMMANDATTRIBUTES= "commandAttributes";

    // TYPES



    // Miniapps - "RecipeManager"
    public static final String MINI_APP_NAME_RECIPE_MANAGER = "RecipeManager";
    public static final String RECIPE_TYPE = "recipe";
    public static final String MINI_APP_COMMAND_PERFORM_SEARCH_BY_COMMAND_ATTRIBUTES = "performSearchByCommandAttributes";
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE = "searchType";
    public static final String COMMAND_ATTRIBUTES_SEARCH_VALUE = "value";
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_CALORIES = NUTRITIONAL_INFO_CALORIES;
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_PROTEIN = NUTRITIONAL_INFO_PROTEIN;
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_FAT = NUTRITIONAL_INFO_FAT;
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_CARBS = NUTRITIONAL_INFO_CARBS;
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_OPERATOR = "operator";
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_RECIPE_NAME = RECIPE_NAME;
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_RECIPE_DESCRIPTION = RECIPE_DESCRIPTION;
    public static final String COMMAND_ATTRIBUTES_SEARCH_TYPE_INGREDIENT = "ingredient";
    public static final String SEARCH_OPERATOR_GREATER_THAN = "greaterThan";
    public static final String SEARCH_OPERATOR_LESS_THAN = "lessThan";
    public static final String SEARCH_OPERATOR_EQUAL_TO = "equalTo";
    public static final String MINI_APP_ALIAS_DUMMYRECIPE = "dummyRecipe";


}
