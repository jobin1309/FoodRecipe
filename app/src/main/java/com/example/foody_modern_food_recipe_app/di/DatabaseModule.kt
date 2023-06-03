package com.example.foody_modern_food_recipe_app.di

import android.content.Context
import androidx.room.Room
import com.example.foody_modern_food_recipe_app.data.database.room.RecipeDatabase
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides //3rd party library
    fun providesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, RecipeDatabase::class.java, DATABASE_NAME
    ).fallbackToDestructiveMigration().build()


    @Provides
    fun providesDao(database: RecipeDatabase) = database.recipeDao()

}