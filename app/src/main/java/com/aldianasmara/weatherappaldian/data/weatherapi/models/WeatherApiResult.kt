package com.aldianasmara.weatherappaldian.data.weatherapi.models

data class WeatherApiResult<out T>(
    val status: Status,
    val data: T?,
    val error: Error?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): WeatherApiResult<T> {
            return WeatherApiResult(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Error?): WeatherApiResult<T> {
            return WeatherApiResult(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): WeatherApiResult<T> {
            return WeatherApiResult(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "WeatherApiResult(status=$status, data=$data, error=$error, message=$message)"
    }
}
