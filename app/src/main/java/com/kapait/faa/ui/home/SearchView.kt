package com.kapait.faa.ui.home

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.kapait.faa.R


class SearchView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), TextWatcher, View.OnClickListener {
    /**
     * Поле ввода
     */
    private val search: EditText
    private val clear: Button

    init {
        /** Загрузить файл макета */
        LayoutInflater.from(context).inflate(R.layout.search_view, this, true)
        search = findViewById<View>(R.id.et_search) as EditText
        clear = findViewById<View>(R.id.btn_clear) as Button
        clear.visibility = GONE
        search.addTextChangedListener(this)
        clear.setOnClickListener(this)
    }

    /**
     * Кнопка очистки за полем ввода
     */
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    /****
     * Наблюдать за вводимым пользователем текстом
     *
     * @param editable
     */
    override fun afterTextChanged(editable: Editable) {
        /**Получить вводимый текст */
        val input = search.text.toString().trim { it <= ' ' }
        if (input.isEmpty()) {
            clear.setVisibility(GONE)
        } else {
            clear.setVisibility(VISIBLE)
        }
    }

    override fun onClick(view: View?) {
        search.setText("")
    }


}