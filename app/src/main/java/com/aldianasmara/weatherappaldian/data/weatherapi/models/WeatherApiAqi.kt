package com.aldianasmara.weatherappaldian.data.weatherapi.models

import com.google.gson.annotations.SerializedName

data class WeatherApiAqi(
    val co: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    @field:SerializedName("us-epa-index") val us_epa_index: Int,
    @field:SerializedName("gb-defra-index") val gb_defra_index: Int
)
