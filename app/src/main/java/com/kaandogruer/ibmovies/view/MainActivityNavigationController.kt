package com.kaandogruer.ibmovies.view

import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.core.BaseFragment
import com.kaandogruer.ibmovies.core.navigator.BaseActivityNavigationController
import com.kaandogruer.ibmovies.view.list.TopRatedMoviesFragment
import javax.inject.Inject

class MainActivityNavigationController @Inject constructor(mainActivity: MainActivity) : BaseActivityNavigationController(mainActivity) {

    var activeFragment: BaseFragment? = null

    init {
        this.containerId = R.id.flContainer

    }

    fun navigateToTopRatedList() {
        clearHistory()
        activeFragment = BaseFragment.newInstance(TopRatedMoviesFragment())
        replace(activeFragment!!)
    }


}