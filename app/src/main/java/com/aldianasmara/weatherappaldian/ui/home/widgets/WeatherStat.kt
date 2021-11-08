package com.aldianasmara.weatherappaldian.ui.home.widgets

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aldianasmara.weatherappaldian.R


class WeatherStat @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var ivStatIcon: ImageView
    private lateinit var tvStatLabel: TextView
    private lateinit var tvStatValue: TextView


    private var _label = ""
    private var _value = ""

    var label: String
        get() = _label;
        set(value) {
            _label = value
            tvStatLabel.text = _label
        }

    var value: String
        get() = _value;
        set(value) {
            _value = value
            tvStatValue.text = _value
        }

    init {

        inflate(context, R.layout.view_weather_stat, this)

        ivStatIcon = findViewById(R.id.iv_stat_icon)
        tvStatLabel = findViewById(R.id.tv_stat_label)
        tvStatValue = findViewById(R.id.tv_stat_value)

        attrs?.let {
            val attributeArray = context.obtainStyledAttributes(it, R.styleable.WeatherStat)

            ivStatIcon.setImageResource(
                attributeArray.getResourceId(
                    R.styleable.WeatherStat_stat_icon,
                    Resources.ID_NULL
                )
            )
            tvStatLabel.text = attributeArray.getString(
                R.styleable.WeatherStat_stat_label,
            )

            tvStatValue.text = attributeArray.getString(
                R.styleable.WeatherStat_stat_value,
            )

        }
    }


    companion object {
        const val TAG = "WeatherStat"
    }

}