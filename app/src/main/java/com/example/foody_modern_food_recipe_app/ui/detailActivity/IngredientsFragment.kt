package com.example.foody_modern_food_recipe_app.ui.detailActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.adapters.IngredientAdapter
import com.example.foody_modern_food_recipe_app.databinding.FragmentIngredientsBinding
import com.example.foody_modern_food_recipe_app.util.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientAdapter by lazy { IngredientAdapter() }
    private lateinit var binding: FragmentIngredientsBinding;



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle =
            args?.getParcelable<com.example.foody_modern_food_recipe_app.models.Result>(
                Constants.RECIPE_RESULT_KEY
            )

        //        recyclerview setup
        binding.ingredientRecyclerView.adapter = mAdapter
        binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(context)
//

        myBundle?.extendedIngredients?.let { mAdapter.setData((it)) }


        return  binding.root
    }

}