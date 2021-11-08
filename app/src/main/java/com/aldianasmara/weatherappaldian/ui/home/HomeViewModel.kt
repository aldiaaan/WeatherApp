package com.aldianasmara.weatherappaldian.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.aldianasmara.weatherappaldian.data.weatherapi.WeatherApiRepository
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiResult
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchCurrentWeatherResponse
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchForecastResponse
import com.aldianasmara.weatherappaldian.data.weatherapi.responses.WeatherApiFetchHistoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject internal constructor(private val weatherRepository: WeatherApiRepository) :
    ViewModel() {


    private var _location = MutableLiveData<String>("")

    var location: String?
        get() = _location.value;
        set(value) {
            value?.let {
                _location.value = it
            }
        }

    private var _currentWeather: LiveData<WeatherApiResult<WeatherApiFetchCurrentWeatherResponse>> =
        _location.distinctUntilChanged().switchMap {
            liveData {
                weatherRepository.fetchLocationsCurrentWeather(it).onStart {
                    emit(WeatherApiResult.loading())
                }.collect {
                    emit(it)
                }
            }
        }

    private var _history: LiveData<WeatherApiResult<WeatherApiFetchHistoryResponse>> =
        _location.distinctUntilChanged().switchMap {


            liveData {
                weatherRepository.fetchHistory(
                    it,
                    SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000)),
                    SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis() - 1L * 24 * 3600 * 1000))
                ).onStart {
                    emit(WeatherApiResult.loading())
                }.collect {
                    emit(it)
                }
            }
        }

    val history = _history

    val currentWeather = _currentWeather

}