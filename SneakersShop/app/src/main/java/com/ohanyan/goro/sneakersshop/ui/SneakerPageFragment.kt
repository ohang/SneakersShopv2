package com.ohanyan.goro.sneakersshop.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.adapters.SlideVPadapter
import com.ohanyan.goro.sneakersshop.db.Order
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.viewmodels.SneakerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern


class SneakerPageFragment() : Fragment() {


    private val args by navArgs<SneakerPageFragmentArgs>()
    lateinit var sneakerViewModel: SneakerViewModel
    private val UserColection = Firebase.firestore.collection("User")
    private val OrderCollection =Firebase.firestore.collection("Order")
    private val SneakerCollectionMan=Firebase.firestore.collection("Sneakers/manwoman/Man")
    private val SneakerCollectionWoman=Firebase.firestore.collection("Sneakers/manwoman/Woman")


    lateinit var order: Order
    lateinit var auth: FirebaseAuth
    lateinit var size: RadioButton


    val textValid: Pattern = Pattern.compile(
        "^" +

                //    "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[ա-ֆԱ-Ֆa-zA-Z0-9])" +      //any letter
                ".{3,}" +               //at least 4 characters
                "$"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sneaker_page, container, false)
        val name: TextView
        val price: TextView
        val buybutton: Button
        val RButtonsSizes: RadioGroup
        val helptext: TextView
        val likeButton: ToggleButton
        val backButton: ImageButton
        val slideViewPager: ViewPager
        val tabIndicator: TabLayout
        val tvslide: TextView
        val sneakeredit:ImageButton


        auth = FirebaseAuth.getInstance()



        buybutton = view.findViewById(R.id.buybutton)
        name = view.findViewById(R.id.name_id)
        price = view.findViewById(R.id.price_id)
        RButtonsSizes = view.findViewById(R.id.radioGroupsizes)
        helptext = view.findViewById(R.id.helptextid)
        likeButton = view.findViewById(R.id.likeButtonID)
        tabIndicator = view.findViewById(R.id.tab_indicator)
        backButton = view.findViewById(R.id.backButtonID)
        slideViewPager = view.findViewById(R.id.mainImgid)
        tvslide = view.findViewById(R.id.slidetextid)
        sneakeredit=view.findViewById(R.id.edit_sneak_id)


        //   val imgurls = arrayListOf(args.sneak.mainImgUrl, args.sneak.secImgUrl)
        val newlist = args.sneak.mainImgUrl.split(",").toList()

        val slideadapter: SlideVPadapter = SlideVPadapter(requireContext(), newlist)

        slideViewPager.adapter = slideadapter
        tabIndicator?.setupWithViewPager(slideViewPager)
        tvslide.text = "1/" + newlist.size

        tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tvslide.text = "" + (tab.position + 1) + "/" + newlist.size
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })



        sneakerViewModel = ViewModelProvider(this).get(SneakerViewModel::class.java)
        name.text = args.sneak.name
        price.text = args.sneak.price

        if (args.sneak.male == "Man") {
            sizecheck("40", R.id.size1, view)
            sizecheck("41", R.id.size2, view)
            sizecheck("42", R.id.size3, view)
            sizecheck("43", R.id.size4, view)
            sizecheck("44", R.id.size5, view)
            sizecheck("45", R.id.size6, view)
        } else {
            sizecheck("36", R.id.size1, view)
            sizecheck("37", R.id.size2, view)
            sizecheck("38", R.id.size3, view)
            sizecheck("39", R.id.size4, view)
            sizecheck("40", R.id.size5, view)
            sizecheck("41", R.id.size6, view)
        }




        if (auth.currentUser == null) {

            buybutton.visibility = View.GONE
            helptext.visibility = View.VISIBLE

        }


        checklike(likeButton)

        likeButton.setOnClickListener {
            addInMyFavorite(args.sneak, likeButton)
        }



        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_sneakerPageFragment_to_bottomNavFragment)
        }


        sneakeredit.setOnClickListener {
            if(args.sneak.male=="Man"){
                applychanges(SneakerCollectionMan)
            }
            else {
                applychanges(SneakerCollectionWoman)
            }
        }


        buybutton.setOnClickListener {
            val ChekedSizes: Int = RButtonsSizes.checkedRadioButtonId
            var sizetext: String? = null

            if (ChekedSizes != -1) {
                size = view.findViewById(ChekedSizes)
                sizetext = size.text.toString()

                showDialog(args.sneak.mainImgUrl, args.sneak.name!!, args.sneak.price!!, sizetext)


            } else {

                Toast.makeText(context, "Ընտրեք չափսը", Toast.LENGTH_SHORT).show()
            }
        }



        return view
    }


    fun addOrder(order: Order) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val query = UserColection.whereEqualTo("email", auth.currentUser?.email).get().await()
            if (query.documents.isNotEmpty()) {
                for (doc in query) {
                    UserColection.document(doc.id).collection("MyOrder").add(order)
                    OrderCollection.add(order)

                }


            }

        } catch (e: Exception) {
        }
    }

    fun sizecheck(sz: String, dr: Int, vw: View) {

        if (sz !in args.sneak.sizes) {
            vw?.findViewById<RadioButton>(dr)?.isClickable = false
            vw?.findViewById<RadioButton>(dr)?.setText(sz)


            val colorValue = ContextCompat.getColor(requireContext(), R.color.black)

            vw?.findViewById<RadioButton>(dr)?.setBackgroundColor(colorValue)

        } else {
            vw?.findViewById<RadioButton>(dr)?.setText(sz)

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun showDialog(imgurl: String, name: String, price: String, size: String) {

        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.order_layout)

        val newlist = args.sneak.mainImgUrl.split(",").toList()
        Glide.with(requireContext().applicationContext)
            .load(newlist[2])
            .into(dialog.findViewById<ImageView>(R.id.sneakimg))

        dialog.findViewById<TextView>(R.id.sneaknameid).text = name
        dialog.findViewById<TextView>(R.id.sneakprice).text = price + " դրամ"
        dialog.findViewById<TextView>(R.id.sizeid).text = "Չափսը՝ " + size


        CoroutineScope(Dispatchers.Main).launch {
            //   val user = sneakerViewModel.getUserByEmail(auth.currentUser?.email)
            val emailquery = UserColection.whereEqualTo("email", auth.currentUser?.email)
                .get().await()
            if (!emailquery.isEmpty()) {
                val user = emailquery.toObjects(User::class.java)[0]

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val formatted = current.format(formatter)



                dialog.findViewById<TextView>(R.id.aboutorderid).text =
                    "Հարգելի " + user.name + " " +
                            user.surname + " Պատվերը հաստատելու" +
                            " դեպքում մեր մասնագետները կկապնվեն ձեր հետ"

                val newlist = args.sneak.mainImgUrl.split(",").toList()
                order = Order(
                    user.name,
                    user.adress,
                    user.phonenumber,
                    user.postcode,
                    user.email,
                    args.sneak.name,
                    newlist[0],
                    args.sneak.price,
                    size,
                    formatted,
                    "Ճանապարհին է"
                )

                dialog.findViewById<Button>(R.id.applyorderid).setOnClickListener {
                    addOrder(order)
                    dialog.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ձեր Պատվերը հաջողությամբ գրանցվել է",
                        Toast.LENGTH_LONG
                    ).show()
                }


            }

        }


        dialog.show()


    }

    fun addInMyFavorite(favsneaker: Sneaker, likeButton: ToggleButton) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val query =
                    UserColection.whereEqualTo("email", auth.currentUser?.email).get().await()

                if (query.documents.isNotEmpty()) {
                    for (doc in query) {

                        val addor = UserColection.document(doc.id).collection("MyFavorite")
                            .whereEqualTo("mainImgUrl", favsneaker.mainImgUrl).get().await()
                        if (addor.documents.isEmpty()) {
                            UserColection.document(doc.id).collection("MyFavorite")
                                .add(favsneaker)
                            //      likeButton.setImageResource(R.drawable.ic_reffav)
                        } else {
                            for (sneak in addor) {
                                UserColection.document(doc.id).collection("MyFavorite")
                                    .document(sneak.id).delete()
                                //    likeButton.setImageResource(R.drawable.ic_favorite)

                            }

                        }
                    }

                }

            } catch (e: Exception) {
            }
        }

    fun checklike(likeButton: ToggleButton) = CoroutineScope(Dispatchers.Main).launch {
        try {
            val query = UserColection.whereEqualTo("email", auth.currentUser?.email).get().await()

            if (query.documents.isNotEmpty()) {
                for (doc in query) {

                    val addor = UserColection.document(doc.id).collection("MyFavorite")
                        .whereEqualTo("mainImgUrl", args.sneak.mainImgUrl).get().await()
                    if (addor.documents.isEmpty()) {
                        // likeButton.setImageResource(R.drawable.ic_favorite)

                    } else {

                        likeButton.isChecked=true
                        //   likeButton.setImageResource(R.drawable.ic_reffav)


                    }
                }

            }

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

    fun applychanges(ManWomanCol:CollectionReference){
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.edit_sneaker_item)

        val editButton = dialog.findViewById<Button>(R.id.applysneakerid)

        val nameEdit: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.nameeditid)
        val sizesEdit: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.sizeseditid)
        val priceEdit: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.updatepriceid)



        CoroutineScope(Dispatchers.Main).launch {

            val emailquery = ManWomanCol.whereEqualTo("name", args.sneak.name)
                .get().await()
            if (!emailquery.isEmpty()) {

                nameEdit.setText(args.sneak.name)
                sizesEdit.setText(args.sneak.sizes)
                priceEdit.setText(args.sneak.price)
            }
            editButton.setOnClickListener {
                if(validate(nameEdit)&&validate(sizesEdit)&&validate(priceEdit)){
                    for (doc in emailquery) {
                        try {
                            val newSneaker = Sneaker(
                                args.sneak.id,
                                priceEdit.text.toString(),
                                args.sneak.mainImgUrl,
                                sizesEdit.text.toString(),
                                args.sneak.male,
                                nameEdit.text.toString()
                            )


                                ManWomanCol.document(doc.id).set(newSneaker)
                                dialog.hide()
                        } catch (e: Exception) {

                        }
                    }
                }




            }


        }



        dialog.show()


    }

    fun deletecurrentsneaker(ManWomanCol:CollectionReference){



        CoroutineScope(Dispatchers.Main).launch {

            val emailquery = ManWomanCol.whereEqualTo("name", args.sneak.name)
                .get().await()

                    for (doc in emailquery) {
                        try {

                            ManWomanCol.document(doc.id).delete()
                        } catch (e: Exception) {

                        }
                    }



        }





    }


}


