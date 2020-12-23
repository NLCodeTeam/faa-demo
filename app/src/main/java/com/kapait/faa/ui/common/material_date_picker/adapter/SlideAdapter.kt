package com.kapait.faa.ui.common.material_date_picker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R
import com.kapait.faa.ui.common.material_date_picker.viewholder.DayViewHolder
import com.kapait.faa.ui.common.material_date_picker.viewholder.MonthViewHolder
import com.kapait.faa.ui.common.material_date_picker.viewholder.YearViewHolder
import java.util.Locale
import kotlin.collections.ArrayList

/**
 * Класс адаптера для RecyclerView
 * @property type идентификатор, который определяет какой ViewHolder использовать
 * @author Murad Luguev on 2020-12-20
 */

class SlideAdapter(private val type: Type) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: ArrayList<Int> = ArrayList()
    private var locale: Locale = Locale.getDefault()
    private var yearModifier = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            Type.DAY -> DayViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_day,
                    parent,
                    false
                )
            )
            Type.MONTH -> MonthViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_month,
                    parent,
                    false
                )
            )
            else -> YearViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_year,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DayViewHolder -> holder.bind(data[position])
            is MonthViewHolder -> holder.bind(data[position], locale)
            is YearViewHolder -> holder.bind(data[position], yearModifier)
        }
    }

    /** Получаю позицию элемента по [value] */
    fun getPositionByValue(value: Int): Int {
        data.forEachIndexed { index, item ->
            if (value == item) {
                return index
            }
        }
        return data.size - 1
    }

    /**Получаю значение элемента по [position] */
    fun getValueByPosition(position: Int): Int {
        data.forEachIndexed { index, item ->
            if (position == index) {
                return item
            }
        }
        return FIRST_IN_DATE_LIST
    }

    /**Устанавливаю значение для параметра [locale] в адаптере*/
    fun setLocale(locale: Locale) {
        this.locale = locale
    }

    /**Устанавливаю значение для параметра [yearModifier] в адаптере*/
    fun setYearModifier(yearModifier: Int) {
        this.yearModifier = yearModifier
    }

    /**Устанавливаю значение для параметра [data] в адаптере*/
    fun setData(data: ArrayList<Int>) {
        this.data = data
    }

    /**Enum для определения ViewHolder'а */
    enum class Type {
        DAY, MONTH, YEAR
    }

    companion object {
        private const val FIRST_IN_DATE_LIST = 1
    }
}