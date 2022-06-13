package com.tanyareznikova.openweather.presentation.search_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tanyareznikova.openweather.databinding.SearchCityItemBinding
import com.tanyareznikova.openweather.domain.model.weather.WeatherModel
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val onSearchClickListener: SearchItemClickListener
): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    //private val cityNameList: MutableList<WeatherModel>

    private var cityNameList = mutableListOf<WeatherModel>()

    inner class SearchViewHolder(val binding: SearchCityItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val binding = SearchCityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        with(holder){
            with(cityNameList[position]) {
                binding.searchCityItemTitleTv.text = this.name
                binding.searchCityItemTitleTv.setOnClickListener {
                    val weather = WeatherModel(
                        dt = this.dt,
                        main = this.main,
                        name = this.name,
                        weather = this.weather,
                        wind = this.wind
                    )
                    onSearchClickListener.onSearchItemClick(weather)
                }
            }
        }

    }

    override fun getItemCount(): Int = cityNameList.size


    fun updateListItems(item:WeatherModel){

        if(!cityNameList.contains(item)) {
            cityNameList.add(item)
            //listItem.addAll(listOf(item))
            notifyItemInserted(cityNameList.size)
        }

    }


    fun updateListItems(item: List<WeatherModel>){

        item.forEach {

            if(!cityNameList.contains(it)) {
                cityNameList.add(it)
                //listItem.addAll(listOf(item))
            }
        }

        notifyItemInserted(cityNameList.size)

    }

    interface SearchItemClickListener {

        fun onSearchItemClick(item: WeatherModel)

    }

    /*
    fun updateCities(items: MutableList<WeatherModel>){

            cityNameList = items
            notifyItemInserted(cityNameList.size)

    }//updateCities

    fun deleteCities(id:Int){

        if(id == null) return

            sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
            sqLiteHelper.deleteCityById(id)

    }//deleteCities

     */

}