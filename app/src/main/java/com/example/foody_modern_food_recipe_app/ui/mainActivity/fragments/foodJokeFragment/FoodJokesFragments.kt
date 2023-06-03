package com.example.foody_modern_food_recipe_app.ui.mainActivity.fragments.foodJokeFragment

import android.content.Intent
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.databinding.FragmentFoodJokesFragmentsBinding
import com.example.foody_modern_food_recipe_app.util.Constants.Companion.API_KEY
import com.example.foody_modern_food_recipe_app.util.NetworkResult
import com.example.foody_modern_food_recipe_app.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodJokesFragments : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentFoodJokesFragmentsBinding? = null
    private val binding get() = _binding

    private var foodJoke = "No food joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentFoodJokesFragmentsBinding.inflate(inflater, container, false)

         binding?.lifecycleOwner = viewLifecycleOwner
         binding?.mainViewModel = mainViewModel

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) {response ->
            when(response) {
                is NetworkResult.Success -> {
                    binding?.foodJokeTextView?.text = response?.data?.text
                    if(response.data != null) {
                        foodJoke = response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment", "Loading")
                }
            }
        }

        setHasOptionsMenu(true)

        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.foodjokemenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share_food_joke_menu) {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke )
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }




    private fun loadDataFromCache() {
        mainViewModel.readFoodJoke.observe(viewLifecycleOwner) {database ->
            if(database.isNotEmpty() && database != null) {
                binding?.foodJokeTextView?.text = database[0].foodJoke.text
                foodJoke = database[0].foodJoke.text
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}