package com.example.composepokemondexproject.module

import com.example.composepokemondexproject.BuildConfig
import com.example.composepokemondexproject.data.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Create by Nguyen Thanh Toan on 10/6/21
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class ResModule {

    companion object {
        var apiInstance: ApiService? = null
    }

    @Provides
    @Singleton
    fun provideHttpClient(
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.interceptors().add(Interceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)
            requestBuilder.addHeader("Content-Type", "application/json")
//            requestBuilder.addHeader(
//                "Authorization",
//                "token 8dcc1542a16daaa11c0ed5c3b7add287fdb28ec1"
//            )
            val request = requestBuilder.build()
            chain
                .withConnectTimeout(40, TimeUnit.SECONDS)
                .withWriteTimeout(40, TimeUnit.SECONDS)
                .withReadTimeout(40, TimeUnit.SECONDS)
                .proceed(request)
        })

        clientBuilder.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        return  OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, jsonParser: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                jsonParser.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideJsonParser(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java).also {
            apiInstance = it
        }
    }
}
