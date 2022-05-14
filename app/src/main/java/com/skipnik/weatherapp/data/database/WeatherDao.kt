package com.skipnik.weatherapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities WHERE name = :cityName")
    fun getCityWeather(cityName: String): Flow<WeatherDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(weatherDatabaseEntity: WeatherDatabaseEntity)

    @Update
    suspend fun update(weatherDatabaseEntity: WeatherDatabaseEntity)

    @Delete
    suspend fun delete(weatherDatabaseEntity: WeatherDatabaseEntity)
}