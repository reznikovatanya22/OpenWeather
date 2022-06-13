package com.tanyareznikova.openweather.presentation.search_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanyareznikova.openweather.databinding.FragmentSearchBinding
import com.tanyareznikova.openweather.domain.model.weather.WeatherModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tanyareznikova.openweather.R
import com.tanyareznikova.openweather.utils.checkForInternet

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchAdapter.SearchItemClickListener {

    private lateinit var mSearchBinding: FragmentSearchBinding
    private lateinit var mSearchAdapter: SearchAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private val mSearchViewModel by viewModels<SearchViewModel>()
    //private lateinit var mMainActivity: MainActivity

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    //private val args by navArgs<SearchFragment>()

    private var cityList = mutableListOf<WeatherModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSearchBinding = FragmentSearchBinding.bind(view)

        GET = (activity as FragmentActivity).getSharedPreferences((activity as FragmentActivity).packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        val cName = GET.getString("cityName", "moscow")
        mSearchBinding.edtCityNameSearchCity.setText(cName)
        //cName?.let { mSearchViewModel.refreshDataForCityListApi(it) }

        mSearchViewModel.refreshAllDataForCityListDb()
        initRecyclerView()
        getLiveDataCityDb()

        /*
        if (checkForInternet(this.context!!)) {
            mSearchViewModel.refreshDataForCityListApi(cName!!)
            getLiveDataCityApi()
        } else {
            mSearchViewModel.refreshAllDataForCityListDb()
            getLiveDataCityDb()
        }

         */

        /*
         swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            hourly_recycler_view.visibility = View.GONE
            data_daily_ll.visibility = View.GONE
            data_daily_ll2.visibility = View.GONE
            data_daily_ll3.visibility = View.GONE
            data_daily_ll4.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            val cityName = GET.getString("cityName", cName)
            edt_city_name.setText(cityName)
            viewModel.refreshData(cityName!!)
            //viewModel.refreshDataForFavCity(cityName!!)
            viewModel.refreshForecastData(cityName!!)
            viewModel.refreshForecastData2(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }
        */

        mSearchBinding.imgSearchCity.setOnClickListener {

            val cityName = mSearchBinding.edtCityNameSearchCity.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()

            initRecyclerView()

            cleanEditText()
            hideKeyboard()

            //mSearchViewModel.refreshDataForCityListApi(cityName)
            //getLiveDataCityApi()


            if (checkForInternet(this.context!!)) {
                mSearchViewModel.refreshDataForCityListApi(cityName)
                getLiveDataCityApi()
                mSearchViewModel.refreshAllDataForCityListDb()
                getLiveDataCityDb()
            } else {
                //mSearchViewModel.refreshDataForCityListDb(cityName)
                mSearchViewModel.refreshAllDataForCityListDb()
                getLiveDataCityDb()
                //mSearchAdapter.notifyDataSetChanged()
            }


            //val actionWeather = SearchFragmentDirections.actionSearchFragmentToWeatherFragment()
            //findNavController().navigate(actionWeather)
       }
    }

    private fun cleanEditText() {

        mSearchBinding.edtCityNameSearchCity.setText("")

    }

    private fun hideKeyboard() {

        val imm: InputMethodManager = (activity as FragmentActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow((activity as FragmentActivity).window.decorView.windowToken,0)

    }

    private fun initRecyclerView() {

        mRecyclerView = mSearchBinding.searchCityRecyclerView
        //mSearchAdapter = SearchAdapter(cityList)
        mSearchAdapter = SearchAdapter(this)
        mRecyclerView.adapter = mSearchAdapter
        mLayoutManager = LinearLayoutManager(activity as FragmentActivity)

    }

    //list of cities
    fun getLiveDataCityApi() {

        //viewLifecycleOwner
        mSearchViewModel.weatherDataCityList.observe((activity as FragmentActivity), Observer { data ->
            data?.let {

                mSearchBinding.searchCityRecyclerView.visibility = View.VISIBLE

                //getCity()
                mSearchAdapter.updateListItems(data)

                /*
                val actionWeather = SearchFragmentDirections.actionSearchFragmentToWeatherFragment()
                    .setCurrentCity(data).setCurrentIcon(data).setCurrentTemp(data).setCurrentWind(data)
                    .setCurrentDescription(data).setCurrentHumidity(data)
                //findNavController().navigate(actionWeather)
                */

                //getCity() /
                //mSearchAdapter.updateCities(cityList)

            }
        })

        mSearchViewModel.weatherErrorCityList.observe((activity as FragmentActivity), Observer { error ->
            error?.let {
                if (error) {
                    mSearchBinding.tvErrorSearchCity.visibility = View.VISIBLE
                    mSearchBinding.pbLoadingSearchCity.visibility = View.GONE
                    mSearchBinding.searchCityRecyclerView.visibility = View.GONE
                } else {
                    mSearchBinding.tvErrorSearchCity.visibility = View.GONE
                }
            }
        })

        mSearchViewModel.weatherLoadingCityList.observe((activity as FragmentActivity), Observer { loading ->
            loading?.let {
                if (loading) {
                    mSearchBinding.pbLoadingSearchCity.visibility = View.VISIBLE
                    mSearchBinding.tvErrorSearchCity.visibility = View.GONE
                    mSearchBinding.searchCityRecyclerView.visibility = View.GONE
                } else {
                    mSearchBinding.pbLoadingSearchCity.visibility = View.GONE
                }
            }
        })

    }

    fun getLiveDataCityDb() {

        //viewLifecycleOwner
        mSearchViewModel.weatherDataCityListDb.observe((activity as FragmentActivity), Observer { data ->
            data?.let {

                mSearchBinding.searchCityRecyclerView.visibility = View.VISIBLE

                mSearchAdapter.updateListItems(data)

            }
        })

        mSearchViewModel.weatherErrorCityListDb.observe((activity as FragmentActivity), Observer { error ->
            error?.let {
                if (error) {
                    mSearchBinding.tvErrorSearchCity.visibility = View.VISIBLE
                    mSearchBinding.pbLoadingSearchCity.visibility = View.GONE
                    mSearchBinding.searchCityRecyclerView.visibility = View.GONE
                } else {
                    mSearchBinding.tvErrorSearchCity.visibility = View.GONE
                }
            }
        })

        mSearchViewModel.weatherLoadingCityListDb.observe((activity as FragmentActivity), Observer { loading ->
            loading?.let {
                if (loading) {
                    mSearchBinding.pbLoadingSearchCity.visibility = View.VISIBLE
                    mSearchBinding.tvErrorSearchCity.visibility = View.GONE
                    mSearchBinding.searchCityRecyclerView.visibility = View.GONE
                } else {
                    mSearchBinding.pbLoadingSearchCity.visibility = View.GONE
                }
            }
        })

    }

    override fun onSearchItemClick(item: WeatherModel) {

        val actionWeather = SearchFragmentDirections.actionSearchFragmentToWeatherFragment()
            .setCurrentCity(item).setCurrentIcon(item).setCurrentTemp(item).setCurrentWind(item)
            .setCurrentDescription(item).setCurrentHumidity(item)
        findNavController().navigate(actionWeather)

    }

}