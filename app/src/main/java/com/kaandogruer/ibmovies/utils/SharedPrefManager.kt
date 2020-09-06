package com.kaandogruer.ibmovies.utils

import android.content.SharedPreferences

import com.kaandogruer.ibmovies.IBMoviesApp


class SharedPrefManager {


    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor
    internal var PRIVATE_MODE = 0

    init {
        pref = IBMoviesApp.get()!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    companion object {
        private val PREF_NAME = "IBMOVIES_PREF"
        private val USER_NAME = "USER_NAME"
    }
}
