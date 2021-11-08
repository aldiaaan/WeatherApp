package com.aldianasmara.weatherappaldian.ui.home.widgets

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.aldianasmara.weatherappaldian.R
import com.aldianasmara.weatherappaldian.ui.home.HomeFragment
import com.aldianasmara.weatherappaldian.ui.home.HomeFragmentDirections
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt


class HomeBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var tvLocation: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvLatestUpdate: TextView
    private lateinit var ivCondition: ImageView
    private lateinit var tvNavigateForecast: TextView
    private lateinit var tvMinTemp: TextView
    private lateinit var tvMaxTemp: TextView
    private lateinit var tvDateLabel: TextView
    private lateinit var tvMaxWindSpeed: TextView


    init {
        inflate(context, R.layout.view_home_banner, this)

        tvLocation = findViewById(R.id.tv_location)
        tvTemp = findViewById(R.id.tv_temp)
        tvNavigateForecast = findViewById(R.id.tv_navigate_forecast)
        tvCondition = findViewById(R.id.tv_condition)
        ivCondition = findViewById(R.id.iv_condition)
        tvLatestUpdate = findViewById(R.id.tv_latestupdate)
        tvMinTemp = findViewById(R.id.tv_min_temp)
        tvMaxTemp = findViewById(R.id.tv_max_temp)
        tvMaxWindSpeed = findViewById(R.id.tv_max_wind_speed)
        tvDateLabel = findViewById(R.id.tv_date_label)
    }

    private var _temp = 0.0
    private var _location = ""
    private var _condition = ""
    private var _latestUpdate = 0L
    private var _maxTemp = 0.0
    private var _minTemp = 0.0
    private var _maxWindSpeed = 0.0
    private var _dateLabel = ""

    fun disableNavigation() {
        tvNavigateForecast.visibility = View.GONE
    }

    var dateLabel: String
        get() = _dateLabel
        set(value) {
            _dateLabel = value
            tvDateLabel.text = _dateLabel
        }

    var location: String
        get() = _location;
        set(value) {
            _location = value
            tvLocation.text = _location
            tvNavigateForecast.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToForecastScreenFragment(
                        location
                    )
                )
            }
        }

    var temp: Double
        get() = _temp;
        set(value) {
            _temp = value
            tvTemp.text = "${_temp.roundToInt()} °C"
        }

    var maxTemp: Double
        get() = _maxTemp;
        set(value) {
            _maxTemp = value
            tvMaxTemp.text = "${_maxTemp.roundToInt()} °C"
        }

    var minTemp: Double
        get() = _minTemp;
        set(value) {
            _minTemp = value
            tvMinTemp.text = "Min. Temp${_minTemp.roundToInt()} °C"
        }

    var maxWindSpeed: Double
        get() = _maxWindSpeed;
        set(value) {
            _maxWindSpeed = value
            tvMaxWindSpeed.text = "${_maxWindSpeed.roundToInt()} °C"
        }

    var condition: String
        get() = _condition;
        set(value) {
            _condition = value
            tvCondition.text = _condition
            when (_condition.lowercase()) {
                "heavy rain", "moderate rain", "light rain", "patchy rain possible", "light rain shower" -> {
                    ivCondition.setImageResource(R.drawable.ic_rain)
                }
                "thundery outbreaks possible", "moderate or heavy rain with thunder", "patchy light rain with thunder" -> {
                    ivCondition.setImageResource(R.drawable.ic_lightning_rainy)
                }
                "mist", "fog" -> {
                    ivCondition.setImageResource(R.drawable.ic_weather_fog)
                }
                "patchy snow possible", "light snow", "moderate snow", "heavy snow", "patchy heavy snow", "blowing snow", "Blizzard" -> {
                    ivCondition.setImageResource(R.drawable.ic_snowy)
                }
                "partly cloudy", "cloudy", "overcast" -> {
                    ivCondition.setImageResource(R.drawable.ic_weather_partly_cloudy)
                }
                "sunny", "clear" -> {
                    ivCondition.setImageResource(R.drawable.ic_sunny)
                }
            }
        }

    var latestUpdate: Long
        get() = _latestUpdate;
        @RequiresApi(Build.VERSION_CODES.N)
        set(value) {
            value?.let {
                var formatted = SimpleDateFormat("EEEE, dd MMM h:mm a")
                    .format(Date(value * 1000))
                _latestUpdate = value
                tvLatestUpdate.text =
                    formatted
            }
        }

    companion object {
        const val TAG = "HomeBanner"
    }
}
