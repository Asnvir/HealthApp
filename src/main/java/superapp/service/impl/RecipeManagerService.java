package superapp.service.impl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.entity.command.MiniAppCommandEntity;

public interface RecipeManagerService {
    public Flux<Object> getRecipes(MiniAppCommandEntity command);
    public Mono<Object> getRecipe(MiniAppCommandEntity command);
    public Mono<Object> updateRecipe(String recipeName, Object recipe);
    public Object deleteRecipe(MiniAppCommandEntity command);
    public Object getRecipeInstructions(MiniAppCommandEntity command);
    public Object addRecipeInstruction(MiniAppCommandEntity command);
    public Object deleteRecipeInstruction(MiniAppCommandEntity command);
    public Object getRecipeIngredients(MiniAppCommandEntity command);
    public Object addRecipeIngredient(MiniAppCommandEntity command);
    public Object deleteRecipeIngredient(MiniAppCommandEntity command);
    public Object getRecipeNutrition(MiniAppCommandEntity command);
    public Object getRecipeNutritionSummary(MiniAppCommandEntity command);
    public Object getRecipeNutritionDetails(MiniAppCommandEntity command);
    public Object getRecipeNutritionDetailsSummary(MiniAppCommandEntity command);

}
