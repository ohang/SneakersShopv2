package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_m_w_sneakers_list.*


class MWSneakersListFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_m_w_sneakers_list, container, false)

        val mwviewpager:ViewPager2
        mwviewpager=view.findViewById(R.id.mwsnviewpager)
        val tb:TabLayout
        tb=view.findViewById(R.id.mwsntablayout)
        val mwfragment= arrayListOf<Fragment>(SneakListFrgmt(),SneakListFrgmt())

        val adapter= ViewPagerAdapter(
            mwfragment,
            requireActivity().supportFragmentManager,
            lifecycle
        )

       mwviewpager.adapter=adapter

        TabLayoutMediator(tb,mwviewpager){tab,position->

        }.attach()

        tb.getTabAt(0)?.text="Տղսմարդու"
        tb.getTabAt(1)?.text="Կանանցի"








        return  view
    }

}