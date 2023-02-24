package com.example.estabasura


import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String
    ): WeatherResponse
}

data class WeatherResponse(val weather: Weather)
