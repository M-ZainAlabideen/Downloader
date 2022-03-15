package com.downloader.network

import com.downloader.classes.Constants
import com.downloader.model.response.FileResponse
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ApiInterface {

    companion object {
        operator fun invoke(): ApiInterface {


            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()

            logging.setLevel(HttpLoggingInterceptor.Level.BODY)


            httpClient.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)



            httpClient.addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept-Language", "en")
                    .build()
                chain.proceed(request)
            }

            httpClient.addInterceptor(logging)

            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL)
                .client(httpClient.build())
                .build()
                .create(ApiInterface::class.java)

        }
    }

    @GET("getListOfFilesResponse.json")
    suspend fun getFiles(): Response<ArrayList<FileResponse>>
}