package com.skipnik.weatherapp.data.networkmodel

data class City(
    val coord: Coord,
    val dt: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val weather: List<Weather>
)