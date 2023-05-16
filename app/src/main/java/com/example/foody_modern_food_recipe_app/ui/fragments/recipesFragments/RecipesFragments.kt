package com.example.foody_modern_food_recipe_app.ui.fragments.recipesFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody_modern_food_recipe_app.viewModels.MainViewModel
import com.example.foody_modern_food_recipe_app.adapters.RecipeAdapter
import com.example.foody_modern_food_recipe_app.databinding.FragmentRecipesFragmentsBinding
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.API_KEY
import com.example.foody_modern_food_recipe_app.util.NetworkResult
import com.example.foody_modern_food_recipe_app.util.observeOnce
import com.example.foody_modern_food_recipe_app.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragments : Fragment() {

    private var _binding: FragmentRecipesFragmentsBinding? = null
    private val binding get() = _binding!!


    private val mAdapter by lazy { RecipeAdapter() } //only create the instance when it is accessed
    private val mViewModel: MainViewModel by viewModels()
    private val recipeViewModel: RecipesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentRecipesFragmentsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner =
            this //we gonna use live data variables/object in RecipesFragment XML layout
        binding.mainViewModel =
            mViewModel //we need to access the Mainviewmodel to data-binding of Mainviewmodel variable name in xml

        setUpRecyclerView();
        readDatabase()
        return binding.root

    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect();
    }


    private fun readDatabase() {

        lifecycleScope.launch {
            mViewModel.readRecipe.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called")
                    mAdapter.setData(database[0].foodRecipe) //recipeEntity is the table of the database, which has only one array of objects, we are the list of food recipe
                    binding.shimmerLayout.stopShimmer()
                } else {
                    Log.d("RecipesResponse", "Api called")
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        mViewModel.getRecipes(recipeViewModel.applyQueries())
        mViewModel.recipeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = GONE

                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache() //if the user face some error, past data will show
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = GONE
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    binding.shimmerLayout.startShimmer();
                }
            }

        }
    }


    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mViewModel.readRecipe.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }

            }
        }
    }


    private fun showShimmerEffect() {
        binding.shimmerLayout.startShimmer()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null    //to avoid memory leaks
    }
}