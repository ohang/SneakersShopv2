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
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Sneaker

class SneakersListAdapter(var context:Context, options: FirestoreRecyclerOptions<Sneaker>,private var onItemClickListener:
SneakersListAdapter.OnItemClickListener
) : FirestoreRecyclerAdapter<Sneaker, SneakersListAdapter.SneakerViewHolder>(options){
    private var mySneakersList = emptyList<Sneaker>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        return SneakerViewHolder(   LayoutInflater.from(parent.context).inflate(
            R.layout.carview_sneak_item,
            parent,
            false
        ),onItemClickListener)
    }

    override fun onBindViewHolder(holder: SneakersListAdapter.SneakerViewHolder, position: Int, model: Sneaker) {

        holder.bind(model)


    }



    inner class SneakerViewHolder(itemview: View,private var onItemClickListener: SneakersListAdapter.OnItemClickListener)
        :RecyclerView.ViewHolder(itemview){

        fun bind(item: Sneaker){
            val price : TextView
            val img:ImageView

            img=itemView.findViewById(R.id.snek_img_id)
            price=itemView.findViewById(R.id.price_id)
            val newlist=item.mainImgUrl.split(",").toList()

            price.text=item.price+" դրամ"

            Glide
                .with(context.applicationContext)
                .load(newlist[0])
                .into(img)


            itemView.setOnClickListener {
                onItemClickListener.onItemClick(
                    item,itemView,adapterPosition)

            }

            itemView.setOnLongClickListener {
                onItemClickListener.onItemLong(
                    item,itemView,adapterPosition
                )
                true
            }


        }

    }



    interface OnItemClickListener {
        fun onItemClick(item: Sneaker, view: View, position: Int)
        fun onItemLong(item: Sneaker, view: View, position: Int)

    }

    fun setData(sneaker: ArrayList<Sneaker>) {
        this.mySneakersList = sneaker
        notifyDataSetChanged()
    }


}