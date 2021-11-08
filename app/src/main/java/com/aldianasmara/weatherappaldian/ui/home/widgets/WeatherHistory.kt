package com.aldianasmara.weatherappaldian.ui.home.widgets

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aldianasmara.weatherappaldian.R
import java.text.SimpleDateFormat
import java.util.*

class WeatherHistory @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var ivStatIcon: ImageView
    private lateinit var tvStatWeekday: TextView
    private lateinit var tvStatDate: TextView
    private lateinit var tvTempC: TextView

    private var _dataEpoch = 0L
    private var _condition = ""
    private var _temp = ""

    var temp: String
        get() = _temp
        set(value) {
            _temp = value
            tvTempC.text = value
        }

    var condition: String
        get() = _condition
        set(value) {
            _condition = value
            Log.d("CONDITION", _condition)
            when (_condition.lowercase()) {
                "heavy rain", "moderate rain", "light rain", "patchy rain possible", "light rain shower" -> {
                    ivStatIcon.setImageResource(R.drawable.ic_rain)
                }
                "thundery outbreaks possible", "moderate or heavy rain with thunder", "patchy light rain with thunder" -> {
                    ivStatIcon.setImageResource(R.drawable.ic_lightning_rainy)
                }
                "mist", "fog" -> {
                    ivStatIcon.setImageResource(R.drawable.ic_weather_fog)
                }
                "patchy snow possible", "light snow", "moderate snow", "heavy snow", "patchy heavy snow", "blowing snow", "Blizzard" -> {
                    ivStatIcon.setImageResource(R.drawable.ic_snowy)
                }
                "partly cloudy", "cloudy", "overcast" -> {
                    ivStatIcon.setImageResource(R.drawable.ic_weather_partly_cloudy)
                }
                "sunny", "clear" -> {
                    ivStatIcon.setImageResource(R.drawable.ic_sunny)
                }
            }

        }

    var dataEpoch: Long
        get() = _dataEpoch;
        set(value) {
            _dataEpoch = value
            tvStatWeekday.text = SimpleDateFormat("EEEE")
                .format(Date(value * 1000))
            tvStatDate.text =
                SimpleDateFormat("dd MMM h:mm a")
                    .format(Date(value * 1000))

        }

    init {

        inflate(context, R.layout.view_weather_history, this)

        ivStatIcon = findViewById(R.id.iv_indicator_icon)
        tvStatWeekday = findViewById(R.id.tv_weekday)
        tvStatDate = findViewById(R.id.tv_date)
        tvTempC = findViewById(R.id.tv_temp_c)

    }


    companion object {
        const val TAG = "WeatherHistory"
    }

}