package com.zar.core.tools.hilt

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Create by Mehrdad Latifi on 8/22/2022
 */

@Module
@InstallIn(SingletonComponent::class)
class HiltProviders {

    //---------------------------------------------------------------------------------------------- provideSharedPreferences
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("secret_shared_prefs", Context.MODE_PRIVATE)
    }
    //---------------------------------------------------------------------------------------------- provideSharedPreferences


}