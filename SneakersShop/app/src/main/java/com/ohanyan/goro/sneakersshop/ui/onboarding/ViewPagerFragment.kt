package com.ohanyan.goro.sneakersshop.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.ViewPagerAdapter
import com.ohanyan.goro.sneakersshop.ui.onboarding.screens.FirstScreen
import com.ohanyan.goro.sneakersshop.ui.onboarding.screens.SecondScreen
import com.ohanyan.goro.sneakersshop.ui.onboarding.screens.ThirdScreen


class ViewPagerFragment : Fragment() {





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList= arrayListOf<Fragment>(
            FirstScreen(),SecondScreen(),ThirdScreen()
        )


        val adapter= ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val vl: ViewPager2
        vl=view.findViewById(R.id.viewPager)

        vl.adapter=adapter









        return view
    }



}