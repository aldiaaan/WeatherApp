package com.aldianasmara.weatherappaldian.data.weatherapi

import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiResult
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchCurrentWeatherResponse
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchForecastResponse
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchHistoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApiRepository @Inject constructor(private val service: WeatherApiService) {

    suspend fun fetchLocationsCurrentWeather(location: String): Flow<WeatherApiResult<WeatherApiFetchCurrentWeatherResponse>> {

        return flow {
            emit(WeatherApiResult.loading())
            emit(
                getResult(
                    request = { service.fetchCurrentWeather(q = "$location", aqi = "yes") },
                )
            )
        }.flowOn(Dispatchers.IO)

    }

    suspend fun fetchHistory(location: String, dt: String, end_dt: String): Flow<WeatherApiResult<WeatherApiFetchHistoryResponse>> {

        return flow {
            emit(WeatherApiResult.loading())
            emit(
                getResult(
                    request = { service.fetchHistory(q = "$location", dt = dt, end_dt = end_dt) },
                )
            )
        }.flowOn(Dispatchers.IO)

    }

    suspend fun fetchWeatherForecast(
        location: String,
        days: Int
    ): Flow<WeatherApiResult<WeatherApiFetchForecastResponse>> {

        return flow {
            emit(WeatherApiResult.loading())
            emit(
                getResult(
                    request = { service.fetchWeatherForecast(q = "$location", days = days) },
                )
            )
        }.flowOn(Dispatchers.IO)

    }

    private suspend fun <T> getResult(
        request: suspend () -> Response<T>,
    ): WeatherApiResult<T> {
        return try {

            val result = request.invoke()

            if (result.isSuccessful) {
                WeatherApiResult.success(data = result.body())
            } else {
                WeatherApiResult.error("Oops, something went wrong", null)
            }

        } catch (e: Throwable) {
            WeatherApiResult.error("${e.message}", null)
        }
    }

}