package com.ohanyan.goro.hayadaran.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ohanyan.goro.hayadaran.R
import kotlinx.android.synthetic.main.fragment_book.view.*


class BookFragment : Fragment() {
    private val args by navArgs<BookFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_book, container, false)
        view.Booknamef_id.text = args.thisBook.Title
      //  view.Author_id.text=args.thisBook.Category

        view.descriptontextid.text=args.thisBook.Description
        view.textauthorid.text=args.thisBook.Author
            // Loadin img

        view.read_id.setOnClickListener{

            val uri = Uri.parse(args.thisBook.LinkURL)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        Glide.with(requireActivity().applicationContext)
            .load(args.thisBook.ImgURL)
            .into(view.fragmentimg)

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear()
    }



}