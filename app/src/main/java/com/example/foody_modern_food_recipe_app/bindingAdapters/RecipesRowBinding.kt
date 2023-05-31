package com.example.foody_modern_food_recipe_app.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.ui.mainActivity.fragments.recipesFragments.RecipesFragmentsDirections
import org.jsoup.Jsoup

class RecipesRowBinding {

    companion object {


        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(
            recipeRowLayout: ConstraintLayout,
            result: com.example.foody_modern_food_recipe_app.models.Result
        ) {
            recipeRowLayout.setOnClickListener {
                try {
                    val action =
                        RecipesFragmentsDirections.actionRecipesFragmentsToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)


                } catch (e: Exception) {
                    Log.d("OnRecipeClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.baseline_cloud_off_24)
            }
        }


        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }


        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context, R.color.green
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?) {
            if (description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }

//
    }

//
}
