package com.ohanyan.goro.sneakersshop.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ohanyan.goro.sneakersshop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ThirdScreen : Fragment() {
    lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     val view= inflater.inflate(R.layout.fragment_third_screen, container, false)
        val ntbutton: TextView

        ntbutton=view.findViewById(R.id.tvnext3)

        auth= FirebaseAuth.getInstance()


        if (auth.currentUser!=null){

            ntbutton.setOnClickListener {
                findNavController().navigate(R.id.action_viewPagerFragment_to_bottomNavFragment)
            }
        } else {
            ntbutton.setOnClickListener{
                findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            }
        }







        return view
    }



}