package com.tanyareznikova.openweather.data.api.dto.forecast_weather


import com.google.gson.annotations.SerializedName

data class Astro(
    @SerializedName("moon_illumination")
    val moonIllumination: String,
    @SerializedName("moon_phase")
    val moonPhase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)