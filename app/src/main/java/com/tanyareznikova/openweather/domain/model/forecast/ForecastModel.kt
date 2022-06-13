package com.tanyareznikova.openweather.domain.model.forecast

import com.tanyareznikova.openweather.data.api.dto.forecast.City
import com.tanyareznikova.openweather.data.api.dto.forecast.Forecast
import java.io.Serializable

data class ForecastModel(

    var city: City? = null,
    var list: List<Forecast>? = null

): Serializable
