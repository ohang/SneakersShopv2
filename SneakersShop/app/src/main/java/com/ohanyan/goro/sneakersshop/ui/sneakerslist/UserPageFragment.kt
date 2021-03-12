package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.viewmodels.SneaskerViewModel
import kotlinx.android.synthetic.main.fragment_user_page.*
import kotlinx.coroutines.*


class UserPageFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    private val UserColection = Firebase.firestore.collection("User")
    lateinit var sneakerViewModel: SneaskerViewModel


    @InternalCoroutinesApi
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_page, container, false)
        val logoutbt: Button
        val tvusername: TextView
        val tvuseremail: TextView
        val tvuseraddress: TextView
        val tvuserphone: TextView
        val tvuserpostcode: TextView
        val tvmyfavorite:TextView
        val tvhelp:TextView
        val tvorder:TextView
        val tvordercount:TextView
        val tvfavoritecount:TextView
        val tologButton:Button
        val toregButton:Button
        val bundle = this.arguments
        auth = FirebaseAuth.getInstance()

        tvusername = view.findViewById(R.id.usernameid)
        tvuseremail = view.findViewById(R.id.userEmailid)
        tvuseraddress = view.findViewById(R.id.useraddressid)
        tvuserphone = view.findViewById(R.id.phonenumberid)
        tvuserpostcode = view.findViewById(R.id.postcodeid)
        tvmyfavorite=view.findViewById(R.id.likedid)
        tvhelp=view.findViewById(R.id.helplogin)
        tvorder=view.findViewById(R.id.myOrdersID)
        tvordercount=view.findViewById(R.id.MyOrdersCount)
        tvfavoritecount=view.findViewById(R.id.LikedCount)
        tologButton=view.findViewById(R.id.tolog)
        toregButton=view.findViewById(R.id.toreg)

        tvuseremail.text = auth.currentUser?.email
        logoutbt = view.findViewById(R.id.logout)
        sneakerViewModel = ViewModelProvider(this).get(SneaskerViewModel::class.java)

        tvmyfavorite.setOnClickListener {

            findNavController().navigate(R.id.action_bottomNavFragment_to_myFavoriteFragment2)
        }

        tologButton.setOnClickListener {

            findNavController().navigate(R.id.action_bottomNavFragment_to_loginFragment)

        }

        toregButton.setOnClickListener {

            findNavController().navigate(R.id.action_bottomNavFragment_to_registrationFragment)
        }
        if(auth.currentUser==null){

            tvusername.visibility=View.GONE
            tvuseraddress.visibility=View.GONE
            tvuserphone.visibility=View.GONE
            tvuserpostcode.visibility=View.GONE
            tvuseremail.visibility=View.GONE
            logoutbt.visibility=View.GONE
            tvmyfavorite.visibility=View.GONE
            tvorder.visibility=View.GONE
            tvordercount.visibility=View.GONE
            tvfavoritecount.visibility=View.GONE
            tvhelp.visibility=View.VISIBLE
            tologButton.visibility=View.VISIBLE
            toregButton.visibility=View.VISIBLE

        }


        CoroutineScope(Dispatchers.IO).launch {
            val user = auth.currentUser?.email?.let { sneakerViewModel.getUserByEmail(it) }

        tvusername.text = user?.name
        tvuseraddress.text = user?.adress
        tvuserphone.text = user?.phonenumber
        tvuserpostcode.text = user?.postcode
    }

/*
        CoroutineScope(Dispatchers.Main).launch {
            val emailquery = UserColection.whereEqualTo("email", auth.currentUser?.email)
                .get().await()
            if (!emailquery.isEmpty()) {


                val user = emailquery.toObjects(User::class.java)[0]
                tvusername.text=user.name
                tvuseraddress.text=user.adress
                tvuserphone.text=user.phonenumber
                tvuserpostcode.text=user.postcode



            }

        }

 */

        logoutbt.setOnClickListener {
            lifecycleScope.launch {
                auth.signOut()
            }

        }




        return view


    }
}