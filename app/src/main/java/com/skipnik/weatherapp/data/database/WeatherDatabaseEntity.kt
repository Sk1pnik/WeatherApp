package com.skipnik.weatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "cities")
class WeatherDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val lat: Double,
    val lon: Double,
    val temp: Double,
    val name: String,
    val weatherDescription: String,
    val date: Long
) {
    val formattedDate: String
        get() = DateFormat.getDateTimeInstance().format(date)
}

fun WeatherDatabaseEntity.toCelsius() : String{
     return (this.temp - 273).toInt().toString() + "\u2103"
}

fun WeatherDatabaseEntity.toFahrenheit() : String{
    return (1.8 * (this.temp - 273) + 32).toInt().toString() + "\u2109"
}
