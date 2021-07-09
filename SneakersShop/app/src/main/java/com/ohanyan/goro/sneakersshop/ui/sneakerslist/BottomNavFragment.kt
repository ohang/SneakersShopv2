package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ohanyan.goro.sneakersshop.R

class BottomNavFragment() : Fragment() {

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



        when(args.currentFragment){
           1-> {
               setCurrentFragment(MWSneakersListFragment())
               bottomNavigationView.setSelectedItemId(R.id.mwsnfragment)
           }
            2-> {
                setCurrentFragment(MyFavoriteFragment())
                bottomNavigationView.setSelectedItemId(R.id.myFavoriteFragment)
            }
           3-> {
               setCurrentFragment(UserPageFragment())
               bottomNavigationView.setSelectedItemId(R.id.userPageFragment)

           }


       }






        bottomNavigationView.setOnNavigationItemSelectedListener {


            when (it.itemId) {
                R.id.myFavoriteFragment -> {
                    setCurrentFragment(MyFavoriteFragment())
                }
                R.id.userPageFragment ->{

                 setCurrentFragment(UserPageFragment())
                }
                R.id.mwsnfragment -> {
                    setCurrentFragment(MWSneakersListFragment())
                }}
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
