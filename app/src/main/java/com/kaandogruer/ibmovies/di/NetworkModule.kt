package com.kaandogruer.ibmovies.di

import com.google.gson.GsonBuilder
import com.kaandogruer.ibmovies.BuildConfig
import com.kaandogruer.ibmovies.IBMoviesApp
import com.kaandogruer.ibmovies.data.remote.*
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    private val CACHE_SIZE = 10L
    private val CACHE_MEMORY_SIZE = 1024L
    private val TIMEOUT_SESSION = 30L



    @Provides
    @Singleton
    fun provideCache(app: IBMoviesApp): Cache {
        // 10 MB Cache for Network Unavailable
        return Cache(File(IBMoviesApp.sContext.cacheDir, "https"), CACHE_SIZE * CACHE_MEMORY_SIZE * CACHE_MEMORY_SIZE)
    }

    @Singleton
    @Provides
    fun provideBaseService(@Named("SERVICE_RETROFIT") retrofit: Retrofit): TMBDService {
        val remoteService = retrofit.create(TMBDService::class.java)
        return remoteService
    }


    @Singleton
    @Provides
    @Named("SERVICE_OKHHTP")
    fun provideOKHttpClient(dispatcher: Dispatcher,
                            cache: Cache): OkHttpClient {

        dispatcher.maxRequests = 4

        return OkHttpClient.Builder().apply {
            this.cache(cache)
            this.connectTimeout(TIMEOUT_SESSION, TimeUnit.HOURS)
            this.writeTimeout(TIMEOUT_SESSION, TimeUnit.HOURS)
            this.readTimeout(TIMEOUT_SESSION, TimeUnit.HOURS)
            if (BuildConfig.DEBUG) { //request&response log if debug mode
                this.addInterceptor(ChuckInterceptor(IBMoviesApp.get()?.applicationContext))
            }
            this.dispatcher(dispatcher)
        }.build()
    }


    @Singleton
    @Provides
    @Named("SERVICE_RETROFIT")
    fun provideRetrofit(@Named("SERVICE_OKHHTP") client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        return Retrofit.Builder()
                .baseUrl(RemoteConstants.mBase)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

}