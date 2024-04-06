package fr.titouan.ecommerceapp.ui.screens.login.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R

@Composable
fun LoginCard(
    onForgotPasswordClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onLoginClick: (email: String, password: String) -> Unit,
    ){
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email.value,
                onValueChange = { newValue ->  email.value = newValue },
                label = { Text("Email") },
                placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { newValue ->  password.value = newValue },
                label = { Text("Mot de passe") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(id = R.string.login_button_forget_password))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onLoginClick(email.value, password.value) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.login_button_signin))
            }
            Button(
                onClick = onGoogleLoginClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.login_button_signin_with_google))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onCreateAccountClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.login_button_signup))
            }
        }
    }
}
