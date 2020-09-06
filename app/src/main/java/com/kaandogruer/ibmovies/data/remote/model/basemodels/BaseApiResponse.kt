package com.kaandogruer.ibmovies.data.remote.model.basemodels

import android.util.Log
import retrofit2.Response
import java.io.IOException


class BaseApiResponse<T>  {
    var isSuccess: Boolean = true

    val code: Int
    val data: T?
    val statusMessage: String?
    val statusCode: String?

    constructor(error: Throwable) {
        statusCode = "500"
        code = 500
        data = null
        statusMessage = error.message
    }

    constructor(body: Response<T>) {
        statusCode = body.code().toString()
        code = body.code()
        if (body.isSuccessful) {
            data = body.body()
            statusMessage = null
        } else {
            var message: String? = null
            if (body.errorBody() != null) {
                try {
                    message = body.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Log.e("Parse", "error while parsing body")
                }

            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = body.message()
            }
            statusMessage = message
            data = null
        }

    }

    val isSuccessful: Boolean
        get() = code >= 200 && code < 300


}
