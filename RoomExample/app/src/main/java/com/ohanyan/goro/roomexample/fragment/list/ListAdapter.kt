package com.ohanyan.goro.roomexample.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ohanyan.goro.roomexample.R
import com.ohanyan.goro.roomexample.model.Book
import kotlinx.android.synthetic.main.recycle_item.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var bookList = emptyList<Book>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = bookList[position]


        holder.itemView.textid.text = currentitem.id.toString()
        holder.itemView.textname.text = currentitem.Bookname
        holder.itemView.textauthor.text = currentitem.Bookauthor

        holder.itemView.rowLayout.setOnClickListener{
            var action =ListFragmentDirections.actionListFragmentToUpdateFragment(currentitem)

            holder.itemView.findNavController().navigate(action)


        }

    }

    fun setData(book: List<Book>) {
        this.bookList = book
        notifyDataSetChanged()
    }

}