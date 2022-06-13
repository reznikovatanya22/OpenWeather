package com.tanyareznikova.openweather.domain.repository

import com.tanyareznikova.openweather.data.api.dto.forecast.ForecastDto
import com.tanyareznikova.openweather.data.api.dto.weather.WeatherDto
import com.tanyareznikova.openweather.data.database.entity.ForecastEntity
import com.tanyareznikova.openweather.data.database.entity.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface WeatherRepository {

    /*
    * Api
    */
    fun getWeatherByCityName(cityname: String): Single<WeatherDto>
    fun getForecastByCityName(cityname: String): Single<ForecastDto>

    fun getCityWeather(cityname: String): Single<WeatherDto>
    fun getForecastHourlyByCityName(cityname: String): Single<ForecastDto>
    fun getForecastDailyByCityName(cityname: String): Single<ForecastDto>


    /*
    * Database
    */
    fun getWeatherFromDb(): Single<List<WeatherEntity>>
    //fun getWeatherByCityFromDb(cityname: String): Observable<List<WeatherEntity>>
    fun getWeatherByCityFromDb(cityname: String): Single<WeatherEntity>
    fun insertWeatherIntoDb(cityWeather: WeatherEntity): Completable
    fun deleteWeatherFromDb(id: Int)

    fun getForecastByCityFromDb(cityname: String): Single<List<ForecastEntity>>
    fun insertForecastIntoDb(cityForecast: ForecastEntity): Completable
    fun deleteForecastFromDb(id: Int)

}