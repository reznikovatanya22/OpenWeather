package com.tanyareznikova.openweather.data.mapper


import com.tanyareznikova.openweather.data.api.dto.weather.WeatherDto
import com.tanyareznikova.openweather.data.database.entity.WeatherEntity
import com.tanyareznikova.openweather.domain.model.weather.WeatherModel

/*
* Weather Dto Mapper
*/
fun WeatherDto.toWeather() : WeatherModel {

    return WeatherModel(
        //clouds = clouds,
        //coord = coord,
        dt = dt,
        main = main,
        name = name,
        //sys = sys,
        //timezone = timezone,
        weather = weather,
        wind = wind
    )

}

/*
* Weather Entity Mapper
*/
fun WeatherEntity.toWeatherModel() : WeatherModel {

    return WeatherModel(
        //clouds = clouds,
        //coord = coord,
        dt = dt,
        main = main,
        name = name,
        //sys = sys,
        //timezone = timezone,
        weather = weather,
        wind = wind
    )

}

fun WeatherModel.toWeatherEntity() : WeatherEntity {

    return WeatherEntity(
        //clouds = clouds,
        //coord = coord,
        dt = dt,
        main = main,
        name = name,
        //sys = sys,
        //timezone = timezone,
        weather = weather,
        wind = wind
    )

}
