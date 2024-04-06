package fr.titouan.ecommerceapp.ui.screens.account.login.orders.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.model.Category
import fr.titouan.ecommerceapp.model.Order
import fr.titouan.ecommerceapp.ui.commonsUi.getCurrencyPrice
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCard(
    onOrderClicked: (Int) -> Unit,
    order: Order,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onOrderClicked(order.idOrder) }
    ) {

        Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier) {
                Icon(modifier = Modifier
                    .align(Alignment.Top)
                    .padding(top = 20.dp, end = 10.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_package),
                    contentDescription = stringResource(id = R.string.icon_order))
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.orders_order_id_text, order.idOrder),
                        modifier = Modifier
                            .padding(bottom = 10.dp),
                        color = Color.Black,
                    )
                    Text(
                        text = order.date,
                        modifier = Modifier
                            .padding(bottom = 20.dp),
                        color = Color.Black,
                    )

                    Text(
                        text = getCurrencyPrice(price =  order.safeTotalPrice),
                        modifier = Modifier,
                        color = Color.Black,
                    )
                }
            }

            Icon(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
                contentDescription = stringResource(id = R.string.icon_arrow_right
            ))
        }

    }
}
@Preview(showBackground = true)
@Composable
fun OrderCardPreview() {

    EcommerceappTheme {
        OrderCard(
            onOrderClicked = {  },
            order = GetSampleData.getOrder(3)
        )
    }
}