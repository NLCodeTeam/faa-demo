package com.kapait.faa.ui.common.material_date_picker.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R
import material_date_picker.utils.getMonths
import java.util.*

/**
 * ViewHolder для месяца
 * @param itemView отображаемое View
 * @author Murad Luguev on 2020-12-20
 */
class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Отображает месяц с заданым языком
     * @param month отображаемый месяц
     * @param locale язык для отображения
     */
    fun bind(month: Int, locale: Locale = Locale.US) {
        val textView = itemView.findViewById<TextView>(R.id.tv_month)
        val monthName = getMonths(locale)[month - 1]
        textView.text = monthName
    }
}