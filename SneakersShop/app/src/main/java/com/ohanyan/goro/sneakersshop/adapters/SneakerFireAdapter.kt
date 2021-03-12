package com.ohanyan.goro.sneakersshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Sneaker
import kotlinx.coroutines.withContext


class SneakerFireAdapter(var context:Context,options: FirestoreRecyclerOptions<Sneaker>,private var onItemClickListener: OnItemClickListener) : FirestoreRecyclerAdapter<Sneaker, SneakerFireAdapter.SneakViewHolder>(options) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SneakViewHolder {
        return SneakViewHolder(   LayoutInflater.from(parent.context).inflate(
            R.layout.sneaker_item_view,
            parent,
            false),onItemClickListener)
    }



    override fun onBindViewHolder(holder: SneakViewHolder, position: Int, model: Sneaker) {

        holder.bind(model)


    }

    inner  class SneakViewHolder(itemView: View,private var onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model:Sneaker){
            val name: TextView
            val price : TextView
            val img:ImageView
            img =itemView.findViewById(R.id.Sneaker_photo)
            name=itemView.findViewById(R.id.sneaker_name_id)
            price=itemView.findViewById(R.id.sneaker_price_id)

            Glide
                .with(context.applicationContext)
                .load(model.mainImgUrl)
                .into(img)
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(
                    model,itemView,adapterPosition)

            }

            itemView.setOnLongClickListener {
                onItemClickListener.onItemLong(
                    model,itemView,adapterPosition
                )
                true
            }

            itemView.setOnClickListener{
              onItemClickListener.onItemClick(
                  model,itemView,adapterPosition)

            }




            name.text=model.name
            price.text=model.price
        }




    }


    interface OnItemClickListener {
        fun onItemClick(item: Sneaker, view: View, position: Int)
        fun onItemLong(item: Sneaker, view: View, position: Int)

    }
}