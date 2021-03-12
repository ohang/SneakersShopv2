package com.ohanyan.goro.sneakersshop.ui

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Order
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.viewmodels.SneaskerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SneakerPageFragment : Fragment() {


    private val args by navArgs<SneakerPageFragmentArgs>()
    lateinit var sneakerViewModel: SneaskerViewModel
    private val OrderColection = Firebase.firestore.collection("User")
    lateinit var order: Order
    lateinit var currentUser: User
    lateinit var auth: FirebaseAuth
    lateinit var size:RadioButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sneaker_page, container, false)
        val name: TextView
        val price: TextView
        val mainImg: ImageView
        val buybutton: Button
        val RButtonsSizes:RadioGroup
        val helptext:TextView
        val likeButton:ImageButton
        val backButton:ImageButton

        buybutton = view.findViewById(R.id.buybutton)
        name = view.findViewById(R.id.name_id)
        price = view.findViewById(R.id.price_id)
        mainImg = view.findViewById(R.id.mainImgid)
        RButtonsSizes=view.findViewById(R.id.radioGroupsizes)
        helptext=view.findViewById(R.id.helptextid)
        likeButton=view.findViewById(R.id.likeButtonID)
        backButton=view.findViewById(R.id.backButtonID)




        auth = FirebaseAuth.getInstance()

        sneakerViewModel = ViewModelProvider(this).get(SneaskerViewModel::class.java)

        Glide
            .with(requireContext())
            .load(args.sneak.mainImgUrl)
            .into(mainImg)

        name.text = args.sneak.name
        price.text = args.sneak.price

        if(auth.currentUser==null){

            buybutton.visibility=View.GONE
          helptext.visibility=View.VISIBLE

        }


        likeButton.setOnClickListener {

            sneakerViewModel.addSneaker(args.sneak)
            likeButton.setImageResource(R.drawable.ic_reffav)

        }

        backButton.setOnClickListener{
            findNavController().navigate(R.id.action_sneakerPageFragment_to_bottomNavFragment)
        }

        buybutton.setOnClickListener {
            val ChekedSizes:Int=RButtonsSizes.checkedRadioButtonId
            var sizetext:String?=null

            if (ChekedSizes!=-1) {
                size=view.findViewById(ChekedSizes)
                sizetext=size.text.toString()


            CoroutineScope(Dispatchers.Main).launch {
                val user = sneakerViewModel.getUserByEmail(auth.currentUser?.email)
                order = Order(
                    user.name,
                    user.adress,
                    user.phonenumber,
                    user.postcode,
                    user.email,
                    args.sneak.name,
                    args.sneak.price,
                    sizetext
                )

                addOrder(order)

            }
            } else {

                Toast.makeText(context, "Ընտրեք չափսը", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }


    fun addOrder(order: Order) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val query = OrderColection.whereEqualTo("email", auth.currentUser?.email).get().await()
            if (query.documents.isNotEmpty()) {
                for (doc in query) {
                    OrderColection.document(doc.id).collection("MyOrder").add(order)

                }

            }

        } catch (e: Exception) {
        }
    }


}
