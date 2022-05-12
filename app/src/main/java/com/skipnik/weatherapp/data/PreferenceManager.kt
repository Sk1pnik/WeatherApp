package com.skipnik.weatherapp.data

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore("user_preferences")


    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val city = preferences[PreferencesKeys.CITY_NAME] ?: DEFAULT_CITY
            val temperatureScale = TemperatureScale.valueOf(
                preferences[PreferencesKeys.TEMPERATURE_SCALE] ?: TemperatureScale.CELSIUS.name
            )
            WeatherPreferences(city, temperatureScale)
        }

    suspend fun updateCity(newCity: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CITY_NAME] = newCity
        }
    }

    suspend fun updateTemperatureScale(temperatureScale: TemperatureScale) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TEMPERATURE_SCALE] = temperatureScale.name
        }
    }

    private object PreferencesKeys {
        val CITY_NAME = preferencesKey<String>("city_name")
        val TEMPERATURE_SCALE = preferencesKey<String>("temperature_scale")
    }


    companion object {
        private const val DEFAULT_CITY = "Kyiv"
    }

}

enum class TemperatureScale {
    FAHRENHEIT,
    CELSIUS
}

data class WeatherPreferences(
    val cityName: String,
    val temperatureScale: TemperatureScale
)