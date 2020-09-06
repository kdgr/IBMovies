package com.kaandogruer.ibmovies.core

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import com.kaandogruer.ibmovies.utils.Constants
import java.io.Serializable

/**
 * Base fragment with usefull functions.
 *
 * Created by kaandogruer
 */

abstract class BaseFragment : Fragment(){

    protected lateinit var progress : ProgressDialog

    companion object {

        fun <T> newInstance(fragment: T  , o : Any? = null ) : T {
            var bundle = Bundle()

            if (o != null) {
                when (o) {
                    is String -> bundle.putString(Constants.FRAGMENT_DATA, o)
                    is Boolean -> bundle.putBoolean(Constants.FRAGMENT_DATA, o)
                    is Int -> bundle.putInt(Constants.FRAGMENT_DATA, o)
                    is Float -> bundle.putFloat(Constants.FRAGMENT_DATA, o)
                    is Byte -> bundle.putByte(Constants.FRAGMENT_DATA, o)
                    is ByteArray -> bundle.putByteArray(Constants.FRAGMENT_DATA, o)
                    is Char -> bundle.putChar(Constants.FRAGMENT_DATA, o)
                    is Short -> bundle.putShort(Constants.FRAGMENT_DATA, o)
                    is IntArray -> bundle.putIntArray(Constants.FRAGMENT_DATA, o)
                    is Parcelable -> bundle.putParcelable(Constants.FRAGMENT_DATA, o)
                    is Serializable -> bundle.putSerializable(Constants.FRAGMENT_DATA, o)
                }
            }

            (fragment as Fragment).arguments = bundle
            return fragment
        }


    }


    fun showProgress(){
        progress = ProgressDialog(activity!!)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)
        progress.show()
    }

    fun hideProgress(){
        try {
            if (progress.isShowing){
                progress.dismiss()
            }
        }catch (ex : Exception){
            Log.d("Progress","${ex.message}")
        }
    }


    fun shareCoorinate(mAddress : String?){
        /*
              val url = "http://maps.google.com/maps?daddr=" + lat + "," + lng
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
              startActivity(intent)
        */

        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Konumum = \n${mAddress!!}")
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

}