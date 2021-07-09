        package com.ohanyan.goro.sneakersshop.ui

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.viewmodels.SneakerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.regex.Pattern

class RegistrationFragment : Fragment() {

    private val UserrColection = Firebase.firestore.collection("User")
    lateinit var sneakerViewModel: SneakerViewModel
    lateinit var auth: FirebaseAuth


    val PASSWORD: Pattern = Pattern.compile(
        "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{7,}" +               //at least 7 characters
                "$"
    );

    val textValid: Pattern = Pattern.compile(
        "^" +

            //    "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[ա-ֆԱ-Ֆa-zA-Z0-9])" +      //any letter
                ".{3,}" +               //at least 4 characters
                "$"
    );


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        sneakerViewModel = ViewModelProvider(this).get(SneakerViewModel::class.java)


        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        val regbt: Button
        val name: TextInputEditText
        val surname:TextInputEditText
        val addres: TextInputEditText
        val postcode: TextInputEditText
        val phone: TextInputEditText
        val email: TextInputEditText
        val password: TextInputEditText


        regbt = view.findViewById(R.id.rebbt)
        name = view.findViewById(R.id.edit_name_id)
        surname=view.findViewById(R.id.edit_surname_id)
        addres = view.findViewById(R.id.edit_address_id)
        postcode = view.findViewById(R.id.edit_postcode_id)
        phone = view.findViewById(R.id.edit_phone_id)
        email = view.findViewById(R.id.edit_emal_id)
        password = view.findViewById(R.id.passid)


        regbt.setOnClickListener {
            //validateEmail(name,password)
                    validate(name)
                    validate(surname)
                    validate(addres)
                    validate(postcode)
                    validate(phone)
                    validateEmail(email)
                    validatePassword(password)
            if (validate(name) &&
                validate(surname) &&
                validate(addres) &&
                validate(postcode) &&
                validate(phone) &&
                validateEmail(email) &&
                validatePassword(password)
            ) {
                val us1 = User(1, name.text.toString(),surname.text.toString(),
                    addres.text.toString(), postcode.text.toString(),
                    phone.text.toString(), email.text.toString()
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        auth.createUserWithEmailAndPassword(
                            email.text.toString(),
                            password.text.toString()
                        ).addOnSuccessListener {
                            saveUser(us1)
                            findNavController().navigate(R.id.action_registrationFragment_to_bottomNavFragment)
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), "Այդ Էլ հասցեվ օգտագտեր արդեն գոյություն ունի", Toast.LENGTH_LONG).show()
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


    fun validate(text: TextInputEditText): Boolean {

        val textinput = text.text?.trim()

        if (!textinput?.isEmpty()!! && textValid.matcher(textinput).matches()) {
            return true
        } else {
            text.error = "Սխալ"
            return false
        }
    }
    fun validateEmail(text: TextInputEditText): Boolean {

        val textinput = text.text?.trim()

        if (!textinput?.isEmpty()!! && Patterns.EMAIL_ADDRESS.matcher(textinput).matches()) {
            return true
        } else {
            text.error = "Նշել էլ Հասցեի ճիշտ ձև"
            return false
        }
    }


    fun validatePassword(Pass: TextInputEditText): Boolean {
        val passinput = Pass.text?.trim()



        if (passinput?.isNotEmpty()!! && PASSWORD.matcher(passinput).matches()) {
            //  Edit.error = "field cant be empty"
            return true
        } else {
            Pass.error = "Գաղտնաբառը պետք է պարունակի ամենաքիչը 7 տառ"
            return false


        }


    }



}