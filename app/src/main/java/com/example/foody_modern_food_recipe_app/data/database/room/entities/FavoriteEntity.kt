package com.example.foody_modern_food_recipe_app.data.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody_modern_food_recipe_app.models.Result
import com.example.foody_modern_food_recipe_app.util.Constants


@Entity(tableName = Constants.FAVORITE_RECIPES_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: com.example.foody_modern_food_recipe_app.models.Result
) {
}