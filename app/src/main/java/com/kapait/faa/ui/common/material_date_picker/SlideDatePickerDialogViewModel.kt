package com.kapait.faa.ui.common.material_date_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import material_date_picker.utils.getAvailableDays
import material_date_picker.utils.getAvailableMonths
import material_date_picker.utils.getYearFromTo
import java.util.*
import kotlin.collections.ArrayList

/**
 * ВМ для отображения даты
 *
 * @property startDate гачальная дата
 * @property endDate конечная дата
 * @param currentDate текущая дата
 * @author Murad Luguev 2020-12-20
 */
class SlideDatePickerDialogViewModel(
    private var startDate: Calendar,
    private var endDate: Calendar,
    currentDate: Calendar) : ViewModel() {

    private val currentDay: MutableLiveData<Int> = MutableLiveData()
    private val currentMonth: MutableLiveData<Int> = MutableLiveData()
    private val currentYear: MutableLiveData<Int> = MutableLiveData()

    private val days: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    private val months: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    private val years: MutableLiveData<ArrayList<Int>> = MutableLiveData()

    private val calendar: MutableLiveData<Calendar> = MutableLiveData()

    init {
        this.calendar.value = currentDate

        initCurrentDate()
        initData()
    }

    private fun initCurrentDate() {
        currentDay.value = calendar.value!!.get(Calendar.DAY_OF_MONTH)
        currentMonth.value = calendar.value!!.get(Calendar.MONTH) + 1
        currentYear.value = calendar.value!!.get(Calendar.YEAR)
    }

    private fun initData() {
        years.value = getYearFromTo(startDate.get(Calendar.YEAR), endDate.get(Calendar.YEAR))
    }

    /** Получаю отображаемый день */
    fun getCurrentDay(): LiveData<Int> = currentDay

    /** Получаю отображаемый месяц */
    fun getCurrentMonth(): LiveData<Int> = currentMonth

    /** Получаю отображаемый гож */
    fun getCurrentYear(): LiveData<Int> = currentYear

    /** Получаю доступные дни */
    fun getDays(): LiveData<ArrayList<Int>> = days

    /** Получаю доступные месяцы */
    fun getMonths(): LiveData<ArrayList<Int>> = months

    /** Получаю доступные годы */
    fun getYears(): LiveData<ArrayList<Int>> = years

    /** Получаю весь календарь (от стартовой даты до конечной) */
    fun getCalendar(): LiveData<Calendar> = calendar

    /** Устанавливаю [year] в качестве отображаемого года */
    fun setYear(year: Int) {
        currentYear.value = year
        months.value = getAvailableMonths(currentYear.value!!, startDate, endDate)

        calendar.value = material_date_picker.utils.getCalendar(
            currentDay.value!!,
            currentMonth.value!!,
            currentYear.value!!
        )
    }

    /** Устанавливаю [month] в качестве отображаемого месяца */
    fun setMonth(month: Int) {
        currentMonth.value = month
        days.value = getAvailableDays(currentYear.value!!, currentMonth.value!!, startDate, endDate)

        calendar.value = material_date_picker.utils.getCalendar(
            currentDay.value!!,
            currentMonth.value!!,
            currentYear.value!!
        )
    }

    /** Устанавливаю [day] в качестве отображаемого дня */
    fun setDay(day: Int) {
        currentDay.value = day

        calendar.value = material_date_picker.utils.getCalendar(
            currentDay.value!!,
            currentMonth.value!!,
            currentYear.value!!
        )
    }
}
