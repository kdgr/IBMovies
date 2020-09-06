package com.kaandogruer.ibmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

import com.kaandogruer.ibmovies.data.Resource
import com.kaandogruer.ibmovies.data.local.entity.Movie
import com.kaandogruer.ibmovies.data.remote.model.datamodels.requestmodels.TopRatedMoviesRequestModel
import com.kaandogruer.ibmovies.data.repository.MoviesRepository
import com.kaandogruer.ibmovies.utils.AbsentLiveData
import com.kaandogruer.ibmovies.utils.Objects

import javax.inject.Inject

/**
 * Created by kaandogruer.
 */
class MoviesViewModel @Inject
constructor(var moviesRepository: MoviesRepository) : BaseViewModel() {

    internal var topRatedMoviesRequestLiveData : MutableLiveData<TopRatedMoviesRequestModel>

    val topRatedMoviesResponseLiveData: LiveData<Resource<List<Movie>>>

    init {
        topRatedMoviesRequestLiveData = MutableLiveData()
        this.topRatedMoviesResponseLiveData = Transformations.switchMap(topRatedMoviesRequestLiveData) {
            when (it) {
                null -> AbsentLiveData.create()
                else -> moviesRepository.loadMovies(it.query!!,it.apiKey!!,it.page!!, isConnect.value!!)
            }
        }
    }

    fun setTopRatedMoviesRequest(requestModel: TopRatedMoviesRequestModel) {
        if (Objects.equals(topRatedMoviesRequestLiveData.value, requestModel)) {
            return
        }
        topRatedMoviesRequestLiveData.value = requestModel
    }


}
