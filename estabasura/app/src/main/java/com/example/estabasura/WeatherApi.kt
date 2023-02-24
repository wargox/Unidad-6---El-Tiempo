package com.example.estabasura


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherService::class.java)

    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Weather {
        return service.getWeather(lat, lon, "hourly,daily", apiKey).weather
    }
}

data class Weather(val current: CurrentWeather, val daily: List<DailyWeather>)

data class CurrentWeather(val temp: Double, val humidity: Int)

data class DailyWeather(val dt: Long, val temp: Temperature, val humidity: Int, val weather: List<WeatherDetail>)

data class Temperature(val min: Double, val max: Double)

data class WeatherDetail(val id: Int, val description: String, val icon: String)
