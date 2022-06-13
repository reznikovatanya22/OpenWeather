package com.tanyareznikova.openweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tanyareznikova.openweather.data.database.dao.WeatherDao
import com.tanyareznikova.openweather.data.database.entity.ForecastEntity
import com.tanyareznikova.openweather.data.database.entity.WeatherEntity
import com.tanyareznikova.openweather.data.mapper.WeatherTypeConverters

@Database(
    entities = [WeatherEntity::class, ForecastEntity::class],
    version = 1
)
@TypeConverters(WeatherTypeConverters::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val dao: WeatherDao
}