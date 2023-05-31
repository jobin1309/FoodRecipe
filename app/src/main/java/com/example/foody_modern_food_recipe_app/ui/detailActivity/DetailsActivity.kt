package com.example.foody_modern_food_recipe_app.ui.detailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.navArgs
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.adapters.ViewPagerAdapter
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity
import com.example.foody_modern_food_recipe_app.databinding.ActivityDetailsBinding
import com.example.foody_modern_food_recipe_app.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()
    private val args by navArgs<DetailsActivityArgs>()
    private lateinit var binding: ActivityDetailsBinding;


    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)


        /** For setting up toolbar */
        setSupportActionBar(binding.toolbar);

        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /** For setting up Bundle data between fragments with viewpager for scrolling*/

        val fragments = ArrayList<Fragment>()

        fragments.add((OverviewFragment()))
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()

        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = ViewPagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager  //fragment manager
        )

        binding.viewPager.adapter = adapter

        binding.tabLayout.setupWithViewPager(binding.viewPager)

        setContentView(binding.root)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_Fav)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mViewModel.readFavorites.observe(this) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.id == args.result.id) {  //if the selected recipe equals to any of the items in the DB
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_Fav && !recipeSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_Fav && recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveToFavorites(item: MenuItem) {
        val favoriteEntity = FavoriteEntity(0, args.result)
        mViewModel.insertFavRecipes(favoriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        Toast.makeText(this, "Recipe Saved", Toast.LENGTH_LONG).show()

        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoriteEntity = FavoriteEntity(savedRecipeId, args.result)
        mViewModel.deleteFavRecipe(favoriteEntity)
        changeMenuItemColor(item, R.color.white)

        Toast.makeText(this, "Item removed", Toast.LENGTH_LONG).show()
        recipeSaved = false
    }

    private fun deleteAllFromFav() {

        mViewModel.deleteAllFavoriteRecipes()
        Toast.makeText(this, "All Favorites recipes cleared", Toast.LENGTH_LONG).show()

    }


    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}
