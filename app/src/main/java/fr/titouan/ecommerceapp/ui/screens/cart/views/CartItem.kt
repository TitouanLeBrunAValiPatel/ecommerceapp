package fr.titouan.ecommerceapp.ui.screens.cart.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.model.ProductInCart
import fr.titouan.ecommerceapp.ui.commonsUi.getCurrencyPrice
import fr.titouan.ecommerceapp.ui.components.NumberInputField
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme

@Composable
fun CartItem(
    onProductClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    cartAction: MutableState<Boolean>,
    productInCart: ProductInCart,
    onDeleteProductInCart: () -> Unit,
    onQuantityChanged: (ProductInCart, Int) -> Unit

) {
    val quantity = remember { mutableIntStateOf(productInCart.quantity) }

    Column(
        modifier = modifier
            .clickable {
                onProductClicked(productInCart.product.id)
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = ColorPainter(Color.Red), // Remplacez Color.Red par votre image
                contentDescription = stringResource(id = R.string.product_image, productInCart.product.name),
                modifier = Modifier.size(100.dp)
            )
            Column (modifier = Modifier.padding(start = 8.dp)){
                Row {
                    Text(
                        text = productInCart.product.name,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 3,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = getCurrencyPrice(quantity = quantity.value, price = productInCart.product.price),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, end = 8.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = Color(productInCart.color.getLongCodeColor()),
                                shape = MaterialTheme.shapes.large
                            )
                    )
                    Text(
                        text = productInCart.color.safeName,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = stringResource(id = R.string.cart_quantity_text),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                    )
                    NumberInputField(
                        productId = productInCart.product.id,
                        colorId = productInCart.color.id,
                        quantity = quantity,
                        onQuantityChanged = onQuantityChanged(productInCart, quantity.value)
                    )

                    if(cartAction.value) {
                        IconButton(
                            modifier = Modifier,
                            onClick = { onDeleteProductInCart() } ) {
                            Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                                tint = Color.Red,
                                contentDescription = stringResource(id = R.string.icon_delete_button)
                            )
                        }
                    }


                }

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    val cartAction = remember { mutableStateOf(false) }
    val productInCart : ProductInCart = GetSampleData.getProductInCart()
    EcommerceappTheme {
        CartItem(
            onProductClicked = { it },
            cartAction = cartAction,
            productInCart = productInCart,
            onDeleteProductInCart =  {  },
            onQuantityChanged = { productInCart, i ->  }
        )
    }
}

