package com.zar.core.tools.manager

import android.content.Context
import android.os.Build
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Inject

/**
 * Create by Mehrdad Latifi on 8/22/2022
 */

@Module
@InstallIn(SingletonComponent::class)
class DeviceManager@Inject constructor(
    @ApplicationContext private val context: Context) {

    //---------------------------------------------------------------------------------------------- appVersionCode
    fun appVersionCode(): Long = try {
        if (Build.VERSION.SDK_INT >= 28)
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode
        else
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toLong()
    } catch (e: Exception) {
        0
    }
    //---------------------------------------------------------------------------------------------- appVersionCode


    //---------------------------------------------------------------------------------------------- appVersionName
    fun appVersionName(): String = try {
        @Suppress("DEPRECATION")
        context.packageManager.getPackageInfo(context.packageName, 0).versionName
    } catch (e: Exception) {
        "0.0.0"
    }
    //---------------------------------------------------------------------------------------------- appVersionName


    //---------------------------------------------------------------------------------------------- androidVersion
    fun androidVersion(): String = try {
        val release = Build.VERSION.RELEASE
        val sdkVersion = Build.VERSION.SDK_INT
        "Android Version is $release & SDK Version is $sdkVersion"
    } catch (e: Exception) {
        "Android Version : ${e.message}"
    }
    //---------------------------------------------------------------------------------------------- androidVersion


    //---------------------------------------------------------------------------------------------- deviceBrand
    fun deviceBrand(): String = try {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        model.replace("-", "_")
        if (model.startsWith(manufacturer))
            model.replaceFirstChar { it.uppercase() }
        else
            "Device Brand : ${manufacturer.replaceFirstChar { it.uppercase() }} ${model.replaceFirstChar { it.uppercase() }}"
    } catch (e: java.lang.Exception) {
        "Device Brand : ${e.message}"
    }
    //---------------------------------------------------------------------------------------------- deviceBrand



    //---------------------------------------------------------------------------------------------- deviceLanguage
    fun deviceLanguage(): String = try {
        val language = Locale.getDefault().displayLanguage
        "Device Language : $language"
    } catch (e: Exception) {
        "Device Language : ${e.message}"
    }
    //---------------------------------------------------------------------------------------------- deviceLanguage



}