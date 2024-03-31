package fr.titouan.ecommerceapp.ui.commonsUi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme


@Composable
fun ProductsGridScreen(
    onProductClicked: (Int) -> Unit,
    products: List<Product>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.padding(10.dp),
            contentPadding = contentPadding,
        ) {
            items(items = products,
                key = { product -> product.id }) { product ->
                ProductCard(
                    onProductClicked = onProductClicked,
                    product = product,
                    modifier = Modifier.fillMaxSize()
                        .padding(10.dp)
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun ProductsGridScreenPreview() {
    val mockProducts = GetSampleData.getProducts(8)
    EcommerceappTheme {
        ProductsGridScreen(
            onProductClicked = { productId -> /* action on click, par exemple afficher un toast avec l'ID du produit */ },
            products = mockProducts
        )

    }
}
