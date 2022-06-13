package com.tanyareznikova.openweather.domain.model.weather

import com.tanyareznikova.openweather.data.api.dto.weather.*
import java.io.Serializable

data class WeatherModel(

    //val clouds: Clouds,
    //val coord: Coord,
    var dt: Int? = null,
    var main: Main? = null,
    var name: String? = null,
    //val sys: Sys,
    //val timezone: Int,
    var weather: List<Weather>? = null,
    var wind: Wind? = null

): Serializable
