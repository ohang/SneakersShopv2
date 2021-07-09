package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.viewmodels.SneakerViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class UserPageFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    private val UserColection = Firebase.firestore.collection("User")
    lateinit var sneakerViewModel: SneakerViewModel


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

        val tologButton:Button
        val toregButton:Button
        val myorders:Button
        val usericon:ImageView
        val tvbotto:TextView
        val imgbuttonedit:ImageButton
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

        tologButton=view.findViewById(R.id.tolog)
        toregButton=view.findViewById(R.id.toreg)
        usericon=view.findViewById(R.id.usericonid)
        tvbotto=view.findViewById(R.id.tvbotto)
        imgbuttonedit=view.findViewById(R.id.imageButton)

        tvuseremail.text = auth.currentUser?.email
        logoutbt = view.findViewById(R.id.logout)
        sneakerViewModel = ViewModelProvider(this).get(SneakerViewModel::class.java)

        tvmyfavorite.setOnClickListener {

          //  findNavController().navigate(R.id.action_bottomNavFragment_to_myFavoriteFragment2)
            val action =BottomNavFragmentDirections.actionBottomNavFragmentSelf3(currentFragment = 2)
            findNavController().navigate(action)
        }

        tologButton.setOnClickListener {

            findNavController().navigate(R.id.action_bottomNavFragment_to_loginFragment)

        }

        tvorder.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavFragment_to_myOrder)
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

            usericon.visibility=View.GONE
            imgbuttonedit.visibility=View.GONE


            tvhelp.visibility=View.VISIBLE
            tologButton.visibility=View.VISIBLE
            toregButton.visibility=View.VISIBLE
            tvbotto.visibility=View.VISIBLE



        }




     /*   CoroutineScope(Dispatchers.IO).launch {
            val user = auth.currentUser?.email?.let { sneakerViewModel.getUserByEmail(it) }

        tvusername.text = user?.name
        tvuseraddress.text = user?.adress
        tvuserphone.text = user?.phonenumber
        tvuserpostcode.text = user?.postcode
    }
    */

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



        logoutbt.setOnClickListener {

            lifecycleScope.launch {
                auth.signOut()
            }

            findNavController().navigate(R.id.action_bottomNavFragment_to_loginFragment)

        }

        imgbuttonedit.setOnClickListener {


            val dialog= MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.user_edit_layout)

            val editButton=dialog.findViewById<Button>(R.id.applyorderid)

            val addressEdit:TextInputEditText= dialog.findViewById<TextInputEditText>(R.id.addresseditid)
            val phoneEdit:TextInputEditText =dialog.findViewById<TextInputEditText>(R.id.phoneditid)
            val postEdit:TextInputEditText= dialog.findViewById<TextInputEditText>(R.id.updatepostcode)


            CoroutineScope(Dispatchers.Main).launch {
                val emailquery = UserColection.whereEqualTo("email", auth.currentUser?.email)
                    .get().await()
                if (!emailquery.isEmpty()) {
                    val  edituser = emailquery.toObjects(User::class.java)[0]

                    addressEdit.setText(edituser.adress)
                    phoneEdit.setText(edituser.phonenumber)
                    postEdit.setText(edituser.postcode)


                    editButton.setOnClickListener {
                        for (doc in emailquery){
                            try {
                                val newUser= User(1,edituser.name,edituser.surname,addressEdit.text.toString(),postEdit.text.toString(),
                                    phoneEdit.text.toString(),edituser.email)

                                UserColection.document(doc.id).set(newUser)
                                tvusername.text=newUser.name
                                tvuseraddress.text=newUser.adress
                                tvuserphone.text=newUser.phonenumber
                                tvuserpostcode.text=newUser.postcode
                                dialog.hide()
                            }catch (e:Exception){

                            }
                        }
                    }


                }


            }



            dialog.show()



        }





        return view


    }




}