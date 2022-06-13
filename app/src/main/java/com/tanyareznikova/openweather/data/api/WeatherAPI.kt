package com.tanyareznikova.openweather.data.api

import com.tanyareznikova.openweather.data.api.dto.forecast.ForecastDto
import com.tanyareznikova.openweather.data.api.dto.weather.WeatherDto
import com.tanyareznikova.openweather.utils.Constants.API_KEY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=moscow&APPID=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //http://api.openweathermap.org/data/2.5/forecast?q=moscow&exclude=hourly,daily&APPID=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //http://api.openweathermap.org/data/2.5/forecast?q=moscow&cnt=1&APPID=461a6dc9004e4d04325d854e78571ab7&lang=ru



    //http://api.openweathermap.org/data/2.5/onecall?q=moscow&exclude=current&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //https://api.openweathermap.org/data/2.5/onecall?lat=55.7522&lon=37.6156&exclude=current&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //daily
    //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=current,hourly&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru
    //hourly
    //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=current,daily&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru
    //current
    //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //ALL WEATHER MODEL
    //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //api.openweathermap.org/data/2.5/forecast/daily?q=London&units=metric&cnt=7&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //api.openweathermap.org/data/2.5/forecast/daily?q=moscow&cnt=7&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //Получаем данные для поиска погоды по стране (CURRENT)
    @GET("data/2.5/weather?&units=metric&exclude=hourly,daily&APPID=$API_KEY&lang=ru")
    fun getDataFromWeatherApi(
        @Query("q") cityName: String
    ): Single<WeatherDto>

    @GET("data/2.5/weather?&units=metric&exclude=hourly,daily&APPID=$API_KEY&lang=ru")
    fun getDataFromWeatherByCityApi(
        @Query("q") cityName: String
    ): Single<WeatherDto>

    //Получаем данные для поиска погоды по стране (forecast)
    @GET("data/2.5/forecast?&units=metric&APPID=$API_KEY&lang=ru")
    fun getDataFromForecastApi(
        @Query("q") cityName: String
    ): Single<ForecastDto>

    //Получаем данные для поиска погоды по стране (forecast hourly)
    @GET("data/2.5/forecast?&units=metric&APPID=$API_KEY&lang=ru")
    fun getDataFromForecastHourlyApi(
        @Query("q") cityName: String
    ): Single<ForecastDto>

    //Получаем данные для поиска погоды по стране (forecast daily)
    @GET("data/2.5/forecast?&units=metric&APPID=$API_KEY&lang=ru")
    fun getDataFromForecastDailyApi(
        @Query("q") cityName: String
    ): Single<ForecastDto>

}