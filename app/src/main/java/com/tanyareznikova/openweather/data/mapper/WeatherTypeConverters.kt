package com.tanyareznikova.openweather.data.mapper

import androidx.room.TypeConverter
import com.tanyareznikova.openweather.data.api.dto.forecast.City
import com.tanyareznikova.openweather.data.api.dto.forecast.Forecast
import com.tanyareznikova.openweather.data.api.dto.weather.*

class WeatherTypeConverters {

    /*
    *  Weather Type Converters
    */
    // Main
    @TypeConverter
    /*fun fromMainTempType(value: Main): String {
        return value.temp!!.toString()
    }

     */
    fun fromMainTempType(value: Main): Double {
        return value.let { main ->
            main.humidity!!.toDouble()
            main.temp!!.toDouble()
        }
    }
    @TypeConverter
    /*fun toMainTempType(value: String): Main {
        return value.let { Main() }
    }

     */
    fun toMainTempType(value: Double): Main {
        return value.let { Main(
            humidity = it.toInt(),
            temp = it.toDouble()
        ) }
    }

    /*
    @TypeConverter
    fun fromMainHumidityType(value: Main): Int {
        return value.humidity!!.toInt()
    }
    @TypeConverter
    fun toMainHumidityType(value: Int): Main {
        return value.let { Main() }
    }

     */

    // Weather
    @TypeConverter
    fun fromWeatherIconType(value: List<Weather>): String {
        return value.let { weather ->
            weather.first().description.toString()
            weather.first().icon.toString()
        }
    }
    @TypeConverter
    fun toWeatherIconType(value: String): List<Weather> {
        return value.let { listOf(Weather(
            description = it.toString(),
            icon = it.toString()
        )) }
    }

    /*
    @TypeConverter
    fun fromWeatherDescriptionType(value: List<Weather>): String = value.first().description.toString()
    @TypeConverter
    fun toWeatherDescriptionType(value: String): List<Weather> = value.let { listOf(Weather()) }

     */


    // Wind
    @TypeConverter
    fun fromWindType(value: Wind): Double {
        return value.let { wind ->
            wind.speed!!.toDouble()
        }
    }
    //fun fromWindType(value: Wind): String = value.speed!!.toString()
    @TypeConverter
    fun toWindType(value: Double): Wind {
        return value.let { Wind(
            speed = it.toDouble()
        ) }
    }
    //fun toWindType(value: String): Wind = value.let { Wind() }


    /*
    *  Forecast Type Converters
    */
    // City
    @TypeConverter
    fun fromCityType(value: City): String = value.name!!.toString()
    @TypeConverter
    fun toCityType(value: String): City = value.let { City() }

    // Forecast
    @TypeConverter
    fun fromForecastType(value: List<Forecast>): String = value.first().dtTxt.toString()
    @TypeConverter
    fun toForecastType(value: String): List<Forecast> = value.let { listOf(Forecast()) }

}