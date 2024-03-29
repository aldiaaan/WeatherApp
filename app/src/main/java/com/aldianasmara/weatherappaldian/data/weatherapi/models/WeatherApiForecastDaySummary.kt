package com.aldianasmara.weatherappaldian.data.weatherapi.models

data class WeatherApiForecastDaySummary(
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val maxwind_mph: Double,
    val maxwind_kph: Double,
    val totalprecip_mm: Double,
    val totalprecip_in: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val avghumidity: Double,
    val daily_will_it_rain: Double,
    val daily_chance_of_rain: Double,
    val daily_will_it_snow: Double,
    val daily_chance_of_snow: Double,
    val condition: WeatherApiCondition,
    val uv: Double,
)
