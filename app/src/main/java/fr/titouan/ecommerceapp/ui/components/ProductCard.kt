package fr.titouan.ecommerceapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.data.sharedPreferences.CartManager
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.ui.commonsUi.getCurrencyPrice
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    onProductClicked: (Int) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    val cartManager = CartManager(LocalContext.current)
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onProductClicked(product.id) }
    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(product.safeImages[0])
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.product_image, product.name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
            )

            Text(
                text = product.name,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                color = Color.Black,
                maxLines = 2,
                minLines = 2
            )


            Text(
                text = product.description,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 4,
                minLines = 4
            )

            Text(
                text = getCurrencyPrice(price = product.price),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                maxLines = 1,
            )

            TextButton(onClick = { cartManager.addToCart(
                1024,
                1,
                quantityAddToProduct = 1
            ) },
                content = {
                    Text(text = "add to cart")
                }
            )
        }
    }
}
//@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    val mockProduct = GetSampleData.getProduct(1)
    EcommerceappTheme {
        ProductCard(
            onProductClicked = {  },
            product = mockProduct,
            modifier = Modifier.fillMaxWidth()
        )
    }
}



