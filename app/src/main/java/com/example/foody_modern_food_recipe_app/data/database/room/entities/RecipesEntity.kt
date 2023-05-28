package com.example.foody_modern_food_recipe_app.data.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.foody_modern_food_recipe_app.data.database.room.RecipesTypeConverter
import com.example.foody_modern_food_recipe_app.models.FoodRecipe
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.RECIPES_TABLE
import kotlinx.android.parcel.Parcelize


@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe

    ) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}