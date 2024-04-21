package fr.titouan.ecommerceapp.ui.screens.product.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.ui.commonsUi.getCurrencyPrice

@Composable
fun ProductDetail(
    product: Product
) {

    Column(
        modifier = Modifier,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(product.safeImages[0])
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.product_image, product.name),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f) // Aspect ratio for image
            )

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = Color.Red,
                modifier = Modifier
                    .size(86.dp) // Adjust size of the icon
                    .padding(16.dp) // Adjust position of the icon
                    .align(Alignment.TopEnd) // Align the icon to top end (top right)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = product.name)
        Text(text = product.description)
        Text(text = getCurrencyPrice(price = product.price))
    }



}