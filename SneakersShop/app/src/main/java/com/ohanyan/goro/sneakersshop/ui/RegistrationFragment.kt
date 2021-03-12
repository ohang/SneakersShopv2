package com.ohanyan.goro.sneakersshop.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.viewmodels.SneaskerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.PasswordAuthentication
import java.util.regex.Pattern

class RegistrationFragment : Fragment() {

    private val UserrColection = Firebase.firestore.collection("User")
    lateinit var sneakerViewModel: SneaskerViewModel
    lateinit var auth: FirebaseAuth


    val PASSWORD: Pattern = Pattern.compile(
        "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$"
    );


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        sneakerViewModel = ViewModelProvider(this).get(SneaskerViewModel::class.java)


        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        val regbt: Button
        val name: TextInputEditText
        val addres: TextInputEditText
        val postcode: TextInputEditText
        val phone: TextInputEditText
        val email: TextInputEditText
        val password: TextInputEditText

        regbt = view.findViewById(R.id.rebbt)
        name = view.findViewById(R.id.edit_name_id)
        addres = view.findViewById(R.id.edit_address_id)
        postcode = view.findViewById(R.id.edit_postcode_id)
        phone = view.findViewById(R.id.edit_phone_id)
        email = view.findViewById(R.id.edit_emal_id)
        password = view.findViewById(R.id.passid)


        regbt.setOnClickListener {
            //validateEmail(name,password)
            if (validate(name) && validate(addres) && validate(postcode) && validate(phone) && validate(
                    email
                ) && validate(password)
            ) {
                val us1 = User(
                    1, name.text.toString(), addres.text.toString(), postcode.text.toString(),
                    phone.text.toString(), email.text.toString()
                )

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        auth.createUserWithEmailAndPassword(
                            email.text.toString(),
                            password.text.toString()
                        ).await()

                        if (auth.currentUser != null) {
                            saveUser(us1)
                            saveUserInDB(us1)
                            findNavController().navigate(R.id.action_registrationFragment_to_bottomNavFragment)

                        }

                    } catch (e: Exception) {

                    }
                }


            }
        }




        return view
    }

    private fun saveUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            UserrColection.add(user).await()

        } catch (e: Exception) {
        }

    }

    private fun registerUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).isSuccessful

            } catch (e: Exception) {

            }
        }
    }

    fun saveUserInDB(user: User){

        sneakerViewModel = ViewModelProvider(this).get(SneaskerViewModel::class.java)
        sneakerViewModel.addUser(user)

    }
    fun validate(text: TextInputEditText): Boolean {

        val textinput = text.text?.trim()

        if (!textinput?.isEmpty()!!) {
            return true
        } else {
            text.error = "Սխալ"
            return false
        }
    }


}