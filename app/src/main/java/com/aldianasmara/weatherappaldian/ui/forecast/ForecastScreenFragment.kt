package com.aldianasmara.weatherappaldian.ui.forecast

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldianasmara.weatherappaldian.R
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiForecastDay
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiResult
import com.aldianasmara.weatherappaldian.databinding.FragmentForecastScreenBinding
import com.aldianasmara.weatherappaldian.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class ForecastScreenFragment :
    Fragment() {

    private val navigationArgs: ForecastScreenFragmentArgs by navArgs()
    private val viewModel: ForecastScreenViewModel by viewModels()
    private val list = ArrayList<WeatherApiForecastDay>()
    private lateinit var forecastListAdapter: ForecastListAdapter

    private var _binding: FragmentForecastScreenBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.location = navigationArgs.location

        forecastListAdapter = ForecastListAdapter(list) { weather, position ->
            findNavController().navigate(
                ForecastScreenFragmentDirections.actionForecastScreenFragmentToForecastDetailFragment(
                    location = navigationArgs.location,
                    date = weather.date,
                    index = position
                )
            )
        }

        binding.rvWeatherForecast.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = forecastListAdapter
        }

        viewModel.threeDaysForecastWeather.observe(viewLifecycleOwner, { result ->

            when (result.status) {
                WeatherApiResult.Status.LOADING -> {
                    binding.loadingIndicator.text = "Loading...."
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
                WeatherApiResult.Status.SUCCESS -> {
                    binding.loadingIndicator.text = ""
                    binding.loadingIndicator.visibility = View.GONE
                    result.data?.forecast?.let {
                        forecastListAdapter.updateList(it.forecastday)
                    }
                }
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastScreenBinding.inflate(inflater, container, false)

        return binding.root
    }


}