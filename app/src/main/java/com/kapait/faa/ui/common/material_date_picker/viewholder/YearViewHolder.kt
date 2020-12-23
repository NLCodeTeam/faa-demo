package com.kapait.faa.ui.common.material_date_picker.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R

/**
 * ViewHolder для года
 * @param itemView отображаемое View
 * @author Murad Luguev on 2020-12-20
 */
class YearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Отображает год + смещение (по умолчанию 0)
     * @param year отображаемый год
     * @param yearModifier смещение года
     */
    fun bind(year: Int, yearModifier: Int = 0) {
        val textView = itemView.findViewById<TextView>(R.id.tv_year)
        textView.text = (year + yearModifier).toString()
    }
}