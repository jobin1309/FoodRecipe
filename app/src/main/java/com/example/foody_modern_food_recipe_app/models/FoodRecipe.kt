package com.example.foody_modern_food_recipe_app.models


import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.foody_modern_food_recipe_app.data.database.room.RecipesTypeConverter
import com.example.foody_modern_food_recipe_app.models.Result
import com.google.gson.annotations.SerializedName
import retrofit2.Response

@TypeConverters(RecipesTypeConverter::class)
data class FoodRecipe(

    @SerializedName("results") //serialized name is used to map json to
    val results: List<Result>

)