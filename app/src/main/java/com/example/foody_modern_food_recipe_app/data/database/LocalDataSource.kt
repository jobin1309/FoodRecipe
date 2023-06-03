package com.example.foody_modern_food_recipe_app.data.database

import com.example.foody_modern_food_recipe_app.data.database.room.RecipesDAO
import com.example.foody_modern_food_recipe_app.data.database.room.entities.RecipesEntity
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FoodJokeEntity
import com.example.foody_modern_food_recipe_app.models.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO
) {


    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDAO.readRecipes()
    }

    fun readFavoritesRecipes(): Flow<List<FavoriteEntity>> {
        return recipesDAO.readFavoriteRecipes()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDAO.readFoodJoke()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }


    suspend fun insertFavoriteRecipes(favoriteEntity: FavoriteEntity) {
        recipesDAO.insertFavoriteRecipe(favoriteEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDAO.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        recipesDAO.deleteFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteAlFavorites() {
        recipesDAO.deleteAllFavoriteRecipe()
    }
}