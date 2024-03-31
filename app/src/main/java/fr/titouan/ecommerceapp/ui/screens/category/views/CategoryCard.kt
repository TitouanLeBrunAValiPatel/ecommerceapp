package fr.titouan.ecommerceapp.ui.screens.category.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    onCategoryClicked: (Int) -> Unit,
    category: Category,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onCategoryClicked(category.id) }
    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                text = category.name,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                color = Color.Black,
                maxLines = 2,
                minLines = 2
            )


        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun ProductCardPreview() {
//    val mockProduct = Product(id = 1, name = "Sample Product", description = "Description _dyeç_fgze  fe_zgfçuz euzehg zeg_ezçzeu g eihfgeoigbeou eg ghozehgo eghzeogezhoge ezgoezhogeog ozegeogh gh", price = 10f)
//    EcommerceappTheme {
//        ProductCard(
//            onProductClicked = {  },
//            product = mockProduct,
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}