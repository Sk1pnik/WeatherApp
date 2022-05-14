package com.skipnik.weatherapp.ui

import androidx.lifecycle.*
import com.skipnik.weatherapp.api.WeatherApi
import com.skipnik.weatherapp.data.PreferenceManager
import com.skipnik.weatherapp.data.TemperatureScale
import com.skipnik.weatherapp.data.WeatherRepository
import com.skipnik.weatherapp.data.networkmodel.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val api: WeatherApi,
    private val preferenceManager: PreferenceManager,
    private val repository: WeatherRepository
) : ViewModel() {


    val preferencesFlow = preferenceManager.preferencesFlow

    private val citiesFlow = preferencesFlow.flatMapLatest { preferences ->
        repository.getWeather(preferences.cityName)
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