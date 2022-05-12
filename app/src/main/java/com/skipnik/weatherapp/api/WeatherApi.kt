package com.skipnik.weatherapp.api


import com.skipnik.weatherapp.BuildConfig
import com.skipnik.weatherapp.data.networkmodel.City
import com.skipnik.weatherapp.data.networkmodel.Coord
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY
    ): City

    @GET("/geo/1.0/direct")
    suspend fun getGeolocation(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY
    ): List<Coord>

}