package com.example.foody_modern_food_recipe_app.data.database

import com.example.foody_modern_food_recipe_app.data.database.room.RecipesDAO
import com.example.foody_modern_food_recipe_app.data.database.room.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO
) {


    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDAO.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }
}