package com.example.foody_modern_food_recipe_app.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foody_modern_food_recipe_app.data.database.room.entities.RecipesEntity
import com.example.foody_modern_food_recipe_app.models.FoodRecipe
import com.example.foody_modern_food_recipe_app.util.NetworkResult

class RecipesBinding {

    companion object {

        @BindingAdapter(
            "readApiResponse",
            "readDatabase",
            requireAll = true
        ) // require these two variables to use
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe>?, // it should handle null
            database: List<RecipesEntity>?
        ) {

            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
                imageView.visibility = View.INVISIBLE

            }

        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {

            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE

            }

        }

    }

}