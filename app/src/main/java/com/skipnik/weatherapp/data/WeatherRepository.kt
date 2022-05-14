package com.skipnik.weatherapp.data

import com.skipnik.weatherapp.api.WeatherApi
import com.skipnik.weatherapp.data.database.WeatherDatabase
import com.skipnik.weatherapp.data.networkmodel.toDatabaseEntity
import com.skipnik.weatherapp.util.networkBoundResource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val database: WeatherDatabase,
    private val api: WeatherApi
) {
    private val weatherDao = database.weatherDao()

    fun getWeather(cityName: String) = networkBoundResource(
        query = {
            weatherDao.getCityWeather(cityName)
        },
        fetch = {
            val coordinates = api.getGeolocation(cityName)
            api.getWeather(coordinates[0].lat, coordinates[0].lon)
        },
        saveFetchResult = {
            weatherDao.insertCity(it.toDatabaseEntity())
        }
//        },
//        shouldFetch = { city ->
//            val lastRefreshed = city.date
//            val currentDate = System.currentTimeMillis()
//            val timeDifference = currentDate - lastRefreshed
//            val minutes = timeDifference / 1000 / 60
//            val seconds = minutes / 60
//
//            return@networkBoundResource minutes > 1
//        }

    )
}