package com.tanyareznikova.openweather.data.api.dto.current_weather


import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    val current: Current,
    val location: Location
)