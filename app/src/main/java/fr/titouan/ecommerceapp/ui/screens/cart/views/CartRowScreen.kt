package fr.titouan.ecommerceapp.ui.screens.cart.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.data.sharedPreferences.CartManager
import fr.titouan.ecommerceapp.model.ProductInCart

@Composable
fun CartRowScreen(
    onProductClicked: (Int) -> Unit,
    productsInCart: MutableState<List<ProductInCart>>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cartAction: MutableState<Boolean>,
    actionDeleteProductInCart: (ProductInCart) -> Unit,
    totalPrice: MutableState<Float>,
    onQuantityChanged: (ProductInCart, Int) -> Unit

) {

    val cartManager = CartManager(LocalContext.current)
    Box {
        LazyColumn(
            modifier = Modifier.padding(10.dp),
            contentPadding = contentPadding,
        ) {
            items(productsInCart.value) { productInCart ->

                CartItem(
                    onProductClicked = onProductClicked,
                    cartAction = cartAction,
                    productInCart = productInCart,
                    modifier = Modifier.padding(vertical = 10.dp),
                    onDeleteProductInCart = {
                        cartManager.removeToCart(productId =  productInCart.product.id,
                            colorId = productInCart.color.id)
                        actionDeleteProductInCart(productInCart)
                    },
                    onQuantityChanged = onQuantityChanged

                )
            }
        }
        Text(text = stringResource(id = R.string.cart_total_price_text),
            modifier = Modifier.align(Alignment.BottomStart))
        Text(text = stringResource(id = R.string.cart_total_price,totalPrice.value),
            modifier = Modifier.align(Alignment.BottomEnd))
    }





}