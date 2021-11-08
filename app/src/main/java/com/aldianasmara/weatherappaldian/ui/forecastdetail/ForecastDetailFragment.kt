package com.aldianasmara.weatherappaldian.ui.forecastdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldianasmara.weatherappaldian.R
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiForecastHour
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiResult
import com.aldianasmara.weatherappaldian.databinding.FragmentForecastDetailBinding
import com.aldianasmara.weatherappaldian.ui.forecast.ForecastScreenViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastDetailFragment : Fragment() {


    private val navigationArgs: ForecastDetailFragmentArgs by navArgs()
    private val viewModel: ForecastDetailViewModel by viewModels()
    private var list = ArrayList<WeatherApiForecastHour>()

    private var _binding: FragmentForecastDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var forecastDetailListAdapter: ForecastDetailListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forecastDetailListAdapter = ForecastDetailListAdapter(list)


        viewModel.location = navigationArgs.location
        binding.forecastDetailBanner.disableNavigation()
        binding.forecastDetailBanner.dateLabel = "Date"
        viewModel.threeDaysForecastWeather.observe(viewLifecycleOwner, { result ->
            Log.d("RESULT", result.data.toString())
            when (result.status) {
                WeatherApiResult.Status.SUCCESS -> {

                    result.data?.let {
                        it.forecast.forecastday[navigationArgs.index].let {
                            binding.wsMaxTemperature.value = "${it.day.maxtemp_c} °C"
                            binding.wsMinTemperature.value = "${it.day.mintemp_c} °C"
                            binding.wsPrecipitation.value = "${it.day.totalprecip_mm} mm"
                            binding.wsWindSpeed.value = "${it.day.maxwind_kph} kph"

                            binding.forecastDetailBanner.temp =
                                it.day.avgtemp_c
                            binding.forecastDetailBanner.condition =
                                it.day.condition.text
                            binding.forecastDetailBanner.latestUpdate =
                                it.date_epoch
                            binding.forecastDetailBanner.location = navigationArgs.location

                            forecastDetailListAdapter.updateList(it.hour)

                        }

                    }
                }

                WeatherApiResult.Status.LOADING -> {
                    binding.wsMaxTemperature.value = "Loading..."
                    binding.wsMinTemperature.value = "Loading..."
                    binding.wsPrecipitation.value =  "Loading..."
                    binding.wsWindSpeed.value =  "Loading..."
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

        binding.rvForecastDetailList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastDetailListAdapter
        }


    }

}