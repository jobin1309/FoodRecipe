package com.example.foody_modern_food_recipe_app.util

class Constants {

    companion object {

        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_250x250/"
        const val API_KEY = "945d17ecc0f74f4b98f3c42576b4dced"


        const val RECIPE_RESULT_KEY = "recipeBundle"


        //API Query keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_RECIPE_INFO = "addRecipeInformation"
        const val QUERY_INGREDIENTS = "fillIngredients"


        //ROOM DataBase

        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITE_RECIPES_TABLE = "favorite_recipe_table"
        const val FOOD_JOKE_TABLE = "food_joke_table"

        //Bottom sheet and preferences

        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "Whole30"


        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"

        const val PREFERENCES_NAME = "foody_preferences"



    }
}