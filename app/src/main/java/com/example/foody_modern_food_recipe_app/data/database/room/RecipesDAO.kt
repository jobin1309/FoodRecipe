package com.example.foody_modern_food_recipe_app.data.database.room

import androidx.room.*
import com.example.foody_modern_food_recipe_app.data.database.room.entities.RecipesEntity
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorite_recipe_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>


    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_recipe_table")
    suspend fun deleteAllFavoriteRecipe()
}