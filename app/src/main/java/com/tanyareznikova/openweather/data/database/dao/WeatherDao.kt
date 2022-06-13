package com.tanyareznikova.openweather.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanyareznikova.openweather.data.database.entity.ForecastEntity
import com.tanyareznikova.openweather.data.database.entity.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface WeatherDao {

    /*
   * Weather Dao
   */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherDao(
        weatherList: WeatherEntity
    ): Completable

    @Query("DELETE FROM weatherentity WHERE id = :id")
    fun clearWeatherByCityDao(id: Int)

    @Query("SELECT * FROM weatherentity")
    fun selectWeatherDao(): Single<List<WeatherEntity>>

    /*
    @Query("SELECT * FROM weatherentity WHERE name = :cityname")
    fun selectWeatherByCityDao(cityname: String): Observable<List<WeatherEntity>>

     */

    @Query("SELECT * FROM weatherentity WHERE name = :cityname")
    fun selectWeatherByCityDao(cityname: String): Single<WeatherEntity>

    /*
    * Forecast Dao
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecastDao(
        forecastDetail: ForecastEntity
    ): Completable

    @Query("DELETE FROM forecastentity WHERE id = :id")
    fun clearForecastByCityDao(id: Int)

    @Query("SELECT * FROM forecastentity WHERE city = :cityname")
    fun selectForecastByCityDao(cityname: String): Single<List<ForecastEntity>>

}