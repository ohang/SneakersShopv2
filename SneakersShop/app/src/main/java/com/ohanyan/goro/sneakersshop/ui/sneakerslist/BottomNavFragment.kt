package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ohanyan.goro.sneakersshop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BottomNavFragment : Fragment() {

    private val args by navArgs<BottomNavFragmentArgs>()
    val bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bottom_nav, container, false)
        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView)


        setCurrentFragment(MWSneakersListFragment())



        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.myFavoriteFragment -> setCurrentFragment(MyFavoriteFragment())
                R.id.userPageFragment -> setCurrentFragment(UserPageFragment())
                R.id.mwsnfragment -> setCurrentFragment(MWSneakersListFragment())
            }
            true
        }
        return view
    }

    private fun setCurrentFragment(fragment: Fragment) {

        parentFragmentManager.beginTransaction()?.apply {
            fragment.arguments = bundle
            replace(R.id.NavHostMenu, fragment)
            commit()
        }


    }


}
