package com.zar.core.tools.hilt

import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
import javax.inject.Named
import javax.inject.Singleton

/**
 * Create by Mehrdad Latifi on 8/21/2022
 * Create by Mehrdad Latifi on 8/21/2022
 */

@Module
@InstallIn(SingletonComponent::class)
class RetrofitProvider {

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
        .connectTimeout(20, TimeUnit.SECONDS)
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
        val originalResponse = chain.proceed(newRequest)
        originalResponse.newBuilder()
            .body(ProgressResponseBody(originalResponse.body!!) { _, _, _ -> })
            .build()
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