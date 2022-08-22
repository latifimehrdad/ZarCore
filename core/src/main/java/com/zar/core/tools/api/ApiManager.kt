package com.zar.core.tools.api

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.zar.core.enums.EnumAuthorizationType
import com.zar.core.enums.EnumErrorType
import com.zar.core.tools.api.interfaces.RemoteErrorEmitter
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Create by Mehrdad Latifi on 8/21/2022
 */


//-------------------------------------------------------------------------------------------------- apiCall
fun <T> apiCall(emitter: RemoteErrorEmitter, responseFunction: suspend () -> T) =  liveData {
    val response = privateApiCall(emitter) { responseFunction() }
    emit(response)
}
//-------------------------------------------------------------------------------------------------- apiCall



//-------------------------------------------------------------------------------------------------- privateApiCall
private suspend fun <T> privateApiCall(
    emitter: RemoteErrorEmitter,
    responseFunction: suspend () -> T
) = try {
    withTimeout(60000) {
        responseFunction()
    }
} catch (e: Exception) {
    exceptionHandle(e, emitter)
    null
}
//-------------------------------------------------------------------------------------------------- privateApiCall



//-------------------------------------------------------------------------------------------------- exceptionHandle
private fun exceptionHandle(e: Exception, emitter: RemoteErrorEmitter) {
    when (e) {
        is HttpException -> httpException(e, emitter)
        is TimeoutCancellationException -> emitter.onError(EnumErrorType.TimeOut, exceptionMessage(e))
        is IOException -> emitter.onError(EnumErrorType.Network, "لطفا اتصال دستگاه خود را به اینترنت چک کنید")
        else -> emitter.onError(EnumErrorType.UNKNOWN, exceptionMessage(e))
    }
}
//-------------------------------------------------------------------------------------------------- exceptionHandle



//-------------------------------------------------------------------------------------------------- exceptionMessage
private fun exceptionMessage(exception: Exception) = exception.message?.let {
    exception.message
} ?: "متاسفانه خطایی رخ داده، چند دقیقه بعد دوباره تلاش کنید"
//-------------------------------------------------------------------------------------------------- exceptionMessage



//-------------------------------------------------------------------------------------------------- httpException
private fun httpException(e: HttpException, emitter: RemoteErrorEmitter) {
    when (e.code()) {
        401 -> emitter.unAuthorization(
            EnumAuthorizationType.UnAuthorization,
            responseMessage(e.response())
        )
        403 -> emitter.unAuthorization(EnumAuthorizationType.UnAccess, responseMessage(e.response()))
        else -> emitter.onError(EnumErrorType.UNKNOWN, responseMessage(e.response()))
    }
}
//-------------------------------------------------------------------------------------------------- httpException



//-------------------------------------------------------------------------------------------------- responseMessage
private fun responseMessage(response: Response<*>?): String {
    val error = response?.errorBody()?.string()?.let {
        if (it.isEmpty())
            null
        else
            JSONObject(it)
    } ?: return "متاسفانه خطایی رخ داده، چند دقیقه بعد دوباره تلاش کنید"

    return if (!error.has("errors")) {
        val message = JSONObject(error.getString("message"))
        message.getString("Exception")
    } else {
        val message = error.getString("message")
        val errors = error.getString("errors")
            .let { Gson().fromJson(it, mutableListOf<String>().javaClass) }
        val sb = StringBuilder()
        sb.append(message)
        errors?.forEach { it ->
            run {
                sb.append("\n")
                sb.append(it)
            }
        }
        sb.toString()
    }
}
//-------------------------------------------------------------------------------------------------- responseMessage

