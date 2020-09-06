package com.kaandogruer.ibmovies.view

import android.os.Bundle
import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.core.BaseInjectableActivity
import com.kaandogruer.ibmovies.databinding.ActivityMainBinding
import com.kaandogruer.ibmovies.viewmodel.MoviesViewModel
import javax.inject.Inject


class MainActivity : BaseInjectableActivity<MoviesViewModel, ActivityMainBinding>() {
    override val layoutResourceId = R.layout.activity_main
    override val viewModelClass: Class<MoviesViewModel> = MoviesViewModel::class.java

    @Inject
    lateinit var mainNavigator: MainActivityNavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        mainNavigator.navigateToTopRatedList()
    }

    override fun onBackPressed() {
        if(mainNavigator.fragmentManager!!.backStackEntryCount > 1){
            mainNavigator.fragmentManager!!.popBackStack()
            setStateOnBack()
        }else{

        }
    }
    fun setStateOnBack() {
        if(getCurrentFragmentTag() != null && !getCurrentFragmentTag()!!.isEmpty()){

        }
    }

    private fun getCurrentFragmentTag(): String? {
        val fragmentTag = mainNavigator.fragmentManager!!.getBackStackEntryAt(mainNavigator.fragmentManager!!.backStackEntryCount - 1).name
        return fragmentTag
    }

}
