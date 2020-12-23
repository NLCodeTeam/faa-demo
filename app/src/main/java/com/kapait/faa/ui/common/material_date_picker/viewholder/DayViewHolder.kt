package com.kapait.faa.ui.common.material_date_picker.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R
import material_date_picker.utils.format2LengthDay

/**
 * ViewHolder для дня
 * @param itemView отображаемое View
 * @author Murad Luguev on 2020-12-20
 */
class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Отоюражает день
     * @param day отображаемый день
     */
    fun bind(day: Int) {
        val textView = itemView.findViewById<TextView>(R.id.tv_day)
        textView.text = format2LengthDay(day)
    }
}