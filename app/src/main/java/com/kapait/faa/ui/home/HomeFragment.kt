package com.kapait.faa.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kapait.faa.Const
import com.kapait.faa.DetailsActivity
import com.kapait.faa.R
import com.kapait.faa.ui.home.adapter.OnItemClickListener
import com.kapait.faa.ui.home.adapter.VacancyItemAdapter

class HomeFragment : Fragment(),OnItemClickListener<Vacancy> {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: VacancyItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = root.findViewById(R.id.vacancies_list)
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.vacanciesLiveData.observe(viewLifecycleOwner,::vacanciesLoaded)
        homeViewModel.loadVacancies()
    }

    private fun vacanciesLoaded(list: List<Vacancy>?) {
        if (list != null) {
            adapter = VacancyItemAdapter(list,this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onItemClick(item: Vacancy) {
        val intent = Intent(context,DetailsActivity::class.java).apply {
            putExtra(Const.VACANCY,item)
        }
        startActivity(intent)
    }
}