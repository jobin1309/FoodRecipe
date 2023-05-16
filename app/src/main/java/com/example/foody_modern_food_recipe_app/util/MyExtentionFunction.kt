package com.example.foody_modern_food_recipe_app.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/** This file for Observing the liveData once */

fun <T> LiveData<T>.observeOnce(LifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(LifecycleOwner, object : Observer<T> {

        override fun onChanged(value: T) {
            removeObserver(this)
            observer.onChanged(value)
        }
    })
}