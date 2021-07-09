package com.ohanyan.goro.sneakersshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Order
import kotlin.contracts.contract


class MyOrdersAdapter(
    var context: Context, options: FirestoreRecyclerOptions<Order>
) : FirestoreRecyclerAdapter<Order, MyOrdersAdapter.OrderViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.order_item_view,
                parent,
                false
            )
        )
    }


    inner class OrderViewHolder(
        itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun bind(item: Order) {
            val price: TextView
            val name:TextView
            val size:TextView
            val img: ImageView
            val tvdate:TextView
            val tvstatus:TextView


            price=itemView.findViewById(R.id.orderpriceid)
            name=itemView.findViewById(R.id.ordersneaknameid)
            size=itemView.findViewById(R.id.ordersize)
            img=itemView.findViewById(R.id.orderimgid)
            tvdate=itemView.findViewById(R.id.orderdateid)
            tvstatus=itemView.findViewById(R.id.orderstatus)

            price.text="Գինը՝ "+item.sneakprice+" դրամ"
            name.text=item.snekname
            size.text="Չափսը՝ "+item.sneaksize
            tvdate.text=item.date
            tvstatus.text=item.orderStatus

            if (tvstatus.text=="Հասել Է"){

                val colorValue = ContextCompat.getColor(context, R.color.green)

                tvstatus.setTextColor(colorValue)
            }

            Glide
                .with(context.applicationContext)
                .load(item.snekimgurl)
                .into(img)



        }

    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int, model: Order) {


        holder.bind(model)


    }


}





