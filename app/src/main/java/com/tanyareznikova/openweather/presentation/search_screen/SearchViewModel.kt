package com.tanyareznikova.openweather.presentation.search_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanyareznikova.openweather.data.api.dto.forecast.ForecastDto
import com.tanyareznikova.openweather.data.api.dto.weather.WeatherDto
import com.tanyareznikova.openweather.data.database.entity.WeatherEntity
import com.tanyareznikova.openweather.data.mapper.toForecast
import com.tanyareznikova.openweather.data.mapper.toWeather
import com.tanyareznikova.openweather.data.mapper.toWeatherEntity
import com.tanyareznikova.openweather.data.mapper.toWeatherModel
import com.tanyareznikova.openweather.domain.model.forecast.ForecastModel
import com.tanyareznikova.openweather.domain.model.weather.WeatherModel
import com.tanyareznikova.openweather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {

    private val disposable = CompositeDisposable()

    //get list of city names
    val weatherDataCityList = MutableLiveData<WeatherModel>()
    val weatherErrorCityList = MutableLiveData<Boolean>()
    val weatherLoadingCityList = MutableLiveData<Boolean>()

    val weatherDataCityListDb = MutableLiveData<List<WeatherModel>>()
    val weatherErrorCityListDb = MutableLiveData<Boolean>()
    val weatherLoadingCityListDb = MutableLiveData<Boolean>()


    //refresh list if city names
    fun refreshDataForCityListApi(cityName: String) {
        getDataForCityListFromAPI(cityName)
    }

    /*
    fun refreshDataForCityListDb(cityName: String) {
        getDataForCityListFromDb(cityName)
    }

     */

    fun refreshAllDataForCityListDb() {
        getAllDataForCityListFromDb()
    }


    //get data from API (list of city names)
    private fun getDataForCityListFromAPI(cityName: String) {

        weatherLoadingCityList.value = true
            disposable.add(
                repository.getCityWeather(cityName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<WeatherDto>() {

                        override fun onSuccess(t: WeatherDto) {

                            weatherDataCityList.value = t.toWeather()
                            weatherErrorCityList.value = false
                            weatherLoadingCityList.value = false

                            Log.d(TAG, "onSuccess: SearchVM Success")

                            repository.insertWeatherIntoDb(t.toWeather().toWeatherEntity())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()

                        }

                        override fun onError(e: Throwable) {
                            weatherErrorCityList.value = true
                            weatherLoadingCityList.value = false

                            Log.e(TAG, "onError: " + e)
                        }

                    })
            )

    }

    private fun getAllDataForCityListFromDb() {

        weatherLoadingCityListDb.value = true
        disposable.add(
            repository.getWeatherFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<WeatherEntity>>() {

                    override fun onSuccess(t: List<WeatherEntity>) {

                        weatherDataCityListDb.value = t.map { it.toWeatherModel() }
                        weatherErrorCityListDb.value = false
                        weatherLoadingCityListDb.value = false

                        Log.d(TAG, "onSuccess: SearchVM Success")
                    }

                    override fun onError(e: Throwable) {
                        weatherErrorCityListDb.value = true
                        weatherLoadingCityListDb.value = false

                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }

}