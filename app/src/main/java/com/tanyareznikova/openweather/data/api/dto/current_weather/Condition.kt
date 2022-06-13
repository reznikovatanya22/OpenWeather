package com.tanyareznikova.openweather.data.api.dto.current_weather


import com.google.gson.annotations.SerializedName

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)