package com.ohanyan.goro.doublerecycle.adapter

import com.ohanyan.goro.doublerecycle.R

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.lang.reflect.Type
import java.util.*
import kotlin.math.E

class MainAdapter(
    var context: Context?,var myList: List<String>) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemholder = LayoutInflater.from(parent.context)
            .inflate(R.layout.mainitem, parent, false)


        return MyViewHolder(itemholder)
    }

    override fun getItemCount(): Int {

        return myList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myList[position])

    }


    inner class MyViewHolder(
        itemView: View,
        var itemname:TextView=itemView.findViewById(R.id.mainitemname),
        var secondrec:RecyclerView=itemView.findViewById(R.id.secondrecyler)
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {

            itemname.text=item
            secondrec.setHasFixedSize(true)

            val secondlayoutmanager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL
                ,false)

            secondrec.layoutManager=secondlayoutmanager

            val secondadapter=SecondAdapter(context,myList)

            secondrec.adapter=secondadapter

        }
    }




}