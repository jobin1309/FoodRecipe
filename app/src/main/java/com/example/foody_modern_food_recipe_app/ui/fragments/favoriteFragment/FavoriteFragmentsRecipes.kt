package com.example.foody_modern_food_recipe_app.ui.fragments.favoriteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foody_modern_food_recipe_app.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragmentsRecipes : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_fragments_recipes, container, false)
    }


}