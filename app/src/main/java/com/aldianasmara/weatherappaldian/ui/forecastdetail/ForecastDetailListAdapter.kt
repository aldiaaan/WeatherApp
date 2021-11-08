package com.aldianasmara.weatherappaldian.ui.forecastdetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aldianasmara.weatherappaldian.R
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiForecastHour
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ForecastDetailListAdapter(
    private val list: ArrayList<WeatherApiForecastHour>
) :
    RecyclerView.Adapter<ForecastDetailListAdapter.ForecastDetailListViewHolder>() {

    class ForecastDetailListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTemp = view.findViewById<TextView>(R.id.tv_temp)
        var tvTime = view.findViewById<TextView>(R.id.tv_time)
        var ivCondition = view.findViewById<ImageView>(R.id.iv_stat_icon)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastDetailListViewHolder {
        return ForecastDetailListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_hourly_forecast, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ForecastDetailListViewHolder, position: Int) {
        holder.tvTemp.text = "${list[position].temp_c} °C"
        holder.tvTime.text = SimpleDateFormat("h:mm a")
            .format(Date(list[position].time_epoch * 1000))
        println(list[position].condition.text)
        when (list[position].condition.text.lowercase()) {
            "heavy rain", "moderate rain", "patchy rain possible", "light rain shower" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_rain)
            }
            "mist", "fog" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_weather_fog)
            }
            "partly cloudy", "cloudy", "overcast" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_weather_partly_cloudy)
            }
            "sunny", "clear" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_sunny)
            }
        }
    }

    fun updateList(forecastList: List<WeatherApiForecastHour>) {
        list.clear()
        list.addAll(forecastList)
        notifyDataSetChanged()
    }


}