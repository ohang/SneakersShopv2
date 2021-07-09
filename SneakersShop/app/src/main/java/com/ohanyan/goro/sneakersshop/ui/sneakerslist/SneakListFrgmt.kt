package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.SneakerFireAdapter
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.viewmodels.SneakerViewModel


class SneakListFrgmt(var male:String) : Fragment(),SneakerFireAdapter.OnItemClickListener {

    lateinit var SneakerList: RecyclerView
    lateinit var sneakerViewModel: SneakerViewModel
    lateinit var loadpr:ProgressBar
    private lateinit var fradapter: SneakerFireAdapter

    private val SneakerColection = Firebase.firestore.collection("Sneakers").
    document("manwoman").collection(male)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sneakers_list, container, false)

        loadpr=view.findViewById(R.id.loadfr)

        sneakerViewModel = ViewModelProvider(this).get(SneakerViewModel::class.java)

        SneakerList = view.findViewById(R.id.sneakers_list_id)

        setUpRecyclerView()

        return view

    }

    private fun setUpRecyclerView() {
        val query: Query = SneakerColection.orderBy(
            "price",
            Query.Direction.DESCENDING
        )

        val options = FirestoreRecyclerOptions.Builder<Sneaker>()
            .setQuery(query, Sneaker::class.java)
            .build()

        fradapter = SneakerFireAdapter(requireContext(), options, this)
        SneakerList.setHasFixedSize(true)
        SneakerList.layoutManager = LinearLayoutManager(requireContext())
        SneakerList.adapter = fradapter


    }

    override fun onStart() {
        super.onStart()
        fradapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        fradapter.stopListening()
    }


    override fun onItemClick(item: Sneaker, view: View, position: Int) {
        val action =BottomNavFragmentDirections.actionBottomNavFragmentToSneakerPageFragment(item)
        findNavController().navigate(action)
    }

    override fun onItemLong(item: Sneaker, view: View, position: Int) {


    }


}



