package com.tanyareznikova.openweather.presentation.weather_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanyareznikova.openweather.databinding.HourlyWeatherItemBinding
import com.tanyareznikova.openweather.domain.model.forecast.ForecastModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HourlyWeatherAdapter @Inject constructor(

): RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    //private var listItem: MutableList<ForecastModel>

    private var listItem = mutableListOf<ForecastModel>()

    inner class HourlyWeatherViewHolder(val binding: HourlyWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {

        val binding = HourlyWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyWeatherViewHolder(binding)

    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {

        with(holder){
            with(listItem[position]) {

                val date = this.list!![position].dt!!.toLong()
                binding.tvDateTimeHourly.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(date*1000))

                Glide.with(binding.imgWeatherPicturesHourly).load("https://openweathermap.org/img/wn/" + this.list!![position].weather!!.get(0).icon + "@2x.png")
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .error(android.R.drawable.stat_notify_error)
                    .into(binding.imgWeatherPicturesHourly)

                binding.tvTemperatureHourly.text = this.list!![position].main!!.temp.toString() + "°C"

            }
        }

    }

    override fun getItemCount(): Int = listItem.size

    fun updateListItems(item:ForecastModel){

        listItem.add(item)
        notifyItemInserted(listItem.size)

    }

}