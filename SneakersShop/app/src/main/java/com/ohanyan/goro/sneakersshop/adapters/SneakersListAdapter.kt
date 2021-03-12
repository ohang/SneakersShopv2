package com.ohanyan.goro.sneakersshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Sneaker

class SneakersListAdapter(var context: Context,private var onItemClickListener: OnItemClickListener):RecyclerView.Adapter<SneakersListAdapter.SneakerViewHolder>(){
    private var mySneakersList = emptyList<Sneaker>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        return SneakerViewHolder(   LayoutInflater.from(parent.context).inflate(
            R.layout.carview_sneak_item,
            parent,
            false
        ),onItemClickListener)
    }

    override fun getItemCount(): Int {
       return mySneakersList.size

    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val currentSneaker=mySneakersList[position]
        holder.bind(currentSneaker)



    }

    inner class SneakerViewHolder(itemview: View,private var onItemClickListener: OnItemClickListener)
        :RecyclerView.ViewHolder(itemview){

        fun bind(item: Sneaker){
            val price : TextView
            val img:ImageView

            img=itemView.findViewById(R.id.snek_img_id)
            price=itemView.findViewById(R.id.price_id)

            price.text=item.price+" դրամ"

            Glide
                .with(context.applicationContext)
                .load(item.mainImgUrl)
                .into(img)
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(
                    item,itemView,adapterPosition)

            }

            itemView.setOnLongClickListener{
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