package com.zar.core.tools.hilt

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Create by Mehrdad Latifi on 8/22/2022
 */

@Module
@InstallIn(SingletonComponent::class)
class HiltProviders {

    //---------------------------------------------------------------------------------------------- provideRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        baseUrl: String,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
    //---------------------------------------------------------------------------------------------- provideRetrofit



    //---------------------------------------------------------------------------------------------- provideHttpClient
    @Provides
    @Singleton
    fun provideHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient()
        .newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .build()
    //---------------------------------------------------------------------------------------------- provideHttpClient



    //---------------------------------------------------------------------------------------------- provideInterceptor
    @Provides
    @Singleton
    fun provideInterceptor() = Interceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("device", "android")
            .build()
        chain.proceed(newRequest)
    }
    //---------------------------------------------------------------------------------------------- provideInterceptor


    //---------------------------------------------------------------------------------------------- provideLoggingInterceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    //---------------------------------------------------------------------------------------------- provideLoggingInterceptor


    //---------------------------------------------------------------------------------------------- provideGson
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().registerTypeAdapter(
        LocalDateTime::class.java,
        LocalDateTimeDeserializerManager()
    ).create()
    //---------------------------------------------------------------------------------------------- provideGson


    //---------------------------------------------------------------------------------------------- provideSharedPreferences
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("secret_shared_prefs", Context.MODE_PRIVATE)
    }
    //---------------------------------------------------------------------------------------------- provideSharedPreferences



    //---------------------------------------------------------------------------------------------- LocalDateTimeDeserializerManager
    class LocalDateTimeDeserializerManager : JsonDeserializer<LocalDateTime> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): LocalDateTime {
            return LocalDateTime.parse(json?.asString, DateTimeFormatter.ISO_DATE_TIME)
        }

    }
    //---------------------------------------------------------------------------------------------- LocalDateTimeDeserializerManager


}