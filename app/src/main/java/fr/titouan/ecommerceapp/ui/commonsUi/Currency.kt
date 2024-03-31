package fr.titouan.ecommerceapp.ui.commonsUi

import java.text.NumberFormat

fun getCurrencyPrice(quantity : Int = 1, price: Float) :String {
    val totalAmount = quantity * price
    return NumberFormat.getCurrencyInstance().format(totalAmount)
}