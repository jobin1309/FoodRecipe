package com.example.foody_modern_food_recipe_app.ui.mainActivity.fragments.favoriteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.adapters.FavoriteRecipeAdapter
import com.example.foody_modern_food_recipe_app.databinding.FragmentFavoriteFragmentsRecipesBinding
import com.example.foody_modern_food_recipe_app.databinding.FragmentFavoriteFragmentsRecipesBindingImpl
import com.example.foody_modern_food_recipe_app.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragmentsRecipes : Fragment() {

    private val mAdapter: FavoriteRecipeAdapter by lazy { FavoriteRecipeAdapter(requireActivity()) }
    private val mViewModel: MainViewModel by viewModels()

    private var _binding: FragmentFavoriteFragmentsRecipesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteFragmentsRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mainViewModel = mViewModel
        binding.mAdapter = mAdapter

        setUpRecyclerView(binding.favoriteRecyclerView)

        mViewModel.readFavorites.observe(viewLifecycleOwner) { favoriteEntity ->
            mAdapter.setData(favoriteEntity)
        }

        return binding.root
    }


    fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter;
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}