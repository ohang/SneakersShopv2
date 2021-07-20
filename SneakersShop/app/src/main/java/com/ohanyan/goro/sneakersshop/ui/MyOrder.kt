package com.ohanyan.goro.sneakersshop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.MyOrdersAdapter
import com.ohanyan.goro.sneakersshop.db.Order

class MyOrder:Fragment() {


    lateinit var MyOrders: RecyclerView
    lateinit var auth: FirebaseAuth
    private val UserColection = Firebase.firestore.collection("User")
    private lateinit var fradapter: MyOrdersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.myorder_layout, container, false)


        auth = FirebaseAuth.getInstance()



        MyOrders = view.findViewById(R.id.myorder_list_id)




        UserColection.whereEqualTo("email", auth.currentUser?.email).get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (doc in it) {
                    val query: Query =UserColection.document(doc.id).collection("MyOrder")
                    val options = FirestoreRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order::class.java)
                        .build()


                    fradapter = MyOrdersAdapter(requireContext(), options)

                    MyOrders.setHasFixedSize(true)
                    MyOrders.layoutManager = LinearLayoutManager(requireContext())

                    MyOrders.adapter = fradapter
                    fradapter.startListening()

                }
            }

        }








        return view

    }

}