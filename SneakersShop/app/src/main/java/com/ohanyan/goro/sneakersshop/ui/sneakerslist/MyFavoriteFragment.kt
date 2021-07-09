package com.ohanyan.goro.sneakersshop.ui.sneakerslist

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.RecyclerViewAdapter
import com.ohanyan.goro.sneakersshop.adapters.SneakersListAdapter
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.viewmodels.SneakerViewModel


class MyFavoriteFragment : Fragment(),RecyclerViewAdapter.OnItemClickListener {

    lateinit var MyFavoriteList: RecyclerView
    lateinit var auth: FirebaseAuth
    lateinit var viewModel: SneakerViewModel
    private val  UserColection = Firebase.firestore.collection("User")
    lateinit var fradapter: SneakersListAdapter
    lateinit var query:CollectionReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_favorite, container, false)

        auth = FirebaseAuth.getInstance()
        viewModel= SneakerViewModel(Application())
        MyFavoriteList = view.findViewById(R.id.myfavorite_list_id)
        viewModel = ViewModelProvider(this).get(SneakerViewModel::class.java)




      //  viewModel.delete()

        UserColection.whereEqualTo("email", auth.currentUser?.email).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    UserColection.document(it.first().id).collection("MyFavorite").get().addOnSuccessListener {
                        for (document in it){

                            viewModel.addSneaker(document.toObject(Sneaker::class.java))
                        }
                    }

                    query =
                        UserColection.document(it.first().id).collection("MyFavorite")


                } else {

                    query = Firebase.firestore.collection("null")
                }






            }
        var myadapter=RecyclerViewAdapter(requireContext(),this)

        MyFavoriteList.setHasFixedSize(true)
        val gridLayoutmanager = GridLayoutManager(requireContext(), 2)

        MyFavoriteList.layoutManager = gridLayoutmanager
        MyFavoriteList.setHasFixedSize(true)

        MyFavoriteList.adapter=myadapter

        viewModel.readAllData.observe(viewLifecycleOwner, Observer { sneaker ->
            myadapter.setData(sneaker as ArrayList<Sneaker>) })

        viewModel.delete()
        return view

    }




    override fun onStart() {
        super.onStart()


    }


    override fun onStop() {

        super.onStop()



    }






    override fun onItemClick(item: Sneaker, view: View, position: Int) {

        val action =BottomNavFragmentDirections.actionBottomNavFragmentToSneakerPageFragment(item)
        findNavController().navigate(action)
        //   Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show()
    }

    override fun onItemLong(item: Sneaker, view: View, position: Int) {
        // sneakerViewModel.addSneaker(item)
        //  Toast.makeText(context, "addeed", Toast.LENGTH_LONG).show()

    }


}

