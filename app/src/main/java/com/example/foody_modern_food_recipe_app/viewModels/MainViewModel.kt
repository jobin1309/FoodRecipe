package com.example.foody_modern_food_recipe_app.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foody_modern_food_recipe_app.MyApplication
import com.example.foody_modern_food_recipe_app.data.Repository
import com.example.foody_modern_food_recipe_app.data.database.room.RecipesEntity
import com.example.foody_modern_food_recipe_app.models.FoodRecipe
import com.example.foody_modern_food_recipe_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject




@HiltViewModel
class MainViewModel @Inject constructor(
    application: MyApplication,
    private val repository: Repository
) : AndroidViewModel(application) {// android view-model has a application level context


    /**ROOM DATABASE */
    val readRecipe: LiveData<List<RecipesEntity>> =
        repository.local.readDatabase().asLiveData() //convertign flow to livedata


    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    /**RETROFIT */
//
//    var _status = MutableLiveData<RecipeApiClass>()
//    val status: LiveData<RecipeApiClass> = _status;

    //backing property
    private val _recipeResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var recipeResponse: LiveData<NetworkResult<FoodRecipe>> =
        _recipeResponse  //NetworkResult is generic class which can handle multiple data types
    //NetworkResult type allows you to add more information to the result, such as an error message or other metadata,than response type


    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {

        _recipeResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {

                val response = repository.remote.getRecipes(queries)
                _recipeResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipeResponse.value!!.data  //we only needed data not message


                if (foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }


            } catch (e: Exception) {
                _recipeResponse.value = NetworkResult.Error("Recipes not found")
            }
        } else {

            _recipeResponse.value = NetworkResult.Error("No internet connection!")
        }

    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }


    //Handling API responses
    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }

    }

}