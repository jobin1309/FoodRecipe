package com.example.foody_modern_food_recipe_app.ui.mainActivity.fragments.favoriteFragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.adapters.FavoriteRecipeAdapter
import com.example.foody_modern_food_recipe_app.databinding.FragmentFavoriteFragmentsRecipesBinding
import com.example.foody_modern_food_recipe_app.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragmentsRecipes : Fragment() {

    private val mViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteRecipeAdapter by lazy {
        FavoriteRecipeAdapter(
            requireActivity(),
            mViewModel
        )
    }


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


        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fav_delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fav_delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }


    fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter;
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Delete Everything")
        builder.setMessage("Are you sure you want to delete everything!")

        builder.setPositiveButton("Yes") { _, _ ->
            mViewModel.deleteAllFavoriteRecipes()
            Toast.makeText(context, "Removed Favorites", Toast.LENGTH_LONG).show()

        }

        builder.setNegativeButton("NO") { _, _ -> }
        builder.create().show();

    }
}