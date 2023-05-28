package com.example.foody_modern_food_recipe_app.ui.detailActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.databinding.FragmentOverviewBinding
import com.example.foody_modern_food_recipe_app.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    private lateinit var binding: FragmentOverviewBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val args = arguments
        val myBundle =
            args?.getParcelable<com.example.foody_modern_food_recipe_app.models.Result>(Constants.RECIPE_RESULT_KEY)

        binding.mainImageView.load(myBundle?.image)
        binding.TitleText.text = myBundle?.title
        binding.likesTextVIew.text = myBundle?.aggregateLikes.toString()
        binding.timeTextView.text = myBundle?.readyInMinutes.toString();
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text();
            binding.summaryTextView.text = summary
        }



        if (myBundle?.vegetarian == true) {
            binding.vegetarianImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.VegTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.vegan == true) {
            binding.veganImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.veganTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.veryHealthy == true) {
            binding.healthyImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.healthyTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.cheap == true) {
            binding.cheapImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.cheapText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.glutenFree == true) {
            binding.GlutenImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.GlutenText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        return binding.root
    }


}



