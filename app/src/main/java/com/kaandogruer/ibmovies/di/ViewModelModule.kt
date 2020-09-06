package com.kaandogruer.ibmovies.di

import androidx.lifecycle.ViewModel
import com.kaandogruer.ibmovies.viewmodel.*

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    internal abstract fun bindMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel
}
