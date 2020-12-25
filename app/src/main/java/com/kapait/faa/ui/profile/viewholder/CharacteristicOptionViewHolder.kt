package com.kapait.faa.ui.profile.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R

class CharacteristicOptionViewHolder(private val option: View, private val listener: (String) -> Unit): RecyclerView.ViewHolder(option) {

    private var itemSelected: Boolean = false
    private lateinit var currentSkill: String
    private lateinit var context: Context

    fun bind(skill: String, context: Context, isSelected: Boolean) {
        this.context = context
        itemSelected = isSelected
        currentSkill = skill
        setView()
    }

    private fun setView() {
        updateViewState()
        option.findViewById<TextView>(R.id.option_name).apply {
            text = currentSkill
            setOnClickListener { setSelectedView() }
        }
    }

    private fun setSelectedView() {
        itemSelected = !itemSelected
        updateViewState()
    }

    private fun updateViewState() {
        val optionName = option as TextView
        if (itemSelected) {
            optionName.background = ContextCompat.getDrawable(context, R.drawable.item_option_selected_background)
            optionName.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        else {
            optionName.background = ContextCompat.getDrawable(context, R.drawable.item_option_background)
            optionName.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        listener.invoke(currentSkill)
    }

}