package net.rmoreno.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.TimeUtil
import net.rmoreno.weatherapp.models.DailyDetail

class DailyAdapter(private var sweaterWeather: Int, private var timezone: String) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    private var dailyList = mutableListOf<DailyDetail>()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var maxTemperature: TextView = v.findViewById(R.id.max_temperature) as TextView
        var minTemperature: TextView = v.findViewById(R.id.min_temperature) as TextView
        var day: TextView = v.findViewById(R.id.time) as TextView
        var precipitation: TextView = v.findViewById(R.id.precipitation) as TextView

        var card: CardView = v.findViewById(R.id.hourlyCard) as CardView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.daily_view, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.day.text = "Today"
        } else {
            holder.day.text = TimeUtil.formatDay(dailyList[position].time, timezone)
        }
        holder.minTemperature.text = Math.round(dailyList[position].tempLow).toString() + "° - "
        holder.maxTemperature.text = Math.round(dailyList[position].tempHigh).toString() + "°"

        holder.precipitation.text = Math.round(dailyList[position].precipProbability).toInt().toString() + "%"

        if (dailyList[position].tempLow < sweaterWeather) {
            holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.light_blue))
        } else {

            when {
                position % 4 == 0 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.teal))
                position % 4 == 1 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.yellow))
                position % 4 == 2 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.orange))
                position % 4 == 3 -> holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.red))
            }
        }
    }

    override fun getItemCount() = dailyList.size

    fun addData(dailyList: MutableList<DailyDetail>) {
        this.dailyList = dailyList
        notifyDataSetChanged()
    }
}
