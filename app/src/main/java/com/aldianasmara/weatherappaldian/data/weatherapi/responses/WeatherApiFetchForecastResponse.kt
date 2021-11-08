package com.aldianasmara.weatherappaldian.data.weatherapi.responses

import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiCurrent
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiForecast
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiLocation
import com.google.gson.annotations.SerializedName

data class WeatherApiFetchForecastResponse(
    @field:SerializedName("location")
    val location: WeatherApiLocation,
    @field:SerializedName("current")
    val current: WeatherApiCurrent,
    @field:SerializedName("forecast")
    val forecast: WeatherApiForecast,
)