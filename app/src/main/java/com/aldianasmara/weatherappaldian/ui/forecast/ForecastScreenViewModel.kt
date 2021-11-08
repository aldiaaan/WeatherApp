package com.aldianasmara.weatherappaldian.ui.forecast

import android.util.Log
import androidx.lifecycle.*
import com.aldianasmara.weatherappaldian.data.weatherapi.WeatherApiRepository
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiResult
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchForecastResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ForecastScreenViewModel @Inject internal constructor(private val weatherRepository: WeatherApiRepository) :
    ViewModel() {

    private var _location = MutableLiveData<String>("")

    var location: String?
        get() = _location.value;
        set(value) {
            value?.let {
                _location.value = it
            }
        }

    private var _threeDaysForecastWeather: LiveData<WeatherApiResult<WeatherApiFetchForecastResponse>> =
        _location.distinctUntilChanged().switchMap {
            liveData {
                weatherRepository.fetchWeatherForecast(it, days = 3).onStart {
                    emit(WeatherApiResult.loading())
                }.collect {
                    emit(it)
                }
            }
        }

    val threeDaysForecastWeather = _threeDaysForecastWeather

}