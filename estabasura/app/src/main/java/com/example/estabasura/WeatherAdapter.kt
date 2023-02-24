package com.example.estabasura

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.weather.databinding.ItemDailyWeatherBinding
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter : ListAdapter<DailyWeather, WeatherAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<DailyWeather>() {
        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather) = oldItem.dt == newItem.dt
        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather) = oldItem == newItem
    }

    class ViewHolder(private val binding: ItemDailyWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dailyWeather: DailyWeather) {
            val dateFormatter = SimpleDateFormat("dd 'de' MMMM", Locale.getDefault())
            val date = dateFormatter.format(Date(dailyWeather.dt * 1000))
            binding.date.text = date

            val temperature = dailyWeather.temp
            val minTemperature = temperature.min.toInt()
            val maxTemperature = temperature.max.toInt()
            binding.temperature.text = itemView.context.getString(R.string.temperature_range, minTemperature, maxTemperature)

            binding.humidity.text = itemView.context.getString(R.string.humidity, dailyWeather.humidity)

            val requestOptions = RequestOptions().centerCrop()
            Glide.with(itemView)
                .load("https://openweathermap.org/img/w/${dailyWeather.weather[0].icon}.png")
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDailyWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int
