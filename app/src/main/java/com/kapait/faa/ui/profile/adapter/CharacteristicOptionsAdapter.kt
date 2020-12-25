package com.kapait.faa.ui.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R
import com.kapait.faa.ui.profile.viewholder.CharacteristicOptionViewHolder

class CharacteristicOptionsAdapter(private val listener: (String) -> Unit): RecyclerView.Adapter<CharacteristicOptionViewHolder>() {

    private var skills: List<String> = emptyList()
    private var selectedSkills: List<String> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacteristicOptionViewHolder {
        context = parent.context
        return CharacteristicOptionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_option, parent, false), listener)
    }

    override fun onBindViewHolder(holder: CharacteristicOptionViewHolder, position: Int) {
        val isSelected = selectedSkills.contains(skills[position])
        holder.bind(skills[position], context, isSelected)
    }

    override fun getItemCount(): Int = skills.size

    fun setSkills(list: List<String>) {
        skills = list
    }

    fun setSelectedSkills(selected: List<String>) {
        selectedSkills = selected
    }
}