package com.kaandogruer.ibmovies.di

import com.kaandogruer.ibmovies.view.detail.MovieDetailFragment
import com.kaandogruer.ibmovies.view.list.TopRatedMoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by kaandogruer.
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun contributeTopRatedMoviesFragment(): TopRatedMoviesFragment
    @ContributesAndroidInjector
    internal abstract fun contributeDetailFragment(): MovieDetailFragment

}
