package com.example.foody_modern_food_recipe_app.ui.fragments.foodJokeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foody_modern_food_recipe_app.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodJokesFragments : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_jokes_fragments, container, false)
    }


}