package com.ohanyan.goro.shoppinglist.ui

import com.ohanyan.goro.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {

    fun onAddButtonClicked(item:ShoppingItem)
}