package com.example.foody_modern_food_recipe_app.ui.detailActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.databinding.FragmentInstructionsBinding
import com.example.foody_modern_food_recipe_app.util.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle =
            args?.getParcelable<com.example.foody_modern_food_recipe_app.models.Result>(
                Constants.RECIPE_RESULT_KEY
            )


        binding.InstructionWebView.webViewClient = object : WebViewClient(){}

        val websiteUrl: String = myBundle!!.sourceUrl

        binding.InstructionWebView.loadUrl(websiteUrl)



        return binding.root

    }
}