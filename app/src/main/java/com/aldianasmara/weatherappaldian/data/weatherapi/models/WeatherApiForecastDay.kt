package com.aldianasmara.weatherappaldian.data.weatherapi.models

data class WeatherApiForecastDay(
    val date: String,
    val date_epoch: Long,
    val day: WeatherApiForecastDaySummary,
    val astro: WeatherApiAstro,
    val hour: List<WeatherApiForecastHour>
)
