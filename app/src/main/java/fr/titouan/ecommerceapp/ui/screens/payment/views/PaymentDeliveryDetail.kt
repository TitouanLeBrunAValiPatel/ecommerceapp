package fr.titouan.ecommerceapp.ui.screens.payment.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.ui.screens.payment.PaymentProcessID
import fr.titouan.ecommerceapp.ui.screens.payment.commons.PaymentStateCard

@Composable
fun PaymentDeliveryDetail(
    user: MutableState<User?>,
    onNextButtonClicked: () -> Unit
) {
    var selectedOption by remember { mutableStateOf("option1") }
    val userNameState = remember { mutableStateOf(user.value?.name ?: "") }
    val userAddressState = remember { mutableStateOf(user.value?.safePostCode ?: "") }
    val userCityState = remember { mutableStateOf(user.value?.safeCity ?: "") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PaymentStateCard(PaymentProcessID.DELIVERY)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == "option1",
                onClick = { selectedOption = "option1" },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = stringResource(id = R.string.payment_radio_button_delivery_address))
        }

        OutlinedTextField(
            value = userNameState.value,
            onValueChange = { newValue ->
                userNameState.value = newValue
                user.value?.name = newValue

            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = userAddressState.value,
            onValueChange = { newValue ->
                userAddressState.value = newValue
                user.value?.postCode = newValue

            },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = userCityState.value,
            onValueChange = { newValue ->
                userCityState.value = newValue
                user.value?.city = newValue

            },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = { onNextButtonClicked() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.payment_next_process))
        }
    }

}