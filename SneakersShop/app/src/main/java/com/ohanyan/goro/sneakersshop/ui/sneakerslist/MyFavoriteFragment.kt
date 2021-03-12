package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.SneakerFireAdapter
import com.ohanyan.goro.sneakersshop.adapters.SneakersListAdapter
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.viewmodels.SneaskerViewModel


class MyFavoriteFragment : Fragment(),SneakersListAdapter.OnItemClickListener {

    lateinit var MyFavoriteList: RecyclerView
    lateinit var sneakerViewModel: SneaskerViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_favorite, container, false)
        val gridLayoutmanager = GridLayoutManager(requireContext(), 2)



        MyFavoriteList = view.findViewById(R.id.myfavorite_list_id)

        MyFavoriteList.layoutManager=gridLayoutmanager
        MyFavoriteList.setHasFixedSize(true)
      //  MyFavoriteList.setLayoutManager(LinearLayoutManager(requireContext()));


        val mySneakerAdapter = SneakersListAdapter(requireContext(),this)

        MyFavoriteList.adapter = mySneakerAdapter

        sneakerViewModel = ViewModelProvider(this).get(SneaskerViewModel::class.java)



        sneakerViewModel.readAllData.observe(viewLifecycleOwner, Observer { sneaker ->
            mySneakerAdapter.setData(sneaker as ArrayList<Sneaker>)
        })






        return view

    }


    override fun onItemClick(item: Sneaker, view: View, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLong(item: Sneaker, view: View, position: Int) {
        TODO("Not yet implemented")
    }


}

