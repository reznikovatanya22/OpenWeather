package com.tanyareznikova.openweather.di

import android.app.Application
import androidx.room.Room
import com.tanyareznikova.openweather.data.api.WeatherAPI
import com.tanyareznikova.openweather.data.database.WeatherDatabase
import com.tanyareznikova.openweather.data.database.dao.WeatherDao
import com.tanyareznikova.openweather.data.repository.WeatherRepositoryImpl
import com.tanyareznikova.openweather.domain.repository.WeatherRepository
import com.tanyareznikova.openweather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "weather.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherAPI, db: WeatherDatabase): WeatherRepository {
        return WeatherRepositoryImpl(api, db.dao)
    }

}