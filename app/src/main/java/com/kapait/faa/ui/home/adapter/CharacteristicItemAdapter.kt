package com.kapait.faa.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.R
import com.kapait.faa.ui.home.Characteristic


class CharacteristicItemAdapter(private val characteristics: List<Characteristic>) : RecyclerView.Adapter<CharacteristicHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacteristicHolder {
        return CharacteristicHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_characteristic, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacteristicHolder, position: Int) {
        holder.bind(characteristics[position])
    }

    override fun getItemCount() = characteristics.size
}

class CharacteristicHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val titleView = itemView.findViewById<TextView>(R.id.item_characteristic_title)
    private val valueView = itemView.findViewById<TextView>(R.id.item_characteristic_value)


    fun bind(characteristic: Characteristic?) {
        if (characteristic != null) {
            titleView.text = characteristic.name
            valueView.text = characteristic.value
        }
    }
}