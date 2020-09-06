package com.kaandogruer.ibmovies.data.remote

import androidx.lifecycle.LiveData
import com.kaandogruer.ibmovies.data.remote.model.basemodels.BaseApiResponse
import com.kaandogruer.ibmovies.data.remote.model.datamodels.responsemodels.TopRatedMovieList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface TMBDService {
    @GET(RemoteConstants.mTopRatedMovies)
    fun getTopRatedMovies(@Query("api_key") apiKey: String,
                 @Query("page") page: Int): LiveData<BaseApiResponse<TopRatedMovieList>>


}
