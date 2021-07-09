package com.ohanyan.goro.hayadaran.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ohanyan.goro.hayadaran.R
import com.ohanyan.goro.hayadaran.RecyclerViewAdapter
import com.ohanyan.goro.hayadaran.data.Book
import com.ohanyan.goro.hayadaran.data.BookViewModel
import kotlinx.android.synthetic.main.fragment_recycle.*
import kotlinx.android.synthetic.main.fragment_recycle.view.*


class RecycleFragment : Fragment(),RecyclerViewAdapter.OnItemClickListener {
    private val args   by navArgs <RecycleFragmentArgs>()


    lateinit var bookViewModel: BookViewModel



    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recycle, container, false)


        val  myrecview = view.recyclerView
        val gridLayoutmanager = GridLayoutManager(requireContext(), 3)

        myrecview.layoutManager = gridLayoutmanager
        myrecview.setHasFixedSize(true)

        val recycleadapter = RecyclerViewAdapter(requireContext(),this)

        myrecview.adapter = recycleadapter

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

      if (args.namesearch=="book" || args.namesearch=="" ) {

            bookViewModel.readAllData.observe(viewLifecycleOwner, Observer { book ->
                recycleadapter.setData(book as ArrayList<Book>) })
      } else {
      bookViewModel.searchData(args.namesearch).observe(viewLifecycleOwner, Observer { book ->
         recycleadapter.setData(book as ArrayList<Book>)
      })
   }











        return view


    }


    override fun onItemClick(item: Book, view: View, position: Int) {

        val action=RecycleFragmentDirections.actionRecycleFragmentToBookFragment(item)
        findNavController().navigate(action)


    }

    override fun onItemLong(item: Book, view: View, position: Int) {


    }
}
