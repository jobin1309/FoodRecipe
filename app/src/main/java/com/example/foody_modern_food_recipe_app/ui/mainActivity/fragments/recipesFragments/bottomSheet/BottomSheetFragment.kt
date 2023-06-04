package com.example.foody_modern_food_recipe_app.ui.mainActivity.fragments.recipesFragments.bottomSheet

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.databinding.FragmentBottomSheetBinding
import com.example.foody_modern_food_recipe_app.util.Constants
import com.example.foody_modern_food_recipe_app.viewModels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding

    private val recipesViewModel: RecipesViewModel by viewModels()

    private var mealTypeChip = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = Constants.DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0;




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)


        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)

        }


        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedMealType = chip?.text?.toString()?.toLowerCase(Locale.ROOT)
            // Rest of your code here
            if (selectedMealType != null) {
                mealTypeChip =  selectedMealType
                mealTypeChipId = checkedId
            }
        }

        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedDietType = chip?.text?.toString()?.toLowerCase(Locale.ROOT)
            // Rest of your code here
            if (selectedDietType != null) {
                dietTypeChip =  selectedDietType
                dietTypeChipId = checkedId
            }
        }

        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            val action = BottomSheetFragmentDirections.actionBottomSheetFragmentToRecipesFragments(true)
            findNavController().navigate(action)
        }


        return binding.root
    }

    private fun updateChip(selectedDietTypeId: Int, dietTypeChipGroup: ChipGroup) {
            if(selectedDietTypeId != 0) {
                try {
                    dietTypeChipGroup.findViewById<Chip>(selectedDietTypeId).isChecked = true
                }
                catch (e: Exception) {
                    Log.d("RecipesBottomSheet", e.message.toString())
                }
            }
    }


}