package com.example.foody_modern_food_recipe_app.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UninstallHandle : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_PACKAGE_REMOVED) {
            // No code inside the if condition
        }
    }
}