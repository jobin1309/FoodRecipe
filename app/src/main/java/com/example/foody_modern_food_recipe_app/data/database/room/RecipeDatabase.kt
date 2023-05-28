package com.example.foody_modern_food_recipe_app.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foody_modern_food_recipe_app.data.database.room.entities.RecipesEntity
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity


@Database(
    entities = [RecipesEntity::class, FavoriteEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipesDAO

}