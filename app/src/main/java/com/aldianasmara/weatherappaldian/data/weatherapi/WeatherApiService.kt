package com.aldianasmara.weatherappaldian.data.weatherapi

import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiResult
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchCurrentWeatherResponse
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchForecastResponse
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchHistoryResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    suspend fun fetchCurrentWeather(
        @Query("q") q: String? = null,
        @Query("aqi") aqi: String? = null
    ): Response<WeatherApiFetchCurrentWeatherResponse>

    @GET("history.json")
    suspend fun fetchHistory(
        @Query("q") q: String? = null,
        @Query("dt") dt: String? = null,
        @Query("end_dt") end_dt: String? = null
    ): Response<WeatherApiFetchHistoryResponse>

    @GET("forecast.json")
    suspend fun fetchWeatherForecast(
        @Query("q") q: String? = null,
        @Query("days") days: Int,
        @Query("aqi") aqi: String? = null,
        @Query("alerts") alerts: String? = null
    ): Response<WeatherApiFetchForecastResponse>


    companion object {

        private const val BASE_URL = "https://api.weatherapi.com/v1/"
        private const val API_KEY = "ea83e28784394934aa2103150210611"

        fun create(): WeatherApiService {

            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()

                val originalHttpUrl = chain.request().url()

                val url =
                    originalHttpUrl.newBuilder().addQueryParameter("key", API_KEY)
                        .build()

                request.url(url)

                return@addInterceptor chain.proceed(request.build())
            }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)

        }
    }

}