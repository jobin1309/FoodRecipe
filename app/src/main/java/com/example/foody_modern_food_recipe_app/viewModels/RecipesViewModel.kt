package com.example.foody_modern_food_recipe_app.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foody_modern_food_recipe_app.util.Constants
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.QUERY_API_KEY
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.QUERY_DIET
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.QUERY_INGREDIENTS
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.QUERY_NUMBER
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.QUERY_RECIPE_INFO
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "100"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_RECIPE_INFO] = "true"
        queries[QUERY_INGREDIENTS] = "true"

        return queries
    }
}