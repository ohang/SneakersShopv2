package com.ohanyan.goro.roomexample.fragment.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ohanyan.goro.roomexample.R
import com.ohanyan.goro.roomexample.model.Book
import com.ohanyan.goro.roomexample.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var bookVModel:BookViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_update, container, false)


        bookVModel=ViewModelProvider(this).get(BookViewModel::class.java)

        view.bookname_update_id.setText(args.CurrentBook.Bookname)

        view.authorname_update_id.setText(args.CurrentBook.Bookauthor)


        view.button_update.setOnClickListener{
          updateitem()

        }
        return view
    }

    private fun updateitem(){
        val Bname = bookname_update_id.text.toString()
        val Bauthor=authorname_update_id.text.toString()

        val updateedBook= Book(args.CurrentBook.id,Bname,Bauthor)

        bookVModel.UpdateBook(updateedBook)
        Toast.makeText(requireContext(), "Successfully ", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }


}