package com.skipnik.weatherapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skipnik.weatherapp.R
import com.skipnik.weatherapp.data.PreferenceManager
import com.skipnik.weatherapp.data.TemperatureScale
import com.skipnik.weatherapp.data.database.toCelsius
import com.skipnik.weatherapp.data.database.toFahrenheit
import com.skipnik.weatherapp.databinding.FragmentWeatherBinding
import com.skipnik.weatherapp.util.Resource
import com.skipnik.weatherapp.util.onQueryTextSubmit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var searchView: SearchView

    private var _binding: FragmentWeatherBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWeatherBinding.bind(view)

        binding.apply {

            viewModel.currentCity.observe(viewLifecycleOwner) { result ->
                if (result.data != null) {
                    textCity.text = result.data.name
                    viewModel.temperatureScale.observe(viewLifecycleOwner) { temperatureScale ->
                        when (temperatureScale) {
                            TemperatureScale.CELSIUS -> textTemperature.text =
                                result.data.toCelsius()
                            TemperatureScale.FAHRENHEIT -> textTemperature.text =
                                result.data.toFahrenheit()
                        }
                        textWeather.text = result.data.weatherDescription
                    }
                }
            }
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.onQueryTextSubmit {
            viewModel.onCityChanged(it)
            searchView.clearFocus()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.celsius -> {
                viewModel.onTemperatureScaleChanged(TemperatureScale.CELSIUS)

                true
            }
            R.id.fahrenheit -> {
                viewModel.onTemperatureScaleChanged(TemperatureScale.FAHRENHEIT)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        searchView.setOnQueryTextListener(null)
    }
}
