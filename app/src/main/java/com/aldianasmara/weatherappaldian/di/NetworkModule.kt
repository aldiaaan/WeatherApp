package com.aldianasmara.weatherappaldian.di

import com.aldianasmara.weatherappaldian.data.weatherapi.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideWeatherApiService(): WeatherApiService {
        return WeatherApiService.create()
    }
}