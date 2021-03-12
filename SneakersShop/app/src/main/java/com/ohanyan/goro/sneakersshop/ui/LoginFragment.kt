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
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.remote.Datastore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Sneaker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.google.firebase.auth.FirebaseAuth
import com.ohanyan.goro.sneakersshop.db.User
import kotlinx.coroutines.flow.first
import java.util.regex.Pattern


class LoginFragment : Fragment() {
    private val SneakerColection = Firebase.firestore.collection("Sneakers")
    private val UserrColection = Firebase.firestore.collection("User")
    lateinit var datastore: DataStore<Preferences>
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

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        datastore = context?.createDataStore("login")!!

        val loginbt: Button
        val logProgres:ProgressBar
        val regbt: TextView
        val skipButton: TextView
        val emailedit: TextInputLayout
        val passedit: TextInputLayout
        emailedit = view.findViewById(R.id.textinputemail)
        passedit = view.findViewById(R.id.textinputpassword)


        loginbt = view.findViewById(R.id.button)
        skipButton = view.findViewById(R.id.skiplogin)
        regbt = view.findViewById(R.id.registrationid)
        logProgres=view.findViewById(R.id.progressBarid)


        skipButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                findNavController().navigate(R.id.action_loginFragment_to_bottomNavFragment)
            }

        }

        loginbt.setOnClickListener {

            val emailinput = emailedit.editText?.text?.toString()
            val passinput = passedit.editText?.text?.toString()

            if (validateEmail(emailedit) || validatePassword(passedit)) {
              logProgres.visibility=View.VISIBLE
                CoroutineScope(Dispatchers.Main).launch {


                    auth.signInWithEmailAndPassword(emailinput!!,passinput!!).addOnSuccessListener {

                        findNavController().navigate(R.id.action_loginFragment_to_bottomNavFragment)
                    }.addOnFailureListener {

                        logProgres.visibility=View.INVISIBLE
                        Toast.makeText(context, "Սխալ Էլ Հասցե Կամ Գաղտնաբառ", Toast.LENGTH_SHORT).show()
                    }




                   /* CoroutineScope(Dispatchers.IO).launch {
                        val emailquery = UserrColection.whereEqualTo("email", auth.currentUser?.email)
                            .get().await()
                        if (!emailquery.isEmpty()) {


                            val user = emailquery.toObjects(User::class.java)[0]





                        }

                    */


                }


            }


        }




        regbt.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return view


    }

    fun validateEmail(Edit: TextInputLayout): Boolean {
        val emailinput = Edit.editText?.text?.trim()


        if (!emailinput?.isEmpty()!! && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
            return true
        } else {

            Edit.error = "Սխալ Էլ Հասցե"

            return false

        }


    }

    fun validatePassword(Pass: TextInputLayout): Boolean {
        val passinput = Pass.editText?.text?.trim()



        if (!passinput?.isEmpty()!! && PASSWORD.matcher(passinput).matches()) {
            //  Edit.error = "field cant be empty"
            return true
        } else {
            Pass.error = "Սխալ Գաղտնաբառ"

            return false


        }


    }

    private fun saveSneaker(sneaker: Sneaker) = CoroutineScope(Dispatchers.IO).launch {
        try {
            SneakerColection.add(sneaker).await()
            // Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_LONG).show()

        } catch (e: Exception) {

        }


    }




}






