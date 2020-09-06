package com.kaandogruer.ibmovies.core.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.util.Log

/**
 * Created by kaandogruer
 *
 */


abstract class BaseNavigationController {

    protected var containerId: Int = 0
    var fragmentManager: FragmentManager? = null

    protected fun add(fragment: Fragment) {
        fragmentManager!!.beginTransaction()
                .add(containerId, fragment, fragment::class.java.simpleName)
                .addToBackStack(fragment::class.java.simpleName)
                .commitAllowingStateLoss()
    }

    protected fun replace(fragment: Fragment) {
        fragmentManager!!.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    protected fun clearHistory() {
        try {
            fragmentManager!!.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } catch (ignored: IllegalStateException) {
            ignored.message?.let { Log.d("ignore", it) }
        }
    }
}