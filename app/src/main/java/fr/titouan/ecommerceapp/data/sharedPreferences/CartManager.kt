package fr.titouan.ecommerceapp.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductInCart
import fr.titouan.ecommerceapp.ui.screens.cart.views.CartViewModel

class CartManager(context: Context) {
    private val PREFS_NAME = "CartSession"
    private val KEY_CART = "Cart"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()


    fun clearCart() {
        editor.clear()
        editor.apply()

    }

    fun addToCart(productId: Int, colorId: Int, quantityAddToProduct: Int) {
        val cartSet = sharedPreferences.getStringSet(KEY_CART, HashSet())?.toMutableSet()

        val existingProductEntry = cartSet?.find { it.startsWith("$productId:$colorId:") }

        if (existingProductEntry != null) {
            val existingQuantity = existingProductEntry.split(":")[2].toIntOrNull() ?: 1
            var newTotalQuantity = existingQuantity + quantityAddToProduct
            if(newTotalQuantity < 1) {
                newTotalQuantity = existingQuantity
            }
            cartSet.remove(existingProductEntry)
            cartSet.add("$productId:$colorId:$newTotalQuantity")
        } else {
            val newProductInCart = "$productId:$colorId:$quantityAddToProduct"
            cartSet?.add(newProductInCart)
        }
        Log.d(Debug.TAG, "$cartSet")

        editor.putStringSet(KEY_CART, cartSet)
        editor.apply()

        Log.d("ADDCART", cartSet.toString())
    }

    fun removeToCart(productId: Int, colorId: Int) {
        val cartSet = sharedPreferences.getStringSet(KEY_CART, HashSet())?.toMutableSet()

        val existingProductEntry = cartSet?.find { it.startsWith("$productId:$colorId:") }

        if (existingProductEntry != null) {
            cartSet?.remove(existingProductEntry)
        }

        editor.putStringSet(KEY_CART, cartSet)
        editor.apply()

        Log.d("ADDCART", cartSet.toString())
    }





    suspend fun getContentCart(cartViewModel: CartViewModel): List<ProductInCart>? {
        val cartSet = sharedPreferences.getStringSet(KEY_CART, HashSet())?.toMutableSet()
        Log.d("TITOUAN", "$cartSet")
        val productsInCart: MutableList<ProductInCart> = mutableListOf()

        if (cartSet != null) {
            for (cartString in cartSet) {
                try {
                    val details = cartString.split(":")
                    if (details.size == 3) {
                        val productId: Int = details[0].toInt()
                        val colorId: Int = details[1].toInt()
                        val quantity: Int = details[2].toInt()
                        Log.d(Debug.TAG, "$productId : color : $colorId : qty : $quantity")
                        val productInCart =
                            cartViewModel.getProductFromCart(
                                productId = productId,
                                colorId = colorId,
                                quantity = quantity
                            )
                        if(productInCart != null) {
                            productsInCart.add(productInCart)
                        } else {
                            removeToCart(
                                productId = productId,
                                colorId = colorId
                            )
                        }
                    }
                } catch (e: Exception) {
                    clearCart()
                    Log.e("getContentCart", "Erreur lors de la récupération du produit du panier", e)
                }
            }
        }

        return productsInCart
    }


}