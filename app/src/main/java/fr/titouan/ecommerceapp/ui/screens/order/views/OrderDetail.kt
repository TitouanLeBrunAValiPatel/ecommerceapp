package fr.titouan.ecommerceapp.ui.screens.order.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.model.Order
import fr.titouan.ecommerceapp.ui.commonsUi.getCurrencyPrice
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.views.OrderCard

@Composable
fun OrderDetail(
    order: Order
) {

    Column(
        modifier = Modifier,
    ) {
        OrderCard(order = order)

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(order.products?.get(0)?.product?.safeImages)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = order.products?.get(0)?.product?.name?.let {
                stringResource(R.string.product_image,
                    it
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "order.name")
        Text(text = "order.description")
        Text(text = getCurrencyPrice(price = order.totalPrice!!))
    }

}