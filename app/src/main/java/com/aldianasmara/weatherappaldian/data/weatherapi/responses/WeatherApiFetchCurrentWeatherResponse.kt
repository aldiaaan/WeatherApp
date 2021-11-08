package com.aldianasmara.weatherappaldian.data.weatherapi.responses

import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiAqi
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiCurrent
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiLocation
import com.google.gson.annotations.SerializedName

data class WeatherApiFetchCurrentWeatherResponse(
    @field:SerializedName("location") val location: WeatherApiLocation,
    @field:SerializedName("current") val current: WeatherApiCurrent,
    @field:SerializedName("aqi") val aqi: WeatherApiAqi,
)