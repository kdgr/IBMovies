package com.kaandogruer.ibmovies.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


class GenericHelper private constructor() {
    fun hideKeyboard(ctx: Context) {
        val inputManager = ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun openKeyboard(ctx: Context) {
        val inputManager = ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.toggleSoftInputFromWindow(
                v.applicationWindowToken,
                InputMethodManager.SHOW_FORCED, 0)
    }

    companion object {

        private var instance: GenericHelper? = null
        fun getInstance(): GenericHelper {
            if (instance == null) {
                instance = GenericHelper()
            }
            return instance as GenericHelper
        }
    }

}
