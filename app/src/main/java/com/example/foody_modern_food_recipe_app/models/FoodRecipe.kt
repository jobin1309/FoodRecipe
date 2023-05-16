package com.example.foody_modern_food_recipe_app.models


import com.example.foody_modern_food_recipe_app.models.Result
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class FoodRecipe(

    @SerializedName("results") //serialized name is used to map json to
    val results: List<Result>

)