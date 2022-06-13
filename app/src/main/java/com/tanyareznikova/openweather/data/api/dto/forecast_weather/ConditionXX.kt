package com.tanyareznikova.openweather.data.api.dto.forecast_weather


import com.google.gson.annotations.SerializedName

data class ConditionXX(
    val code: Int,
    val icon: String,
    val text: String
)