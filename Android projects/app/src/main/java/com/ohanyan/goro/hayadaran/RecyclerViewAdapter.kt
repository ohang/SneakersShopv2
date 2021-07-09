package com.ohanyan.goro.hayadaran

import android.content.Context
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ohanyan.goro.hayadaran.data.Book
import kotlinx.android.synthetic.main.cardview_book_item.view.*


class RecyclerViewAdapter(
    var context: Context?, private var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var myBookList = emptyList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemholder = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_book_item, parent, false)


        return MyViewHolder(itemholder, onItemClickListener)
    }

    override fun getItemCount(): Int {

        return myBookList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myBookList[position])

    }


    inner class MyViewHolder(
        itemView: View, private var onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Book) {

            itemView.book_title_id.text = item.Title

            context?.let {
                Glide.with(it)
                    .load(item.ImgURL)
                    .into(itemView.book_img_id)
            }
            itemView.book_img_id.setOnClickListener {
                onItemClickListener.onItemClick(
                    myBookList[adapterPosition],
                    itemView,
                    adapterPosition
                )


            }

            itemView.book_img_id.setOnLongClickListener {
                onItemClickListener.onItemLong(
                    myBookList[adapterPosition],
                    itemView,
                    adapterPosition
                )
                true

            }


        }


    }


    interface OnItemClickListener {
        fun onItemClick(item: Book, view: View, position: Int)
        fun onItemLong(item: Book, view: View, position: Int)

    }

    fun setData(book: ArrayList<Book>) {
        this.myBookList = book
        notifyDataSetChanged()
    }

}