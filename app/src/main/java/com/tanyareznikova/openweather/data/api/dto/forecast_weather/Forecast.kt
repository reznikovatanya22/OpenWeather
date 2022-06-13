package com.tanyareznikova.openweather.data.api.dto.forecast_weather


import com.google.gson.annotations.SerializedName

data class Forecast(
    val forecastday: List<Forecastday>
)