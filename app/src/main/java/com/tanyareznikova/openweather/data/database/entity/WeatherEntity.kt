package com.tanyareznikova.openweather.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tanyareznikova.openweather.data.api.dto.weather.*

@Entity
data class WeatherEntity(
    @PrimaryKey var id: Int? = null,
    //val clouds: Clouds,
    //val coord: Coord,
    var dt: Int? = null,
    var main: Main? = null,
    var name: String? = "",
    //val sys: Sys,
    //val timezone: Int,
    var weather: List<Weather>? = null,
    var wind: Wind? = null
)
