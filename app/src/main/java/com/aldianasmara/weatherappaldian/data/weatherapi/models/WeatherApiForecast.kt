package com.aldianasmara.weatherappaldian.data.weatherapi.models

data class WeatherApiForecast(
    val forecastday: List<WeatherApiForecastDay>,
    val location: WeatherApiLocation,
    val current: WeatherApiCurrent
)
