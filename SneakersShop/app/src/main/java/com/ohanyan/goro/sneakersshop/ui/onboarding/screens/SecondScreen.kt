package com.ohanyan.goro.sneakersshop.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.ohanyan.goro.sneakersshop.R


class SecondScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_second_screen, container, false)

        val viewpager= activity?.findViewById<ViewPager2>(R.id.viewPager)
        val ntbutton: TextView

        ntbutton=view.findViewById(R.id.tvnext2)
        ntbutton.setOnClickListener{
            viewpager?.currentItem=2
        }

        return view
    }


}