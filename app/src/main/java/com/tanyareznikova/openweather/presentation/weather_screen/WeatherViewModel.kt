package com.tanyareznikova.openweather.presentation.weather_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanyareznikova.openweather.data.api.dto.forecast.ForecastDto
import com.tanyareznikova.openweather.data.api.dto.weather.WeatherDto
import com.tanyareznikova.openweather.data.database.entity.WeatherEntity
import com.tanyareznikova.openweather.data.mapper.*
import com.tanyareznikova.openweather.domain.model.forecast.ForecastModel
import com.tanyareznikova.openweather.domain.model.weather.WeatherModel
import com.tanyareznikova.openweather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {

    private val disposable = CompositeDisposable()

    // Api

    //current weather
    val weatherData = MutableLiveData<WeatherModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoading = MutableLiveData<Boolean>()

    //hourly forecast weather
    val forecastHourlyWeatherData = MutableLiveData<ForecastModel>()
    val forecastHourlyWeatherError = MutableLiveData<Boolean>()
    val forecastHourlyWeatherLoading = MutableLiveData<Boolean>()

    //daily forecast weather
    val forecastDailyWeatherData = MutableLiveData<ForecastModel>()
    val forecastDailyWeatherError = MutableLiveData<Boolean>()
    val forecastDailyWeatherLoading = MutableLiveData<Boolean>()

    // Database

    // current weather
    val weatherDataCityListDb = MutableLiveData<WeatherModel>()
    val weatherErrorCityListDb = MutableLiveData<Boolean>()
    val weatherLoadingCityListDb = MutableLiveData<Boolean>()




    //refresh current weather
    fun refreshData(cityName: String) {
        getWeatherDataFromAPI(cityName)
    }

    fun refreshCurrentDataDb(cityName: String) {
        getDataForCityListFromDb(cityName)
    }

    //refresh hourly weather
    fun refreshForecastHourlyData(cityName: String) {
        getForecastHourlyDataFromAPI(cityName)
    }

    //refresh daily weather
    fun refreshForecastDailyData(cityName: String) {
        getForecastDailyDataFromAPI(cityName)
    }


    //get data from API (current weather)
    private fun getWeatherDataFromAPI(cityName: String) {

        weatherLoading.value = true
        disposable.add(
            repository.getWeatherByCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherDto>() {

                    override fun onSuccess(t: WeatherDto) {
                        weatherData.value = t.toWeather()
                        weatherError.value = false
                        weatherLoading.value = false

                        //repository.insertWeatherIntoDb(t.toWeather().toWeatherEntity())

                        repository.insertWeatherIntoDb(t.toWeather().toWeatherEntity())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()

                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoading.value = false
                    }

                })
        )

    }

    private fun getDataForCityListFromDb(cityName: String) {

        weatherLoadingCityListDb.value = true
        disposable.add(
            repository.getWeatherByCityFromDb(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherEntity>() {

                    override fun onSuccess(t: WeatherEntity) {

                        weatherDataCityListDb.value = t.toWeatherModel()
                        weatherErrorCityListDb.value = false
                        weatherLoadingCityListDb.value = false

                    }

                    override fun onError(e: Throwable) {

                        weatherErrorCityListDb.value = true
                        weatherLoadingCityListDb.value = false

                    }

                })
        )

    }

    //get data from API (hourly weather)
    private fun getForecastHourlyDataFromAPI(cityName: String) {

        forecastHourlyWeatherLoading.value = true
        disposable.add(
            repository.getForecastHourlyByCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ForecastDto>() {

                    override fun onSuccess(t: ForecastDto) {
                        forecastHourlyWeatherData.value = t.toForecast()
                        forecastHourlyWeatherError.value = false
                        forecastHourlyWeatherLoading.value = false

                        //repository.insertForecastIntoDb(t.toForecast().toForecastEntity())
                    }

                    override fun onError(e: Throwable) {
                        forecastHourlyWeatherError.value = true
                        forecastHourlyWeatherLoading.value = false
                    }

                })
        )

    }

    //get data from API (daily weather)
    private fun getForecastDailyDataFromAPI(cityName: String) {

        forecastDailyWeatherLoading.value = true
        disposable.add(
            repository.getForecastDailyByCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ForecastDto>() {

                    override fun onSuccess(t: ForecastDto) {
                        forecastDailyWeatherData.value = t.toForecast()
                        forecastDailyWeatherError.value = false
                        forecastDailyWeatherLoading.value = false

                        //repository.insertForecastIntoDb(t.toForecast().toForecastEntity())
                    }

                    override fun onError(e: Throwable) {
                        forecastDailyWeatherError.value = true
                        forecastDailyWeatherLoading.value = false
                    }

                })
        )

    }

}