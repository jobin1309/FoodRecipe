package com.example.foody_modern_food_recipe_app.data

import com.example.foody_modern_food_recipe_app.data.database.LocalDataSource
import com.example.foody_modern_food_recipe_app.data.network.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton //survive configuration changes throughout the app
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}