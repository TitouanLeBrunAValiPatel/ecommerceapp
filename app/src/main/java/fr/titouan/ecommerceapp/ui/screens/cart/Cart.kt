package fr.titouan.ecommerceapp.ui.screens.cart

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.components.ErrorScreen
import fr.titouan.ecommerceapp.ui.screens.cart.Cart.Screen
import fr.titouan.ecommerceapp.ui.screens.cart.views.CartRowScreen
import fr.titouan.ecommerceapp.ui.screens.cart.views.CartUiState
import fr.titouan.ecommerceapp.ui.screens.cart.views.CartViewModel
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme



object Cart {
    private const val RouteBase = "Cart"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    @Composable
    fun Screen(
        setTitle: (String) -> Unit,
        cartAction: MutableState<Boolean>,
        onProductClicked: (Int) -> Unit

    ) {
        val cartViewModel: CartViewModel = viewModel(factory = CartViewModel.Factory)
        var totalPrice = remember { mutableFloatStateOf(cartViewModel.totalPrice) }
        var productsInCartUiState = remember { mutableStateOf(cartViewModel.mCartUiState) }
        var productsInCart = remember { mutableStateOf(cartViewModel.mProductsInCart) }
        Title.value = stringResource(id = R.string.cart_title)
        setTitle(Title.value)

        LaunchedEffect(cartViewModel.totalPrice) {
            totalPrice.value = cartViewModel.totalPrice
        }
        LaunchedEffect(cartViewModel.mCartUiState, cartViewModel.mProductsInCart) {
            productsInCartUiState.value = cartViewModel.mCartUiState
            productsInCart.value = cartViewModel.mProductsInCart
        }
        LaunchedEffect(null) {
            cartViewModel.getProductsInCart()
        }
        when (productsInCartUiState.value) {

            is CartUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is CartUiState.Success -> {
                if(productsInCart.value.isNullOrEmpty()) {
                    Text(text = stringResource(id = R.string.cart_empty))
                } else {
                    Text(text = totalPrice.value.toString())
                    CartRowScreen(
                        onProductClicked = onProductClicked,
                        productsInCart = productsInCart,
                        cartAction = cartAction,
                        actionDeleteProductInCart = { cartViewModel.removeProductToCart(it) },
                        totalPrice = totalPrice
                    ) { productInCart, newQuantity ->
                        cartViewModel.updateQuantity(
                            productInCart,
                            newQuantity
                        )
                    }
                }

            }

            is CartUiState.Error -> {
                Log.d(Debug.TAG, (productsInCartUiState.value as CartUiState.Error).message)
                ErrorScreen(
                    retryAction = { cartViewModel.getSafeProductsInCart() },
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    val action = remember { mutableStateOf(true) }

    EcommerceappTheme {
        Screen(
            setTitle = { it },
            cartAction = action,
            onProductClicked = { it }
        )
    }
}