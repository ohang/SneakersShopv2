package com.ohanyan.goro.doublerecycle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ohanyan.goro.doublerecycle.R


class SecondAdapter(
    var context: Context?, var myList: List<String>) :
    RecyclerView.Adapter<SecondAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemholder = LayoutInflater.from(parent.context)
            .inflate(R.layout.seconditem, parent, false)


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
        var itemname: TextView =itemView.findViewById(R.id.seconditemname)
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {
            itemname.text=item
        }
    }




}