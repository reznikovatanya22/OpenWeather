package com.tanyareznikova.openweather.data.repository

import com.tanyareznikova.openweather.data.api.WeatherAPI
import com.tanyareznikova.openweather.data.api.dto.forecast.ForecastDto
import com.tanyareznikova.openweather.data.api.dto.weather.WeatherDto
import com.tanyareznikova.openweather.data.database.dao.WeatherDao
import com.tanyareznikova.openweather.data.database.entity.ForecastEntity
import com.tanyareznikova.openweather.data.database.entity.WeatherEntity
import com.tanyareznikova.openweather.domain.repository.WeatherRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI,
    private val dao: WeatherDao
): WeatherRepository {

    /*
    * Api
    */
    override fun getWeatherByCityName(cityname: String): Single<WeatherDto> {
        return api.getDataFromWeatherApi(cityname)
    }

    override fun getForecastByCityName(cityname: String): Single<ForecastDto> {
        return api.getDataFromForecastApi(cityname)
    }

    override fun getCityWeather(cityname: String): Single<WeatherDto> {
        return api.getDataFromWeatherByCityApi(cityname)
    }

    override fun getForecastHourlyByCityName(cityname: String): Single<ForecastDto> {
        return api.getDataFromForecastHourlyApi(cityname)
    }

    override fun getForecastDailyByCityName(cityname: String): Single<ForecastDto> {
        return api.getDataFromForecastDailyApi(cityname)
    }

    /*
    * Database
    */
    override fun getWeatherFromDb(): Single<List<WeatherEntity>> {
        return dao.selectWeatherDao()
    }

    override fun getWeatherByCityFromDb(cityname: String): Single<WeatherEntity> {
        return dao.selectWeatherByCityDao(cityname)
    }

    /*override fun getWeatherByCityFromDb(cityname: String): Observable<List<WeatherEntity>> {
        return dao.selectWeatherByCityDao(cityname)
    }*/

    override fun insertWeatherIntoDb(cityWeather: WeatherEntity): Completable {
        return dao.insertWeatherDao(cityWeather)
    }

    override fun deleteWeatherFromDb(id: Int) {
        dao.clearWeatherByCityDao(id)
    }

    override fun getForecastByCityFromDb(cityname: String): Single<List<ForecastEntity>> {
        return dao.selectForecastByCityDao(cityname)
    }

    override fun insertForecastIntoDb(cityForecast: ForecastEntity): Completable {
        return dao.insertForecastDao(cityForecast)
    }

    override fun deleteForecastFromDb(id: Int) {
        dao.clearForecastByCityDao(id)
    }
}