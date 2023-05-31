package com.example.foody_modern_food_recipe_app.adapters

import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity
import com.example.foody_modern_food_recipe_app.databinding.FavoriteRowLayoutBinding
import com.example.foody_modern_food_recipe_app.ui.mainActivity.fragments.favoriteFragment.FavoriteFragmentsRecipesDirections
import com.example.foody_modern_food_recipe_app.util.RecipesDiffUtil
import com.google.android.material.card.MaterialCardView


class FavoriteRecipeAdapter(private val requireActivity: FragmentActivity) : RecyclerView.Adapter<FavoriteRecipeAdapter.mViewHolder>(), ActionMode.Callback {


    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoriteEntity>()
    private var mViewHolders = arrayListOf<mViewHolder>()
    private var favoriteRecipes = emptyList<FavoriteEntity>()


    private var favoritesRecipes = emptyList<FavoriteEntity>()

    class mViewHolder(private val binding: FavoriteRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteEntity: FavoriteEntity) {
            binding.favoriteEntity = favoriteEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): mViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRowLayoutBinding.inflate(layoutInflater, parent, false)
                return mViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): mViewHolder {

        return mViewHolder.from(parent)

    }


    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        mViewHolders.add(holder)
        val currentRecipes = favoritesRecipes[position]
        holder.bind(currentRecipes)


        /** Single click listener */
        holder.itemView.findViewById<ConstraintLayout>(R.id.Fav_recipe_row_layout).setOnClickListener {
            if(multiSelection) {
                applySelection(holder, currentRecipes)
            }
            else {
                val action =
                    FavoriteFragmentsRecipesDirections.actionFavoriteFragmentsRecipesToDetailsActivity(
                        currentRecipes.result
                    )

                holder.itemView.findNavController().navigate(action)
            }
        }

        /**Long click listenter */

        holder.itemView.findViewById<ConstraintLayout>(R.id.Fav_recipe_row_layout).setOnLongClickListener {
            if(!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                true
            }
            else {
                multiSelection = false
                false
            }
        }

    }

    private fun applySelection(holder: mViewHolder, currentRecipes: FavoriteEntity) {
        if(selectedRecipes.contains(currentRecipes)) {
            selectedRecipes.remove(currentRecipes)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        }else {
            selectedRecipes.add(currentRecipes)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(
        holder: mViewHolder,
        cardBackgroundColor: Int,
        strokeColor: Int
    ) {
       holder.itemView.findViewById<ConstraintLayout>(R.id.Fav_recipe_row_layout).setBackgroundColor(
           ContextCompat.getColor(requireActivity, cardBackgroundColor)
       )
        holder.itemView.findViewById<MaterialCardView>(R.id.fav_materialCardView).strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)

    }


    private fun applyActionModeTitle() {
        when(selectedRecipes.size) {
            0 -> {

            }
        }

    }

    override fun getItemCount(): Int {
        return favoritesRecipes.size
    }



    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        mViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)

        }
        multiSelection = false
        selectedRecipes.clear()
      applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)

    }
    fun setData(newData: List<FavoriteEntity>) {
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoritesRecipes, newData)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoritesRecipes = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}