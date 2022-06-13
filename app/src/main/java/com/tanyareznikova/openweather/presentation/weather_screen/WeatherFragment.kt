package com.tanyareznikova.openweather.presentation.weather_screen

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanyareznikova.openweather.databinding.FragmentWeatherBinding
import com.tanyareznikova.openweather.domain.model.forecast.ForecastModel
import com.tanyareznikova.openweather.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.tanyareznikova.openweather.R
import com.tanyareznikova.openweather.utils.checkForInternet
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private lateinit var mWeatherBinding: FragmentWeatherBinding
    private val mWeatherViewModel by viewModels<WeatherViewModel>()
    private lateinit var mHourlyAdapter: HourlyWeatherAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    //private lateinit var mMainActivity: MainActivity

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private var hourlyList = mutableListOf<ForecastModel>()

    private val args by navArgs<WeatherFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mWeatherBinding = FragmentWeatherBinding.bind(view)

        GET = (activity as FragmentActivity).getSharedPreferences((activity as FragmentActivity).packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        val cName = GET.getString("cityName", "moscow")
        mWeatherBinding.tvCityNameCurrentWeather.setText(cName)

        //mWeatherViewModel.refreshCurrentDataDb(cName!!)
        //mWeatherViewModel.refreshData(cName)
        //mWeatherViewModel.refreshForecastHourlyData(cName)
        //mWeatherViewModel.refreshForecastDailyData(cName)

        if (checkForInternet(this.context!!)) {
            mWeatherViewModel.refreshData(cName!!)
            getLiveDataCurrentWeather()
            getLiveDataHourlyWeather()
            getLiveDataDailyWeather()
            mWeatherViewModel.refreshCurrentDataDb(cName!!)
            mWeatherViewModel.refreshForecastHourlyData(cName)
            mWeatherViewModel.refreshForecastDailyData(cName)
            getLiveDataCurrentWeatherDb()
        } else {
            mWeatherViewModel.refreshCurrentDataDb(cName!!)
            getLiveDataCurrentWeatherDb()
        }

        initRecyclerView()

        //getLiveDataCurrentWeatherDb()
        //getLiveDataCurrentWeather()
        //getLiveDataHourlyWeather()
        //getLiveDataDailyWeather()

    }

    private fun initRecyclerView() {

        mRecyclerView = mWeatherBinding.hourlyRecyclerViewWeather
        //mHourlyAdapter = HourlyWeatherAdapter(hourlyList)
        mHourlyAdapter = HourlyWeatherAdapter()
        mRecyclerView.adapter = mHourlyAdapter
        mLayoutManager = LinearLayoutManager(activity as FragmentActivity)

    }

    //current weather for the selected city
    private fun getLiveDataCurrentWeather() {

        mWeatherViewModel.weatherData.observe(viewLifecycleOwner, Observer { data ->

            data?.let {

                mWeatherBinding.llDataCurrentWeather.visibility = View.VISIBLE

                //tv_city_code.text = data.sys.country.toString()
                //mWeatherBinding.tvCityNameCurrentWeather.text = data.name
                mWeatherBinding.tvCityNameCurrentWeather.text = args.currentCity?.name
                Glide.with(this)
                    //.load("https://openweathermap.org/img/wn/" + data.weather?.get(0)?.icon + "@2x.png")
                    .load("https://openweathermap.org/img/wn/" + args.currentIcon?.weather?.get(0)?.icon + "@2x.png")
                    .into(mWeatherBinding.imgWeatherPicturesCurrentWeather)
                //mWeatherBinding.tvWeatherDescriptionCurrentWeather.text = data.weather?.get(0)?.description
                mWeatherBinding.tvWeatherDescriptionCurrentWeather.text = args.currentDescription?.weather?.get(0)?.description
                //mWeatherBinding.tvDegreeCurrentWeather.text = data.main?.temp.toString() + "°C"
                mWeatherBinding.tvDegreeCurrentWeather.text = args.currentTemp?.main?.temp.toString() + "°C"
                //mWeatherBinding.tvHumidityCurrentWeather.text = data.main?.humidity.toString() + "%"
                mWeatherBinding.tvHumidityCurrentWeather.text = args.currentHumidity?.main?.humidity.toString() + "%"
                //mWeatherBinding.tvWindSpeedCurrentWeather.text = data.wind?.speed.toString() + "м/с"
                mWeatherBinding.tvWindSpeedCurrentWeather.text = args.currentWind?.wind?.speed.toString() + "м/с"

            }
        })

        mWeatherViewModel.weatherError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    mWeatherBinding.tvErrorCurrentWeather.visibility = View.VISIBLE
                    mWeatherBinding.pbLoadingCurrentWeather.visibility = View.GONE
                    mWeatherBinding.llDataCurrentWeather.visibility = View.GONE
                } else {
                    mWeatherBinding.tvErrorCurrentWeather.visibility = View.GONE
                }
            }
        })

        mWeatherViewModel.weatherLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    mWeatherBinding.pbLoadingCurrentWeather.visibility = View.VISIBLE
                    mWeatherBinding.tvErrorCurrentWeather.visibility = View.GONE
                    mWeatherBinding.llDataCurrentWeather.visibility = View.GONE
                } else {
                    mWeatherBinding.pbLoadingCurrentWeather.visibility = View.GONE
                }
            }
        })

    }


    private fun getLiveDataCurrentWeatherDb() {

        mWeatherViewModel.weatherDataCityListDb.observe(viewLifecycleOwner, Observer { data ->

            data?.let {

                mWeatherBinding.llDataCurrentWeather.visibility = View.VISIBLE

                //tv_city_code.text = data.sys.country.toString()
                //mWeatherBinding.tvCityNameCurrentWeather.text = data.name
                mWeatherBinding.tvCityNameCurrentWeather.text = args.currentCity?.name
                Glide.with(this)
                    //.load("https://openweathermap.org/img/wn/" + data.weather?.get(0)?.icon + "@2x.png")
                    .load("https://openweathermap.org/img/wn/" + args.currentIcon?.weather?.get(0)?.icon + "@2x.png")
                    .into(mWeatherBinding.imgWeatherPicturesCurrentWeather)
                //mWeatherBinding.tvWeatherDescriptionCurrentWeather.text = data.weather?.get(0)?.description
                mWeatherBinding.tvWeatherDescriptionCurrentWeather.text = args.currentDescription?.weather?.first()?.description.toString()
                //mWeatherBinding.tvDegreeCurrentWeather.text = data.main?.temp.toString() + "°C"
                mWeatherBinding.tvDegreeCurrentWeather.text = args.currentTemp?.main?.temp.toString() + "°C"
                //mWeatherBinding.tvHumidityCurrentWeather.text = data.main?.humidity.toString() + "%"
                mWeatherBinding.tvHumidityCurrentWeather.text = args.currentHumidity?.main?.humidity.toString() + "%"
                //mWeatherBinding.tvWindSpeedCurrentWeather.text = data.wind?.speed.toString() + "м/с"
                mWeatherBinding.tvWindSpeedCurrentWeather.text = args.currentWind?.wind?.speed.toString() + "м/с"

            }
        })

        mWeatherViewModel.weatherErrorCityListDb.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    mWeatherBinding.tvErrorCurrentWeather.visibility = View.VISIBLE
                    mWeatherBinding.pbLoadingCurrentWeather.visibility = View.GONE
                    mWeatherBinding.llDataCurrentWeather.visibility = View.GONE
                } else {
                    mWeatherBinding.tvErrorCurrentWeather.visibility = View.GONE
                }
            }
        })

        mWeatherViewModel.weatherLoadingCityListDb.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    mWeatherBinding.pbLoadingCurrentWeather.visibility = View.VISIBLE
                    mWeatherBinding.tvErrorCurrentWeather.visibility = View.GONE
                    mWeatherBinding.llDataCurrentWeather.visibility = View.GONE
                } else {
                    mWeatherBinding.pbLoadingCurrentWeather.visibility = View.GONE
                }
            }
        })

    }

    //hourly weather for the selected city
    private fun getLiveDataHourlyWeather() {

        mWeatherViewModel.forecastHourlyWeatherData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                mWeatherBinding.hourlyRecyclerViewWeather.visibility = View.VISIBLE

                mHourlyAdapter.updateListItems(data)
                mHourlyAdapter.updateListItems(data)
                mHourlyAdapter.updateListItems(data)
                mHourlyAdapter.updateListItems(data)
                mHourlyAdapter.updateListItems(data)
                mHourlyAdapter.updateListItems(data)
                mHourlyAdapter.updateListItems(data)
                mHourlyAdapter.updateListItems(data)

            }
        })

        mWeatherViewModel.forecastHourlyWeatherError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    mWeatherBinding.tvErrorHourlyWeather.visibility = View.VISIBLE
                    mWeatherBinding.pbLoadingHourlyWeather.visibility = View.GONE
                    mWeatherBinding.hourlyRecyclerViewWeather.visibility = View.GONE
                } else {
                    mWeatherBinding.tvErrorHourlyWeather.visibility = View.GONE
                }
            }
        })

        mWeatherViewModel.forecastHourlyWeatherLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    mWeatherBinding.pbLoadingHourlyWeather.visibility = View.VISIBLE
                    mWeatherBinding.tvErrorHourlyWeather.visibility = View.GONE
                    mWeatherBinding.hourlyRecyclerViewWeather.visibility = View.GONE
                } else {
                    mWeatherBinding.pbLoadingHourlyWeather.visibility = View.GONE
                }
            }
        })


    }

    //daily weather for the selected city
    private fun getLiveDataDailyWeather() {

        mWeatherViewModel.forecastDailyWeatherData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {

                // 1
                mWeatherBinding.includeDailyItem1.dataDailyLl.visibility = View.VISIBLE
                val date:Long = data.list!![8].dt!!.toLong()
                mWeatherBinding.includeDailyItem1.tvDateTimeDaily.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list!![8].weather!!.get(0).icon + "@2x.png")
                    .into(mWeatherBinding.includeDailyItem1.imgWeatherPicturesDaily)
                mWeatherBinding.includeDailyItem1.tvTemperatureDaily.text = data.list!![8].main!!.temp.toString() + "°C"
                mWeatherBinding.includeDailyItem1.tvWindDaily.text = data.list!![8].wind!!.speed.toString() + " м/с"

                // 2
                mWeatherBinding.includeDailyItem2.dataDailyLl.visibility = View.VISIBLE
                val date2:Long = data.list!![16].dt!!.toLong()
                mWeatherBinding.includeDailyItem2.tvDateTimeDaily.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date2*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list!![16].weather!!.get(0).icon + "@2x.png")
                    .into(mWeatherBinding.includeDailyItem2.imgWeatherPicturesDaily)
                mWeatherBinding.includeDailyItem2.tvTemperatureDaily.text = data.list!![16].main!!.temp.toString() + "°C"
                mWeatherBinding.includeDailyItem2.tvWindDaily.text = data.list!![16].wind!!.speed.toString() + " м/с"

                // 3
                mWeatherBinding.includeDailyItem3.dataDailyLl.visibility = View.VISIBLE
                val date3:Long = data.list!![24].dt!!.toLong()
                mWeatherBinding.includeDailyItem3.tvDateTimeDaily.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date3*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list!![24].weather!!.get(0).icon + "@2x.png")
                    .into(mWeatherBinding.includeDailyItem3.imgWeatherPicturesDaily)
                mWeatherBinding.includeDailyItem3.tvTemperatureDaily.text = data.list!![24].main!!.temp.toString() + "°C"
                mWeatherBinding.includeDailyItem3.tvWindDaily.text = data.list!![24].wind!!.speed.toString() + " м/с"

                // 4
                mWeatherBinding.includeDailyItem4.dataDailyLl.visibility = View.VISIBLE
                val date4:Long = data.list!![32].dt!!.toLong()
                mWeatherBinding.includeDailyItem4.tvDateTimeDaily.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date4*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list!![32].weather!!.get(0).icon + "@2x.png")
                    .into(mWeatherBinding.includeDailyItem4.imgWeatherPicturesDaily)
                mWeatherBinding.includeDailyItem4.tvTemperatureDaily.text = data.list!![32].main!!.temp.toString() + "°C"
                mWeatherBinding.includeDailyItem4.tvWindDaily.text = data.list!![32].wind!!.speed.toString() + " м/с"

            }
        })
        mWeatherViewModel.forecastDailyWeatherError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    mWeatherBinding.tvErrorDailyWeather.visibility = View.VISIBLE
                    mWeatherBinding.pbLoadingDailyWeather.visibility = View.GONE
                    mWeatherBinding.includeDailyItem1.dataDailyLl.visibility = View.GONE
                    mWeatherBinding.includeDailyItem2.dataDailyLl.visibility = View.GONE
                    mWeatherBinding.includeDailyItem3.dataDailyLl.visibility = View.GONE
                    mWeatherBinding.includeDailyItem4.dataDailyLl.visibility = View.GONE
                } else {
                    mWeatherBinding.tvErrorDailyWeather.visibility = View.GONE
                }
            }
        })

        mWeatherViewModel.forecastDailyWeatherLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    mWeatherBinding.pbLoadingDailyWeather.visibility = View.VISIBLE
                    mWeatherBinding.tvErrorDailyWeather.visibility = View.GONE
                    mWeatherBinding.includeDailyItem1.dataDailyLl.visibility = View.GONE
                    mWeatherBinding.includeDailyItem2.dataDailyLl.visibility = View.GONE
                    mWeatherBinding.includeDailyItem3.dataDailyLl.visibility = View.GONE
                    mWeatherBinding.includeDailyItem4.dataDailyLl.visibility = View.GONE
                } else {
                    mWeatherBinding.pbLoadingDailyWeather.visibility = View.GONE
                }
            }
        })

    }

}