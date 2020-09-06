/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kaandogruer.ibmovies

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment


import com.kaandogruer.ibmovies.di.AppInjector

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector


/**
 * Android Application class. Used for accessing singletons.
 */
class IBMoviesApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>



    override fun onCreate() {
        super.onCreate()
        instance = this
        sContext = this
        AppInjector.init(this)

        sInstance = this

    }


    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return mFragmentInjector
    }

    companion object {
        lateinit var sContext : Context
        lateinit var instance: IBMoviesApp
        private var sInstance: IBMoviesApp? = null
        var isBackCount : Int = 0

        fun get(): IBMoviesApp? {
            return sInstance
        }
    }
}
