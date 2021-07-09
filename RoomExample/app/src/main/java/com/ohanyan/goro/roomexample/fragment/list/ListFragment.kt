package com.ohanyan.goro.roomexample.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ohanyan.goro.roomexample.R
import com.ohanyan.goro.roomexample.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private lateinit var bookViewModel: BookViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        var adapter = ListAdapter()
        var recycleview = view.recyclerView
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(requireContext())
        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        bookViewModel.readAllData.observe(viewLifecycleOwner, Observer { book ->
            adapter.setData(book)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        return view


    }

}
