package fr.titouan.ecommerceapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.data.sharedPreferences.CartManager
import fr.titouan.ecommerceapp.model.ProductInCart
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme

@Composable
fun NumberInputField(
    productId : Int,
    colorId : Int,
    quantity: MutableState<Int>,
    quantityToAdd: Int = 1,
    onQuantityChanged: Unit

) {
    val cartManager = CartManager(LocalContext.current)

    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary_color1)
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.width(20.dp).height(20.dp),
            onClick = {
                cartManager.addToCart(
                    productId = productId,
                    colorId = colorId,
                    quantityAddToProduct = -quantityToAdd
                )
                var newValue : Int = quantity.value - quantityToAdd
                if(newValue > 1){
                    quantity.value = newValue
                } else {
                    quantity.value = 1
                }
                onQuantityChanged
            }
        ) {
            Text(text = "-")

        }
        Text(text = quantity.value.toString(), modifier = Modifier.padding(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary_color1)
            ),
            modifier = Modifier.width(20.dp).height(20.dp),
            onClick = {
                cartManager.addToCart(
                    productId = productId,
                    colorId = colorId,
                    quantityAddToProduct = quantityToAdd
                )
                val newValue = quantity.value + quantityToAdd
                quantity.value = newValue
                onQuantityChanged

            },
            contentPadding = PaddingValues(0.dp),
            ){
            Text(text = "+")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NumberInputFieldPreview() {
    EcommerceappTheme {
        val quantity = remember {
            mutableStateOf(2)
        }
        NumberInputField(
            productId = 1,
            colorId = 2,
            quantity = quantity,
            onQuantityChanged = Unit
        )
    }
}
