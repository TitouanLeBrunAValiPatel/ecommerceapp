package fr.titouan.ecommerceapp.ui.screens.account.password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R

@Composable
fun PasswordScreen(
    password: String,
    onPasswordClick: (oldPassword: String, newPassword: String) -> Unit,

){
    var oldPassword = remember { mutableStateOf("") }
    var newPassword = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .wrapContentWidth()
    ){
        OutlinedTextField(
            value = oldPassword.value,
            onValueChange = { newValue ->  oldPassword.value = newValue },
            label = { Text(stringResource(id = R.string.password_old_passoword)) },
            placeholder = { Text(text = stringResource(id = R.string.password_placeholder_old_password)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = newPassword.value,
            onValueChange = { newValue ->  newPassword.value = newValue },
            label = { Text(stringResource(id = R.string.password_new_passoword)) },
            placeholder = { Text(text = stringResource(id = R.string.password_placeholder_new_password)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            if(password != oldPassword.value) {

            } else {
                onPasswordClick(oldPassword.value, newPassword.value)
            }
        }) {
            Text(text = stringResource(id = R.string.nav_set_password))
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}