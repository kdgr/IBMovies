package com.kaandogruer.ibmovies.viewmodel

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaandogruer.ibmovies.IBMoviesApp
import com.kaandogruer.ibmovies.data.Status
import com.kaandogruer.ibmovies.data.local.AppDatabase
import com.kaandogruer.ibmovies.utils.ConnectivityReceiver
import com.kaandogruer.ibmovies.utils.Objects
import com.kaandogruer.ibmovies.utils.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by kaandogruer.
 */
/**
 * Connection status checking with broadcast. (isConnect).
 */
open class BaseViewModel : ViewModel(), ConnectivityReceiver.ConnectivityReceiverListener {

    internal open var status = MutableLiveData<Status>()
    internal var isConnect = MutableLiveData<Boolean>().default(true)
    private var networkReceiver: ConnectivityReceiver = ConnectivityReceiver(this)

    @Inject
    lateinit var appDatabase: AppDatabase

    init {
        IBMoviesApp.get()!!.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    @VisibleForTesting
    fun setLoading(mStatus: Status) {
        if (Objects.equals(status, mStatus)) {
            return
        }
        this.status.value = mStatus
    }

    override fun onNetworkConnectChanged(isConnected: Boolean) {
        if (Objects.equals(isConnect.value, isConnected)) {
            return
        }
        isConnect.value = isConnected
    }

    fun clearLocalDB() {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
    }

}
