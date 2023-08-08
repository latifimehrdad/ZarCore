package com.zar.core.tools.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.zar.core.enums.EnumInternetConnection
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Create by Mehrdad Latifi on 8/22/2022
 */

@Module
@InstallIn(ActivityComponent::class)
class InternetManager @Inject constructor(@ApplicationContext private val context: Context) {

    //______________________________________________________________________________________________ connection
    fun connection(): EnumInternetConnection {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities == null)
            EnumInternetConnection.NONE
        else {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                    EnumInternetConnection.CELLULAR

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                    EnumInternetConnection.WIFI

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) ->
                    EnumInternetConnection.WIFI

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                    EnumInternetConnection.WIFI

                else ->
                    EnumInternetConnection.NONE
            }
        }
    }
    //______________________________________________________________________________________________ connection


}