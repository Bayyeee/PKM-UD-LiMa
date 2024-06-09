package com.project.pkm_ud_lima.data.retrofit

import com.project.pkm_ud_lima.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Konfigurasi retrofit

class ApiConfig {
    companion object {
        private fun getRetrofit(baseUrl: String): Retrofit {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        fun getWeatherService(): ApiService {
            return getRetrofit("https://api.openweathermap.org/").create(ApiService::class.java)
        }

        fun getFlameService(): ApiService {
            return getRetrofit("https://fireguardudlima.000webhostapp.com/").create(ApiService::class.java)
        }
    }
}