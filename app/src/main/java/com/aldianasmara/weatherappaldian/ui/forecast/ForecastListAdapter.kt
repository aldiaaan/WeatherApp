package com.aldianasmara.weatherappaldian.ui.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aldianasmara.weatherappaldian.data.weatherapi.models.WeatherApiForecastDay
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aldianasmara.weatherappaldian.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ForecastListAdapter(
    private val list: ArrayList<WeatherApiForecastDay>,
    private val onItemClicked: (forecast: WeatherApiForecastDay, index: Int) -> Unit
) :
    RecyclerView.Adapter<ForecastListAdapter.ForecastListViewHolder>() {

    class ForecastListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvWeekday: TextView = itemView.findViewById(R.id.tv_weekday)
        var tvDate: TextView = itemView.findViewById(R.id.tv_date)
        var tvTempC: TextView = itemView.findViewById(R.id.tv_temp_c)
        var ivCondition: ImageView = itemView.findViewById(R.id.iv_indicator_icon)
        var clContainer: ConstraintLayout = itemView.findViewById(R.id.cl_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        return ForecastListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_weather_history, parent, false)
        )

//        return ForecastListViewHolder(ListForecastBinding.inflate(
//            LayoutInflater.from(parent.context)
//            ,parent, false).root)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
        holder.tvWeekday.text = "${
            SimpleDateFormat("EEEE")
            .format(Date(list[position].date_epoch  * 1000))}"
        holder.tvDate.text = "${
            SimpleDateFormat("dd MMMM")
                .format(Date(list[position].date_epoch  * 1000))}"
        holder.tvTempC.text = "${list[position]?.day.avgtemp_c} °C"
        when (list[position]?.day?.condition?.text?.lowercase()) {
            "heavy rain", "moderate rain", "light rain", "patchy rain possible", "light rain shower" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_rain)
            }
            "thundery outbreaks possible", "moderate or heavy rain with thunder", "patchy light rain with thunder" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_lightning_rainy)
            }
            "mist", "fog" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_weather_fog)
            }
            "patchy snow possible", "light snow", "moderate snow", "heavy snow", "patchy heavy snow", "blowing snow", "Blizzard" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_snowy)
            }
            "partly cloudy", "cloudy", "overcast" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_weather_partly_cloudy)
            }
            "sunny", "clear" -> {
                holder.ivCondition.setImageResource(R.drawable.ic_sunny)
            }
        }
        holder.clContainer.setOnClickListener{
            onItemClicked(list[position], position)
        }
    }

    fun updateList(forecastList: List<WeatherApiForecastDay>) {
        list.clear()
        list.addAll(forecastList)
        notifyDataSetChanged()
    }


}
