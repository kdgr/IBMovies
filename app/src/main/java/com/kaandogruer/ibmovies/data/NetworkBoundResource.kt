package com.kaandogruer.ibmovies.data

import androidx.lifecycle.LiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.MediatorLiveData

import com.kaandogruer.ibmovies.core.AppExecutors
import com.kaandogruer.ibmovies.IBMoviesApp
import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.data.remote.model.basemodels.BaseApiResponse

/**
 * Created by kaandogruer.
 */

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
protected constructor(private val appExecutors: AppExecutors,private val isConnect:Boolean)  {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {

        val dbSource = loadFromDb()

        result.addSource(dbSource) { resultType ->
            result.removeSource(dbSource)
            if (shouldFetch(resultType)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { rT ->
                    if(isConnect)
                        result.value = Resource.success(rT)
                    else
                        result.value = Resource.error(500,IBMoviesApp.get()!!.getString(R.string.msg_null_response), rT)
                }
            }
        }

    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) {
            resultType ->
            result.value = Resource.loading(resultType)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response!!.isSuccess || response.code == 200) {
                appExecutors.diskIO().execute {
                    saveCallResult(processResponse(response)!!)
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDb()
                        ) { newData -> result.value = (Resource.success(newData)) }
                    }
                }
            } else {
                onFetchFailed()
                result.addSource(dbSource) {
                    newData ->

                }
            }
        }
    }

    protected open fun onFetchFailed() {

    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }


    @WorkerThread
    private fun processResponse(response: BaseApiResponse<RequestType>): RequestType? {
        return response.data
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<BaseApiResponse<RequestType>>

}
