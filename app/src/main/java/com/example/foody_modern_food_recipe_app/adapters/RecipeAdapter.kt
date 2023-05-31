package com.example.foody_modern_food_recipe_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity
import com.example.foody_modern_food_recipe_app.databinding.RecipesRowLayoutBinding
import com.example.foody_modern_food_recipe_app.models.FoodRecipe
import com.example.foody_modern_food_recipe_app.util.RecipesDiffUtil


class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    private var recipes = emptyList<com.example.foody_modern_food_recipe_app.models.Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: com.example.foody_modern_food_recipe_app.models.Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
           return MyViewHolder.from(parent)
    }

//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
//        return MyViewHolder(binding)

//        return MyViewHolder.from(parent)


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }


    fun setData(newData: FoodRecipe) {
        val recipeDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }




}