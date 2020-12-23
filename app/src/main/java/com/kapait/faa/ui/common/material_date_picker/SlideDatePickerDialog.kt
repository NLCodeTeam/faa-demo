package com.kapait.faa.ui.common.material_date_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R
import com.kapait.faa.ui.common.material_date_picker.adapter.SlideAdapter
import com.kapait.faa.ui.common.material_date_picker.callback.SlideDatePickerDialogCallback
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Класс диалога для выбора даты рождения
 * @author Murad Luguev on 2020-12-20
 */
class SlideDatePickerDialog : DialogFragment() {

    private lateinit var viewModel: SlideDatePickerDialogViewModel

    private lateinit var topContainer: LinearLayout
    private lateinit var tvYear: TextView
    private lateinit var tvDate: TextView
    private lateinit var recyclerViewDay: RecyclerView
    private lateinit var recyclerViewMonth: RecyclerView
    private lateinit var recyclerViewYear: RecyclerView
    private lateinit var btnCancel: Button
    private lateinit var btnConfirm: Button

    private val dayAdapter = SlideAdapter(SlideAdapter.Type.DAY)
    private val monthAdapter = SlideAdapter(SlideAdapter.Type.MONTH)
    private val yearAdapter = SlideAdapter(SlideAdapter.Type.YEAR)
    private val daySnapHelper = LinearSnapHelper()
    private val monthSnapHelper = LinearSnapHelper()
    private val yearSnapHelper = LinearSnapHelper()
    lateinit var dayLayoutManager: LinearLayoutManager
    lateinit var monthLayoutManager: LinearLayoutManager
    lateinit var yearLayoutManager: LinearLayoutManager

    private var yearLastScrollState = RecyclerView.SCROLL_STATE_SETTLING
    private var monthLastScrollState = RecyclerView.SCROLL_STATE_SETTLING
    private var dayLastScrollState = RecyclerView.SCROLL_STATE_SETTLING

    private var startDate: Calendar = Calendar.getInstance().apply {
        this.set(Calendar.YEAR, this.get(Calendar.YEAR) - 100)
        this.set(Calendar.MONTH, 0)
        this.set(Calendar.DAY_OF_MONTH, 1)
    }
    private var endDate: Calendar = Calendar.getInstance()
    private var preselectedDate: Calendar = Calendar.getInstance()
    private var yearModifier: Int = 0
    private var locale: Locale = Locale.US
    @ColorInt private var themeColor: Int = -1
    @ColorInt private var headerTextColor: Int = -1
    @ColorInt private var cancelButtonColor: Int = -1
    private var headerTextFormat: String = "EEE, MMM dd"
    private var showYear = true
    private var cancelText = ""
    private var confirmText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { bundle ->
            restoreInstanceState(bundle)
        } ?: run {
            restoreArgument(arguments)
        }
        setStyle(STYLE_NO_TITLE, R.style.BaseDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_slide_date_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topContainer = view.findViewById(R.id.top_container)
        tvYear = view.findViewById(R.id.tv_year)
        tvDate = view.findViewById(R.id.tv_date)
        recyclerViewDay = view.findViewById(R.id.recycler_view_day)
        recyclerViewMonth = view.findViewById(R.id.recycler_view_month)
        recyclerViewYear = view.findViewById(R.id.recycler_view_year)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnConfirm = view.findViewById(R.id.btn_confirm)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SlideDatePickerDialogViewModel(startDate, endDate, preselectedDate) as T
            }

        }).get(SlideDatePickerDialogViewModel::class.java)
        initialize()
        observe()
    }

    private fun restoreInstanceState(bundle: Bundle) {
        bundle.run {
            getSerializable(EXTRA_START_DATE)?.let { startDate = it as Calendar }
            getSerializable(EXTRA_END_DATE)?.let { endDate = it as Calendar }
            getSerializable(EXTRA_PRESELECTED_DATE)?.let { preselectedDate = it as Calendar }
            getInt(EXTRA_YEAR_MODIFIER).let { yearModifier = it }
            getSerializable(EXTRA_LOCALE)?.let { locale = it as Locale }
            getInt(EXTRA_THEME_COLOR, -1).let { themeColor = it }
            getInt(EXTRA_HEADER_TEXT_COLOR, -1).let { headerTextColor = it }
            getInt(EXTRA_CANCEL_BUTTON_TEXT_COLOR, -1).let { cancelButtonColor = it }
            getString(EXTRA_HEADER_DATE_FORMAT, "").let { headerTextFormat = it }
            getBoolean(EXTRA_SHOW_YEAR, true).let { showYear = it }
            getString(EXTRA_CANCEL_TEXT, "").let { cancelText = it }
            getString(EXTRA_CONFIRM_TEXT, "").let { confirmText = it }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putSerializable(EXTRA_START_DATE, startDate)
            putSerializable(EXTRA_END_DATE, endDate)
            putSerializable(EXTRA_PRESELECTED_DATE, preselectedDate)
            putInt(EXTRA_YEAR_MODIFIER, yearModifier)
            putSerializable(EXTRA_LOCALE, locale)
            putInt(EXTRA_THEME_COLOR, themeColor)
            putInt(EXTRA_HEADER_TEXT_COLOR, headerTextColor)
            putString(EXTRA_HEADER_DATE_FORMAT, headerTextFormat)
            putBoolean(EXTRA_SHOW_YEAR, showYear)
            putString(EXTRA_CANCEL_TEXT, cancelText)
            putString(EXTRA_CONFIRM_TEXT, confirmText)
        }

        super.onSaveInstanceState(outState)

    }

    private fun restoreArgument(bundle: Bundle?) {
        bundle?.run {
            getSerializable(EXTRA_START_DATE)?.let { startDate = it as Calendar }
            getSerializable(EXTRA_END_DATE)?.let { endDate = it as Calendar }
            getSerializable(EXTRA_PRESELECTED_DATE)?.let { preselectedDate = it as Calendar }
            getInt(EXTRA_YEAR_MODIFIER).let { yearModifier = it }
            getSerializable(EXTRA_LOCALE)?.let { locale = it as Locale }
            getInt(EXTRA_THEME_COLOR, -1).let { themeColor = it }
            getInt(EXTRA_HEADER_TEXT_COLOR, -1).let { headerTextColor = it }
            getString(EXTRA_HEADER_DATE_FORMAT, "").let { headerTextFormat = it }
            getBoolean(EXTRA_SHOW_YEAR, true).let { showYear = it }
            getString(EXTRA_CANCEL_TEXT, "").let { cancelText = it }
            getString(EXTRA_CONFIRM_TEXT, "").let { confirmText = it }
        }
    }

    private fun initialize() {
        dayLayoutManager = LinearLayoutManager(context)
        monthLayoutManager = LinearLayoutManager(context)
        yearLayoutManager = LinearLayoutManager(context)

        recyclerViewDay.layoutManager = dayLayoutManager
        recyclerViewDay.adapter = dayAdapter
        recyclerViewDay.addOnScrollListener(dayScrollListener)
        recyclerViewDay.onFlingListener = null
        daySnapHelper.attachToRecyclerView(recyclerViewDay)

        recyclerViewMonth.layoutManager = monthLayoutManager
        recyclerViewMonth.adapter = monthAdapter
        recyclerViewMonth.addOnScrollListener(monthScrollListener)
        recyclerViewMonth.onFlingListener = null
        monthSnapHelper.attachToRecyclerView(recyclerViewMonth)

        recyclerViewYear.layoutManager = yearLayoutManager
        recyclerViewYear.adapter = yearAdapter
        recyclerViewYear.addOnScrollListener(yearScrollListener)
        recyclerViewYear.onFlingListener = null
        yearSnapHelper.attachToRecyclerView(recyclerViewYear)

        btnConfirm.setOnClickListener {
            onDialogConfirm()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        setConfiguration()
    }

    private fun setConfiguration() {

        if (themeColor != -1) {
            topContainer.setBackgroundColor(themeColor)
            btnConfirm.setTextColor(themeColor)
        }

        if (cancelButtonColor != -1) {
            btnCancel.setTextColor(cancelButtonColor)
        }

        if (!showYear) {
            tvYear.visibility = View.GONE
        }

        if (headerTextFormat.isEmpty()) {
            headerTextFormat = "EEE, MMM dd"
        }

        if (cancelText.isNotBlank()) {
            btnCancel.text = cancelText
        }

        if (confirmText.isNotBlank()) {
            btnConfirm.text = confirmText
        }

        monthAdapter.setLocale(locale)
        yearAdapter.setYearModifier(yearModifier)
    }

    private fun onDialogConfirm() {
        val day = viewModel.getCurrentDay().value!!
        val month = viewModel.getCurrentMonth().value!!
        val year = viewModel.getCurrentYear().value!!
        val calendar = viewModel.getCalendar().value!!
        getListener()?.onPositiveClick(day, month, year, calendar)
        dismiss()
    }

    private fun observe() {
        viewModel.getDays().observe(this, Observer {
            dayAdapter.setData(it)
            dayAdapter.notifyDataSetChanged()
            viewModel.getCurrentDay().value?.let { day ->
                val currentDayPosition = dayAdapter.getPositionByValue(day)
                recyclerViewDay.alpha = 0f
                recyclerViewDay.animate().alpha(1f).apply {
                    duration = 200
                }.start()
                recyclerViewDay.scrollToPosition(currentDayPosition)
                recyclerViewDay.smoothScrollToPosition(currentDayPosition)
            }
        })

        viewModel.getMonths().observe(this, Observer {
            monthAdapter.setData(it)
            monthAdapter.notifyDataSetChanged()
            viewModel.getCurrentMonth().value?.let { month ->
                val currentMonthPosition = monthAdapter.getPositionByValue(month)
                recyclerViewMonth.alpha = 0f
                recyclerViewMonth.animate().alpha(1f).apply {
                    duration = 200
                }.start()
                recyclerViewMonth.scrollToPosition(currentMonthPosition)
                recyclerViewMonth.smoothScrollToPosition(currentMonthPosition)
            }
        })

        viewModel.getYears().observe(this, Observer {
            yearAdapter.setData(it)
            yearAdapter.notifyDataSetChanged()
            viewModel.getCurrentYear().value?.let { year ->
                val currentYearPosition = yearAdapter.getPositionByValue(year)
                recyclerViewYear.scrollToPosition(currentYearPosition)
                recyclerViewYear.smoothScrollToPosition(currentYearPosition)
            }
        })

        viewModel.getCalendar().observe(this, Observer {
            tvDate.text = SimpleDateFormat(headerTextFormat, locale).format(it.time)
        })

        viewModel.getCurrentYear().observe(this, Observer {
            tvYear.text = (it + yearModifier).toString()
        })
    }

    private val dayScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE && dayLastScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                val snappedView = daySnapHelper.findSnapView(dayLayoutManager)
                snappedView?.let { view ->
                    val day = dayAdapter.getValueByPosition(dayLayoutManager.getPosition(view))
                    viewModel.setDay(day)
                }
            }

            dayLastScrollState = newState
        }
    }

    private val monthScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE && monthLastScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                val snappedView = monthSnapHelper.findSnapView(monthLayoutManager)
                snappedView?.let { view ->
                    val month = monthAdapter.getValueByPosition(monthLayoutManager.getPosition(view))
                    viewModel.setMonth(month)
                }
            }

            monthLastScrollState = newState
        }
    }

    private val yearScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE && yearLastScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                val snappedView = yearSnapHelper.findSnapView(yearLayoutManager)
                snappedView?.let { view ->
                    val year = yearAdapter.getValueByPosition(yearLayoutManager.getPosition(view))
                    viewModel.setYear(year)
                }
            }

            yearLastScrollState = newState
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerViewDay.removeOnScrollListener(dayScrollListener)
        recyclerViewMonth.removeOnScrollListener(monthScrollListener)
        recyclerViewYear.removeOnScrollListener(yearScrollListener)
    }

    private fun getListener(): SlideDatePickerDialogCallback? {
        return when {
            parentFragment != null -> (parentFragment as? SlideDatePickerDialogCallback)
            activity != null -> (activity as? SlideDatePickerDialogCallback)
            else -> null
        }
    }

    /**
     * Класс для создания экземпляра класса SlideDatePickerDialog с заранее установленными параметрами
     * @author Murad Luguev on 2020-12-20
     */
    class Builder() {
        private var startDate: Calendar? = null
        private var endDate: Calendar? = null
        private var preselectedDate: Calendar? = null
        private var yearModifier: Int = 0
        private var locale: Locale? = null
        @ColorInt private var themeColor: Int = -1
        @ColorInt private var headerTextColor: Int = -1
        @ColorInt private var cancelButtonColor: Int = -1
        private var headerDateFormat: String? = null
        private var showYear = true
        private var cancelText = ""
        private var confirmText = ""

        /** Устанавливает [startDate] в качестве стартовой даты */
        fun setStartDate(startDate: Calendar): Builder = this.apply {
            this.startDate = startDate
        }

        /** Устанавливает [endDate] в качестве конечной даты */
        fun setEndDate(endDate: Calendar): Builder = this.apply {
            this.endDate = endDate
        }

        /** Устанавливает [preselectedDate] в качестве начальной позиции DatePicker'а */
        fun setPreselectedDate(preselectedDate: Calendar): Builder = this.apply {
            this.preselectedDate = preselectedDate
        }

        /** Прибавляет к первому году [yearModifier] */
        fun setYearModifier(yearModifier: Int): Builder = this.apply {
            this.yearModifier = yearModifier
        }

        /** Устанавливает [locale] для отображения  */
        fun setLocale(locale: Locale): Builder = this.apply {
            this.locale = locale
        }

        /** Устанавливает [themeColor] в качестве основного цвета темы */
        fun setThemeColor(@ColorInt themeColor: Int): Builder = this.apply {
            this.themeColor = themeColor
        }

        /** Устанавливает [headerTextColor] в качестве цвета даты, отображаемой в заголовке */
        fun setHeaderTextColor(@ColorRes headerTextColor: Int): Builder = this.apply {
            this.headerTextColor = headerTextColor
        }

        /** Устанавливает [headerDateFormat] в качестве формата отображения даты в заголовке */
        fun setHeaderDateFormat(headerDateFormat: String): Builder = this.apply {
            this.headerDateFormat = headerDateFormat
        }

        /** Устанавливает флаг [showYear] для отоюражения/скрытия года */
        fun setShowYear(showYear: Boolean): Builder = this.apply {
            this.showYear = showYear
        }

        /** Устанавливает [preselectedDate] в качестве текста для кнопки CLOSE */
        fun setCancelText(cancelText: String): Builder = this.apply {
            this.cancelText = cancelText
        }

        /** Устанавливает [cancelButtonColor] в качестве цвета для кнопки CLOSE */
        fun setCancelButtonColor(@ColorRes cancelButtonColor: Int) {
            this.cancelButtonColor = cancelButtonColor
        }

        /** Устанавливает [preselectedDate] в качестве текста для кнопки OK */
        fun setConfirmText(confirmText: String): Builder = this.apply {
            this.confirmText = confirmText
        }

        /** Собирает готовый экземпляр класса SlideDatePicker */
        fun build(): SlideDatePickerDialog = SlideDatePickerDialog.newInstance(
            startDate,
            endDate,
            preselectedDate,
            yearModifier,
            locale,
            themeColor,
            headerTextColor,
            cancelButtonColor,
            headerDateFormat,
            showYear,
            cancelText,
            confirmText
        )
    }

    companion object {
        /**
         *
         * @param startDate начало отсчета
         * @param endDate конец отсчета
         * @param preselectedDate заранее выбранная дата
         * @param yearModifier сдвиг последнего года
         * @param locale язык отображения
         * @param themeColor основной цвет
         * @param headerTextColor цвет заголовка
         * @param cancelButtonColor цвет кнопки закрытия
         * @param headerDateFormat форматирование отображения
         * @param showYear флаг отображения года
         * @param cancelText кастомный текст для кнопки CLOSE
         * @param confirmText кастомный текст для кнопки OK
         *
         * @author Murad Luguev on 2020-12-20
         */
        fun newInstance(
            startDate: Calendar? = null,
            endDate: Calendar? = null,
            preselectedDate: Calendar? = null,
            yearModifier: Int = 0,
            locale: Locale? = null,
            @ColorInt themeColor: Int = -1,
            @ColorInt headerTextColor: Int = -1,
            @ColorInt cancelButtonColor: Int = -1,
            headerDateFormat: String? = null,
            showYear: Boolean = true,
            cancelText: String = "",
            confirmText: String = ""
        ) = SlideDatePickerDialog().apply {
            arguments = Bundle().apply {
                startDate?.let { putSerializable(EXTRA_START_DATE, it) }
                endDate?.let { putSerializable(EXTRA_END_DATE, it) }
                preselectedDate?.let { putSerializable(EXTRA_PRESELECTED_DATE, it) }
                putInt(EXTRA_YEAR_MODIFIER, yearModifier)
                locale?.let { putSerializable(EXTRA_LOCALE, it) }
                putInt(EXTRA_THEME_COLOR, themeColor)
                putInt(EXTRA_HEADER_TEXT_COLOR, headerTextColor)
                putInt(EXTRA_CANCEL_BUTTON_TEXT_COLOR, cancelButtonColor)
                headerDateFormat?.let { putString(EXTRA_HEADER_DATE_FORMAT, it) }
                putBoolean(EXTRA_SHOW_YEAR, showYear)
                putString(EXTRA_CANCEL_TEXT, cancelText)
                putString(EXTRA_CONFIRM_TEXT, confirmText)
            }
        }

        const val EXTRA_START_DATE = "extra-start-date"
        const val EXTRA_END_DATE = "extra-end-date"
        const val EXTRA_PRESELECTED_DATE = "extra-preselected-date"
        const val EXTRA_YEAR_MODIFIER = "extra-year-modifier"
        const val EXTRA_LOCALE = "extra-locale"
        const val EXTRA_THEME_COLOR = "extra-theme-color"
        const val EXTRA_HEADER_TEXT_COLOR = "extra-header-text-color"
        const val EXTRA_CANCEL_BUTTON_TEXT_COLOR = "extra-cancel-button-text-color"
        const val EXTRA_HEADER_DATE_FORMAT = "extra-header-date-format"
        const val EXTRA_SHOW_YEAR = "extra-show-year"
        const val EXTRA_CANCEL_TEXT = "extra-cancel-text"
        const val EXTRA_CONFIRM_TEXT = "extra-confirm-text"
    }

}
