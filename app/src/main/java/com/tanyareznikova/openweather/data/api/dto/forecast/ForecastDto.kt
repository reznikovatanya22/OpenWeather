package com.tanyareznikova.openweather.data.api.dto.forecast

data class ForecastDto(
    val city: City? = null,
    val cnt: Int? = null,
    val cod: String? = null,
    val list: List<Forecast>? = null,
    val message: Int? = null
)