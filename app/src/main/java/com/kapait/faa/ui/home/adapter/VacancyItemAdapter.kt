package com.kapait.faa.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.kapait.faa.R
import com.kapait.faa.ui.home.Vacancy
import java.text.SimpleDateFormat
import java.util.*

class VacancyItemAdapter(private val vacancies: List<Vacancy>,private val listener: OnItemClickListener<Vacancy>) : RecyclerView.Adapter<VacancyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyHolder {
        return VacancyHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_vacancy, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VacancyHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(vacancies[position])
        }
    }

    override fun getItemCount() = vacancies.size
}

class VacancyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleView = itemView.findViewById<TextView>(R.id.item_title)
    //val actionView = itemView.findViewById<TextView>(R.id.item_action)
    val currencyView = itemView.findViewById<TextView>(R.id.item_currency)
    val descriptionView = itemView.findViewById<TextView>(R.id.item_description)
    val dateView = itemView.findViewById<TextView>(R.id.item_date)
    val jobTypeView = itemView.findViewById<TextView>(R.id.item_job_type)
    val publishDateView = itemView.findViewById<TextView>(R.id.item_publish_date)
    val typeView = itemView.findViewById<TextView>(R.id.item_type)

    fun bind(vacancy: Vacancy?) {
        if (vacancy != null) {
            titleView.text = vacancy.title
            currencyView.text = vacancy.compensation.toInt().toString()
            descriptionView.text = vacancy.details
            dateView.text = getDate(vacancy)
            jobTypeView.text = vacancy.vacancyTypeId
            publishDateView.text = vacancy.publicationDateTime
            typeView.text = "Jobs"
        }
    }

    private fun getDate(vacancy: Vacancy): String {
        return "${vacancy.visibleStartDateTime} - ${vacancy.visibleFinishDateTime}"
    }

}
