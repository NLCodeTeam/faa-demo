package com.kapait.faa.ui.common.material_date_picker.callback

import java.util.Calendar

/**
 * Обратный вызов для работы с полученной датой
 * @author Murad Luguev on 2020-12-20
 */
interface SlideDatePickerDialogCallback {
    fun onPositiveClick(day: Int, month: Int, year: Int, calendar: Calendar)
}