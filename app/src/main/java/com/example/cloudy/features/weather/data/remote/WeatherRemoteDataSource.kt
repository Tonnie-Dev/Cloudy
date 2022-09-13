package com.example.cloudy.features.weather.data.remote

import com.example.cloudy.core.util.Resource
import com.example.cloudy.features.weather.data.remote.dto.WeatherDto

interface WeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, long: Double): Resource<WeatherDto>
}