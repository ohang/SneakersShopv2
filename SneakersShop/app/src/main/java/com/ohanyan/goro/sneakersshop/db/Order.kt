package com.ohanyan.goro.sneakersshop.db

data class Order(
    var Username:String?="",
    var Useraddress:String?="",
    var Userphonemuber: String?="",
    var Userpostcode:String?="",
    var Useremail:String?="",
    var snekname:String?="",
    var snekimgurl:String?="",
    var sneakprice:String?="",
    var sneaksize:String?="",
    var date:String?="",
    var orderStatus:String?=""
)
