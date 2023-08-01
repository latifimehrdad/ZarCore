package com.zar.core.tools.api

import androidx.lifecycle.MutableLiveData
import com.zar.core.enums.EnumApiError
import com.zar.core.models.ErrorApiModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

/**
 * Create by Mehrdad Latifi on 8/21/2022
 */


//-------------------------------------------------------------------------------------------------- apiCall
suspend fun <T> apiCall(responseFunction: suspend () -> T) =
    privateApiCall { responseFunction() }
//-------------------------------------------------------------------------------------------------- apiCall


//-------------------------------------------------------------------------------------------------- privateApiCall
private suspend fun <T> privateApiCall(
    responseFunction: suspend () -> T
) = try {
    withTimeout(60000) {
        responseFunction()
    }
} catch (e: TimeoutCancellationException) {
    null
}
//-------------------------------------------------------------------------------------------------- privateApiCall


//-------------------------------------------------------------------------------------------------- checkResponseError
fun checkResponseError(response: Response<*>?, liveData: MutableLiveData<ErrorApiModel>) {
    val message = responseMessage(response)
    when (response?.code()) {
        401 -> {
            val error = ErrorApiModel(EnumApiError.UnAuthorization, message)
            liveData.postValue(error)
        }

        403 -> {
            val error = ErrorApiModel(EnumApiError.UnAccess, message)
            liveData.postValue(error)
        }

        else -> {
            val error = ErrorApiModel(EnumApiError.Error, message)
            liveData.postValue(error)
        }
    }
}
//-------------------------------------------------------------------------------------------------- checkResponseError


//-------------------------------------------------------------------------------------------------- responseMessage
private fun responseMessage(response: Response<*>?): String {
    val error = response?.errorBody()?.string()?.let {
        if (it.isEmpty()) {
            if (response.message().isNullOrEmpty())
                null
            else
                return response.message()
        } else
            JSONObject(it)
    } ?: return "متاسفانه خطایی رخ داده، چند دقیقه بعد دوباره تلاش کنید"

    return if (!error.has("errors")) {
        error.getString("message")
    } else {
        val errors = error.getJSONObject("errors").toMap()
        val sb = StringBuilder()
        errors.forEach {
            run {
                sb.append(it.value)
                sb.append("\n")
            }
        }
        sb.toString()
    }
}
//-------------------------------------------------------------------------------------------------- responseMessage


//-------------------------------------------------------------------------------------------------- toMap
fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
    when (val value = this[it]) {
        is JSONArray -> {
            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
            JSONObject(map).toMap().values.toList()
        }

        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}
//-------------------------------------------------------------------------------------------------- toMap
