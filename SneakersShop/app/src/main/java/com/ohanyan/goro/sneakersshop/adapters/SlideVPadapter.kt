package com.ohanyan.goro.sneakersshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ohanyan.goro.sneakersshop.R
import kotlinx.android.synthetic.main.fragment_sneaker_page.view.*


class SlideVPadapter(var context: Context, var imageUrls: List<String>) : PagerAdapter() {

    var onViewPagerListener: (position:Int) -> Unit = {}


    var itemPosition = 0

    override fun getCount(): Int {
       return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img:ImageView= ImageView(context)
        var tvslide:TextView= TextView(context)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val layoutScreen = inflater.inflate(R.layout.fragment_sneaker_page, null)


        layoutScreen.findViewById<TextView>(R.id.slidetextid).text="dfffd"

        itemPosition = position
        onViewPagerListener.invoke(position)
        val option = RequestOptions()
        option.centerCrop()
        Glide.with(context.applicationContext)
            .load(imageUrls[position])
            .apply(option)
            .into(img)


        tvslide.text="gogo"

        container.addView(img)


       return img
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view==`object`
    }
}