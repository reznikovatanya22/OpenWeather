package com.tanyareznikova.openweather.data.api.dto.forecast

import com.google.gson.annotations.SerializedName

data class Forecast(
    val clouds: Clouds? = null,
    val dt: Int? = null,
    @SerializedName("dt_txt")
    val dtTxt: String? = null,
    val main: Main? = null,
    val pop: Double? = null,
    val sys: Sys? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null
)