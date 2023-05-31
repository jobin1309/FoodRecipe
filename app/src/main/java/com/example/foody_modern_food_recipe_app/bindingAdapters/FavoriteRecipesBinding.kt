package com.example.foody_modern_food_recipe_app.bindingAdapters

import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.datastore.preferences.protobuf.Internal.MapAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foody_modern_food_recipe_app.adapters.FavoriteRecipeAdapter
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity

class FavoriteRecipesBinding {

    companion object {

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoriteEntity: List<FavoriteEntity>?,
            mAdapter: FavoriteRecipeAdapter?
        ) {
            if (favoriteEntity.isNullOrEmpty()) {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }

                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                    }
                }
            } else {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }

                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoriteEntity)
                    }
                }
            }

        }
    }
}