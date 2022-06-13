package com.tanyareznikova.openweather.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tanyareznikova.openweather.data.api.dto.forecast.City
import com.tanyareznikova.openweather.data.api.dto.forecast.Forecast

@Entity
data class ForecastEntity(
    @PrimaryKey var id: Int? = null,
    var city: City? = null,
    var list: List<Forecast>? = null
)
