package com.example.foody_modern_food_recipe_app.ui.detailActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.navArgs
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.adapters.ViewPagerAdapter
import com.example.foody_modern_food_recipe_app.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {


    private val args by navArgs<DetailsActivityArgs>()
    private lateinit var binding: ActivityDetailsBinding;


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
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_Fav) {
//            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }


//    private fun saveToFavorites(item: MenuItem) {
//        val favoriteEntity = FavoriteEntity(args.result)
//        changeMenuItemColor(item, R.color.yellow)
//        Toast.makeText(this, "Recipe Saved", Toast.LENGTH_LONG).show()
//    }


    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}
