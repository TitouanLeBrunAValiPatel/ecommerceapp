package fr.titouan.ecommerceapp.ui.screens.payment.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.ui.screens.payment.PaymentProcessID
import fr.titouan.ecommerceapp.ui.screens.payment.commons.PaymentStateCard

@Composable
fun PaymentSuccessDetail(
    user: MutableState<User?>
) {

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PaymentStateCard(PaymentProcessID.SUCCESS)

    }

}