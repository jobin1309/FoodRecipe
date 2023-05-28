package com.example.foody_modern_food_recipe_app.data.network

import com.example.foody_modern_food_recipe_app.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap SearchQuery: Map<String, String>
    ): Response<FoodRecipe>
}