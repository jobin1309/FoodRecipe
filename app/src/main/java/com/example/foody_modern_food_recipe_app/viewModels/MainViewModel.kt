package com.example.foody_modern_food_recipe_app.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.datastore.core.IOException
import androidx.lifecycle.*
import com.example.foody_modern_food_recipe_app.MyApplication
import com.example.foody_modern_food_recipe_app.data.Repository
import com.example.foody_modern_food_recipe_app.data.database.room.entities.RecipesEntity
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FavoriteEntity
import com.example.foody_modern_food_recipe_app.data.database.room.entities.FoodJokeEntity
import com.example.foody_modern_food_recipe_app.models.FoodJoke
import com.example.foody_modern_food_recipe_app.models.FoodRecipe
import com.example.foody_modern_food_recipe_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    application: MyApplication,
    private val repository: Repository
) : AndroidViewModel(application) {// android view-model has a application level context


    /**ROOM DATABASE */
    val readRecipe: LiveData<List<RecipesEntity>> =
        repository.local.readRecipes().asLiveData() //converting flow to livedata

    val readFavorites: LiveData<List<FavoriteEntity>> =
        repository.local.readFavoritesRecipes().asLiveData()

    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.local.readFoodJoke().asLiveData()


    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }


    fun insertFavRecipes(favoriteEntity: FavoriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipes(favoriteEntity)
        }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodJoke(foodJokeEntity)
        }
    }

    fun deleteFavRecipe(favoriteEntity: FavoriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoriteEntity)
        }

    fun deleteAllFavoriteRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAlFavorites()
        }

    /**RETROFIT */
//
//    var _status = MutableLiveData<RecipeApiClass>()
//    val status: LiveData<RecipeApiClass> = _status;

    //backing property
    val recipeResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    var foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    //NetworkResult is generic class which can handle multiple data types
    //NetworkResult type allows you to add more information to the result, such as an error message or other metadata,than response type

    private val _searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> =
        MutableLiveData();
    var searchRecipeResponse: LiveData<NetworkResult<FoodRecipe>> = _searchRecipesResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)

    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipeSafeCall(searchQuery)
    }

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodRecipesJokeResponse(response)

            } catch (e: IOException) {
                foodJokeResponse.value =
                    NetworkResult.Error("IO Error: Recipes not found due to IO")
            } catch (e: HttpException) {
                foodJokeResponse.value =
                    NetworkResult.Error("HTTP Error: Recipes not found duo to HTTP")
            } catch (e: Exception) {
                foodJokeResponse.value =
                    NetworkResult.Error("Unknown Error: Recipes not found to unknown")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No internet connection!")
        }
    }

    private suspend fun searchRecipeSafeCall(searchQuery: Map<String, String>) {
        _searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                _searchRecipesResponse.value = handleFoodRecipesResponse(response)

            } catch (e: IOException) {
                _searchRecipesResponse.value =
                    NetworkResult.Error("IO Error: Recipes not found due to IO")
            } catch (e: HttpException) {
                _searchRecipesResponse.value =
                    NetworkResult.Error("HTTP Error: Recipes not found duo to HTTP")
            } catch (e: Exception) {
                _searchRecipesResponse.value =
                    NetworkResult.Error("Unknown Error: Recipes not found to unknown")
            }
        } else {
            _searchRecipesResponse.value = NetworkResult.Error("No internet connection!")
        }
    }


    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipeResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipeResponse.value = handleFoodRecipesResponse(response)
                val foodRecipe = recipeResponse.value!!.data  //we only needed data not message
                if (foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: IOException) {
                // Handle IO exception separately
                recipeResponse.value = NetworkResult.Error("IO Error: Recipes not found due to IO")
            } catch (e: HttpException) {
                // Handle HTTP exception separately
                recipeResponse.value =
                    NetworkResult.Error("HTTP Error: Recipes not found due to HTTP")
            } catch (e: Exception) {
                // Handle other exceptions separately
                recipeResponse.value =
                    NetworkResult.Error("Unknown Error: Recipes not found due to unknown")
            }
        } else {
            recipeResponse.value = NetworkResult.Error("No internet connection!")
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

    private fun handleFoodRecipesJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }

            response.isSuccessful -> {
                val foodJoke = response.body()
                return NetworkResult.Success(foodJoke!!)
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