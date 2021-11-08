package com.aldianasmara.weatherappaldian.data.weatherapi.models

data class WeatherApiLocation(
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val country: String,
    val tz_id: String,
    val localtime_epoch: Int,
    val localtime: String
)
