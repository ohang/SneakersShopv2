package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.remote.Datastore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.SneakerFireAdapter
import com.ohanyan.goro.sneakersshop.adapters.SneakersListAdapter
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.ui.SplashFragmentDirections
import com.ohanyan.goro.sneakersshop.viewmodels.SneaskerViewModel
import kotlinx.coroutines.launch


class SneakListFrgmt : Fragment(),SneakerFireAdapter.OnItemClickListener {

    lateinit var SneakerList: RecyclerView
    lateinit var sneakerViewModel: SneaskerViewModel


    private lateinit var fradapter: SneakerFireAdapter

    private val SneakerColection = Firebase.firestore.collection("Sneakers")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sneakers_list, container, false)


        sneakerViewModel = ViewModelProvider(this).get(SneaskerViewModel::class.java)


        SneakerList = view.findViewById(R.id.sneakers_list_id)

        setUpRecyclerView()

        return view

    }

    private fun setUpRecyclerView() {
        val query: Query = SneakerColection.orderBy(
           "id",
        Query.Direction.DESCENDING
        )

        val options = FirestoreRecyclerOptions.Builder<Sneaker>()
            .setQuery(query, Sneaker::class.java)
            .build()
        fradapter = SneakerFireAdapter(requireContext(),options,this)
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
     //   Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show()
    }

    override fun onItemLong(item: Sneaker, view: View, position: Int) {
          sneakerViewModel.addSneaker(item)
      //  Toast.makeText(context, "addeed", Toast.LENGTH_LONG).show()

    }


}



