package com.kaandogruer.ibmovies.data.repository

import androidx.lifecycle.LiveData

import com.kaandogruer.ibmovies.core.AppExecutors
import com.kaandogruer.ibmovies.data.NetworkBoundResource
import com.kaandogruer.ibmovies.data.Resource
import com.kaandogruer.ibmovies.data.local.dao.MovieDao
import com.kaandogruer.ibmovies.data.local.entity.Movie
import com.kaandogruer.ibmovies.data.remote.TMBDService
import com.kaandogruer.ibmovies.data.remote.model.basemodels.BaseApiResponse
import com.kaandogruer.ibmovies.data.remote.model.datamodels.responsemodels.TopRatedMovieList

import javax.inject.Inject

/**
 * Created by kaandogruer.
 */

/**
 * Talking to local datasource and remote datasource(web service)
 */

class MoviesRepository @Inject
internal constructor(private val appExecutors: AppExecutors,
                     val movieDao: MovieDao,
                     private val remoteService: TMBDService) {


    //get movies online&offline
    fun loadMovies(searchText: String,apiKey: String,page: Int, isConnect:Boolean): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, TopRatedMovieList>(appExecutors,isConnect) {

            override fun saveCallResult(item: TopRatedMovieList) {
                movieDao.insertAll(item.results)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                //u can run ur online or offline scenario
                return isConnect
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return if (searchText.isNullOrEmpty())
                    movieDao.movieList
                else
                    movieDao.searchMovie(searchText)
            }

            override fun createCall(): LiveData<BaseApiResponse<TopRatedMovieList>> {
                return remoteService.getTopRatedMovies(apiKey,page)
            }

        }.asLiveData()
    }
}
