package com.aldianasmara.weatherappaldian.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aldianasmara.weatherappaldian.R
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiResult
import com.aldianasmara.weatherappaldian.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.snackbar.Snackbar
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.aldianasmara.weatherappaldian.ui.home.widgets.WeatherHistory
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(false)

    }

//    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
//        (requireActivity() as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val location = "Jakarta"

        homeViewModel.location = location
        binding.homeBanner.location = location

        subscribeUi()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun subscribeUi() {
        binding.homeBanner.dateLabel = "Last Update"
        homeViewModel.history.observe(viewLifecycleOwner) { result ->
            Log.d("SIZE", result.data?.forecast?.forecastday?.size.toString())

            when (result.status) {
                WeatherApiResult.Status.ERROR -> {
                    result.message?.let {
                        Snackbar.make(
                            requireActivity().findViewById(R.id.main_constraint_layout),
                            it,
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction("DISMISS") {
                        }.show()
                    }

                }
                WeatherApiResult.Status.SUCCESS -> {
                    result.data?.forecast?.forecastday?.forEach {
                        binding.containerWeatherHistory.addView(WeatherHistory(requireContext()).apply {
                            dataEpoch = it.date_epoch
                            condition = it.day.condition.text
                            temp = "${it.day.avgtemp_c} °C"
                        })
                    }
                }
            }

        }


        homeViewModel.currentWeather.observe(viewLifecycleOwner, { result ->

            when (result.status) {
                WeatherApiResult.Status.SUCCESS -> {

                    result.data?.let {
                        binding.homeBanner.temp = it.current.temp_c
                        binding.homeBanner.condition = it.current.condition.text
                        binding.homeBanner.latestUpdate = it.current.last_updated_epoch
                        binding.wsWindKph.value = "${it.current.wind_kph} kph"
                        binding.wsPressure.value = "${it.current.pressure_mb} mb"
                        binding.wsPrecipitation.value = "${it.current.precip_mm} mm"
                        binding.wsHumidity.value = "${it.current.humidity}"
                        binding.wsCloud.value = "${it.current.cloud}"
                        binding.wsTemperature.value = "${it.current.temp_c} °C"

                        binding.tvCo.text = "${it.current.air_quality.co} μg/m3"
                        binding.tvO3.text = "${it.current.air_quality.o3} μg/m3"
                        binding.tvNo2.text = "${it.current.air_quality.no2} μg/m3"
                        binding.tvSo2.text = "${it.current.air_quality.so2} μg/m3"
                        binding.tvPm25.text = "${it.current.air_quality.pm2_5} μg/m3"
                        binding.tvPm10.text = "${it.current.air_quality.pm10} μg/m3"

                        binding.tvUsEpa.text = when (it.current.air_quality.us_epa_index) {
                            1 -> {
                                "(Good)"
                            }

                            2 -> {
                                "(Moderate)"
                            }

                            3 -> {
                                "(Unhealthy for sensitive group)"
                            }


                            4 -> {
                                "(Unhealthy)"
                            }


                            5 -> {
                                "(Very Unhealthy)"
                            }


                            6 -> {
                                "(Hazardous)"
                            }
                            
                            else -> {
                                "UNKNOWN"
                            }
                        }
                    }
                }

                WeatherApiResult.Status.LOADING -> {
                    binding.wsWindKph.value = "Loading..."
                    binding.wsPressure.value ="Loading..."
                    binding.wsPrecipitation.value = "Loading..."
                    binding.wsHumidity.value = "Loading..."
                    binding.wsCloud.value = "Loading..."
                    binding.wsTemperature.value = "Loading..."

                    binding.tvCo.text = "Loading..."
                    binding.tvO3.text ="Loading..."
                    binding.tvNo2.text = "Loading..."
                    binding.tvSo2.text = "Loading..."
                    binding.tvPm25.text = "Loading..."
                    binding.tvPm10.text = "Loading..."
                }

                WeatherApiResult.Status.ERROR -> {
                    result.message?.let {
                        Snackbar.make(
                            requireActivity().findViewById(R.id.main_constraint_layout),
                            it,
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction("DISMISS") {
                        }.show()
                    }

                }

            }
        })
    }

}