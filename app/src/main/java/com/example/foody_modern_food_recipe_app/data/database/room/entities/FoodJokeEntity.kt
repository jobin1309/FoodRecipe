package com.example.foody_modern_food_recipe_app.data.database.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody_modern_food_recipe_app.models.FoodJoke
import com.example.foody_modern_food_recipe_app.util.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}