package com.kapait.faa.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kapait.faa.Const
import com.kapait.faa.R
import com.kapait.faa.ui.home.adapter.CharacteristicItemAdapter
import com.kapait.faa.ui.profile.adapter.CharacteristicOptionsAdapter

class DetailsFragment : Fragment() {

    private lateinit var characteristicOptions: RecyclerView
    private var vacancy: Vacancy? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var titleView: TextView
    private lateinit var currencyView: TextView
    private lateinit var descriptionView : TextView
    private lateinit var dateView : TextView
    private lateinit var jobTypeView: TextView
    private lateinit var location: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacteristicItemAdapter
    private lateinit var respondBtn: Button

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_details, container, false)

        titleView = root.findViewById(R.id.title)
        currencyView = root.findViewById(R.id.currency)
        descriptionView = root.findViewById(R.id.description)
        dateView = root.findViewById(R.id.date)
        jobTypeView = root.findViewById(R.id.job_type)
        location = root.findViewById(R.id.location)
        recyclerView = root.findViewById(R.id.characteristic_list)
        characteristicOptions = root.findViewById(R.id.characteristic_options)
        respondBtn = root.findViewById(R.id.btnSave)


        return root
    }

    private fun createAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("")
        builder.setMessage("By applying for the job you take responsibility for being available " +
                "at the time mentioned in the job description")
        builder.setPositiveButton("Cancel") {dialog, i ->

        }
        builder.setNegativeButton("Ok") {dialog, i ->

        }
        builder.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vacancy = arguments?.getSerializable(Const.VACANCY) as Vacancy?
        vacancy?.let { initViews(it) }
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true);
        respondBtn.setOnClickListener{
            createAlertDialog()
        }


    }

    private fun initViews(vacancy: Vacancy) {
      //  homeViewModel.loadSkills(vacancy.)
        titleView.text = vacancy.title
        currencyView.text = "¥ ${vacancy.compensation.toInt()}"
        descriptionView.text = vacancy.details
        dateView.text = getDate(vacancy)
        jobTypeView.text = vacancy.vacancyTypeId
        location.text = vacancy.location

        adapter = CharacteristicItemAdapter(vacancy.toCharacteristicList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        initSkills()
    }

    private fun initSkills() {

        val skillsAdapter = CharacteristicOptionsAdapter{}.apply {
            setSkills(getArrayFromResources(R.array.skills_options))
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        characteristicOptions.apply {
            adapter = skillsAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun getArrayFromResources(resourceId: Int): List<String> {
        return resources.getStringArray(resourceId).toList()
    }

    private fun getDate(vacancy: Vacancy): String {
        return "${vacancy.visibleStartDateTime} - ${vacancy.visibleFinishDateTime}"
    }

    companion object {
        fun getFragment(vacancy: Vacancy) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Const.VACANCY, vacancy)
            }
        }
    }
}