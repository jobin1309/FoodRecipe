package com.example.foody_modern_food_recipe_app.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.models.ExtendedIngredient
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foody_modern_food_recipe_app.util.RecipesDiffUtil
import java.util.*
import kotlin.collections.List

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {


     private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredients_row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.ingredientImageView)
            .load(BASE_IMAGE_URL + ingredientsList[position].image)
        holder.itemView.findViewById<TextView>(R.id.ingredient_main).text =
            ingredientsList[position].name.capitalize(
                Locale.ROOT
            )
        holder.itemView.findViewById<TextView>(R.id.ingredient_unit_num).text =
            ingredientsList[position].amount.toString()
        holder.itemView.findViewById<TextView>(R.id.ingredient_unit).text =
            ingredientsList[position].unit
        holder.itemView.findViewById<TextView>(R.id.ingredient_consistency).text =
            ingredientsList[position].consistency
        holder.itemView.findViewById<TextView>(R.id.ingredient_original).text =
            ingredientsList[position].original

    }

    fun setData(newIngredient: List<ExtendedIngredient>) {
        val recipeDiffUtil = RecipesDiffUtil(ingredientsList, newIngredient)
        val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUtil)
        ingredientsList = newIngredient
        diffUtilResult.dispatchUpdatesTo(this)
    }

}