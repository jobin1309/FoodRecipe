package com.example.foody_modern_food_recipe_app.data.network

import com.example.foody_modern_food_recipe_app.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
      return foodRecipesApi.searchRecipes(searchQuery)
    }
}