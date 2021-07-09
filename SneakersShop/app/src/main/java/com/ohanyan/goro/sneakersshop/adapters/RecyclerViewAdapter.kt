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

class RecyclerViewAdapter(
    var context: Context?, private var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var mySneakerList = emptyList<Sneaker>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemholder = LayoutInflater.from(parent.context)
            .inflate(R.layout.carview_sneak_item, parent, false)

        return MyViewHolder(itemholder, onItemClickListener)
    }



    override fun getItemCount(): Int {

        return mySneakerList.size
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mySneakerList[position])
    }


    inner class MyViewHolder(
        itemView: View, private var onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Sneaker) {
            val price : TextView
            val img: ImageView

            img=itemView.findViewById(R.id.snek_img_id)
            price=itemView.findViewById(R.id.price_id)
            val newlist=item.mainImgUrl.split(",").toList()

            price.text=item.price+" դրամ"















































            ///////////////////////////////////////////////////
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(
                    item,itemView,adapterPosition)
            }
            itemView.setOnLongClickListener {
                onItemClickListener.onItemLong(
                    item,itemView,adapterPosition
                )
                true }


        }


    }


    interface OnItemClickListener {
        fun onItemClick(item: Sneaker, view: View, position: Int)
        fun onItemLong(item: Sneaker, view: View, position: Int)

    }

    fun setData(sneaker: ArrayList<Sneaker>) {
        this.mySneakerList= sneaker
        notifyDataSetChanged()
    }

}