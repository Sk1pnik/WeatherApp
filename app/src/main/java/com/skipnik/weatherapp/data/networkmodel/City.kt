package com.skipnik.weatherapp.data.networkmodel

import com.skipnik.weatherapp.data.database.WeatherDatabaseEntity

data class City(
    val coord: Coord,
    val dt: Long,
    val main: Main,
    val name: String,
    val sys: Sys,
    val weather: List<Weather>
)

fun City.toDatabaseEntity(): WeatherDatabaseEntity {
    return WeatherDatabaseEntity(
        lon = this.coord.lon,
        lat = this.coord.lat,
        temp = this.main.temp,
        name = this.name,
        weatherDescription = this.weather[0].description,
        date = this.dt
    )
}

