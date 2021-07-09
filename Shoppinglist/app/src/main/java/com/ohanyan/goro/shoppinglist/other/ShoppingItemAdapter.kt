package com.ohanyan.goro.shoppinglist.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ohanyan.goro.shoppinglist.R
import com.ohanyan.goro.shoppinglist.data.db.entities.ShoppingItem
import com.ohanyan.goro.shoppinglist.ui.ShoppingViewModel
import kotlinx.android.synthetic.main.shopping_item.view.*

class ShoppingItemAdapter(var items:List<ShoppingItem>,
                          private val viewModel:ShoppingViewModel
):RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item,parent,false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount(): Int {
      return  items.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
         val currentItem=items[position]

        holder.itemView.tvName.text=currentItem.name
        holder.itemView.tvAmount.text= currentItem.amount.toString()

        holder.itemView.ivDelete.setOnClickListener{
            viewModel.delete(currentItem)
        }

    }

    inner class ShoppingViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){



    }
}