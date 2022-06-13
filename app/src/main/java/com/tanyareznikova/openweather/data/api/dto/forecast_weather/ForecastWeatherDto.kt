package com.tanyareznikova.openweather.data.api.dto.forecast_weather


import com.google.gson.annotations.SerializedName

data class ForecastWeatherDto(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)