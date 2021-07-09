package com.ohanyan.goro.twolayouts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ohanyan.goro.twolayouts.R
import com.ohanyan.goro.twolayouts.data.MainItem
import com.ohanyan.goro.twolayouts.data.appicons
import java.lang.reflect.Type
import java.util.*
import kotlin.math.E

class MyAdapter(var context:Context,var list: MutableList<MainItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==2) {
            return CategoryViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_item_layout, parent, false)
            )

        }
        else
          return AppIconViewHolder( LayoutInflater.from(parent.context)
           .inflate(R.layout.app_icon_layout, parent, false))

    }

    override fun getItemViewType(position: Int): Int {
     if (list[position].categoryname!=null){
         return 2
     } else
        return 0
    }



    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (this.getItemViewType(position)==2){

            var category: MainItem =list[position]
            var catholder:CategoryViewHolder= holder as CategoryViewHolder

            catholder.name.text=category.categoryname
        }
        else{
             var app:MainItem=list[position]
             var appholder:AppIconViewHolder=holder as AppIconViewHolder

            appholder.appname.text=app.app?.name

            appholder.bind(app)
        }
    }



    inner class CategoryViewHolder(
        itemView: View,
        var name:TextView=itemView.findViewById(R.id.item_name,)
    ):RecyclerView.ViewHolder(itemView){


    }

    inner class AppIconViewHolder(  itemView: View,
    var img:ImageView=itemView.findViewById(R.id.app_icon),
    var appname:TextView=itemView.findViewById(R.id.appname)

                                    ):RecyclerView.ViewHolder(itemView){



     fun bind(item:MainItem){

         context?.applicationContext?.let {
             Glide
                 .with(it)
                 .load(item.app?.icon)
                 .into(img)
         }

     }
    }

}