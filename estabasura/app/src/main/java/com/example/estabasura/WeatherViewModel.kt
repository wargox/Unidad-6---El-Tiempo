package com.example.estabasura

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherRepository
import com.example.weather.data.model.Weather
import kotlinx.coroutines.launch



class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<Weather>()
    val weatherLiveData: LiveData<Weather>
        get() = _weatherLiveData

    fun getWeather(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            val weather = repository.getWeather(lat, lon, apiKey)
            _weatherLiveData.value = weather
        }
    }
}
