package com.example.foody_modern_food_recipe_app.di

import android.app.Application
import com.example.foody_modern_food_recipe_app.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideMyApplication(application: Application): MyApplication {
        return application as MyApplication
    }

}