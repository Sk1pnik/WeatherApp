package com.skipnik.weatherapp.ui

import androidx.lifecycle.*
import com.skipnik.weatherapp.api.WeatherApi
import com.skipnik.weatherapp.data.PreferenceManager
import com.skipnik.weatherapp.data.TemperatureScale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val api: WeatherApi,
    private val preferenceManager: PreferenceManager
) : ViewModel() {


    val preferencesFlow = preferenceManager.preferencesFlow

    private val citiesFlow = preferencesFlow.map { preferences ->
        val coordinates = api.getGeolocation(preferences.cityName)
        api.getWeather(coordinates[0].lat, coordinates[0].lon)
    }

    val temperatureScale = preferencesFlow.mapLatest { preferences ->
        preferences.temperatureScale
    }.asLiveData()

    val currentCity = citiesFlow.asLiveData()

    fun onCityChanged(newCity: String) = viewModelScope.launch {
        preferenceManager.updateCity(newCity)
    }

    fun onTemperatureScaleChanged(temperatureScale: TemperatureScale) =
        viewModelScope.launch {
            preferenceManager.updateTemperatureScale(temperatureScale)
        }


}