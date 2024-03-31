package fr.titouan.ecommerceapp.ui.screens.category.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.model.Category

@Composable
fun CategoriesRowScreen(
    onCategoryClicked: (Int) -> Unit,
    categories: List<Category>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.padding(10.dp),
            contentPadding = contentPadding,
        ) {
            items(items = categories,
                key = { category -> category.id }) { category ->
                CategoryCard(
                    onCategoryClicked = onCategoryClicked,
                    category = category,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }
    }

}
//@Preview(showBackground = true)
//@Composable
//fun ProductsGridScreenPreview() {
//    val mockProducts = List(3) {
//        Product(id = it, name = "Product $it", description = "Description $it", price = it.toFloat())
//    }
//    val mockProducts2 = List(3) {
//        Product(id = it + 3, name = "Product $it \n ehfouegoeuq fefo", description = "Description $it \n ofqeofehfoehe eifei \n fpiqehfeipqghqe fhesoighe", price = it.toFloat())
//    }
//    val mock = mockProducts + mockProducts2
//    EcommerceappTheme {
//        CategoriesRowScreen(
//            onProductClicked = { productId -> /* action on click, par exemple afficher un toast avec l'ID du produit */ },
//            products = mock
//        )
//
//    }
//}