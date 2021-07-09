package com.ohanyan.goro.roomexample.fragment.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ohanyan.goro.roomexample.R
import com.ohanyan.goro.roomexample.model.Book
import com.ohanyan.goro.roomexample.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class addFragment : Fragment() {

   private lateinit var bookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add, container, false)

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        view.button_add.setOnClickListener{

            insertDatatoDatabase()
        }

        return view
    }


    private fun insertDatatoDatabase(){

        val bookname=bookname_ed.text.toString()
        val authorname=authorname_ed.text.toString()


        if(inputcheck(bookname,authorname)){
          val newbook=
              Book(0, bookname, authorname)
          bookViewModel.addBook(newbook)

            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else
        {
            Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_SHORT).show()

        }

    }

    private fun inputcheck(bookname:String,authorname:String):Boolean{

        return !(TextUtils.isEmpty(bookname) && TextUtils.isEmpty(authorname)  )
    }

}