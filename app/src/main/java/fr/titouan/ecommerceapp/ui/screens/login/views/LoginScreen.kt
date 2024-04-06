package fr.titouan.ecommerceapp.ui.screens.login.views

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.components.ErrorScreen
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun LoginScreen(
    onLogged: () -> Unit,
    title: MutableState<String>,
    setTitle: (String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val loginViewModel : LoginViewModel = viewModel(factory = LoginViewModel.Factory)
    val loginUiState = loginViewModel.loginUiState
    val isLoggedUi = remember { mutableStateOf(loginViewModel.isLogged) }

    LaunchedEffect(loginViewModel.isLogged) {
        Log.d(Debug.SESSION, "Launcheffect loginvm")
        isLoggedUi.value = loginViewModel.isLogged
    }

    when(loginUiState) {
        is LoginUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier
                .width(30.dp)
                .height(30.dp)
            )
        }
        else -> {
            if(!isLoggedUi.value) {
                title.value = stringResource(id = R.string.login_title)
                setTitle(title.value)
                LoginCard(
                    onCreateAccountClick = {

                    } ,
                    onForgotPasswordClick = {

                    },
                    onGoogleLoginClick = {
                        coroutineScope.launch {
                            loginViewModel.logWithGoogle(context)
                            if (loginViewModel.isLogged) {
                                onLogged()
                            }
                        }
                    } ,
                    onLoginClick = {email, password ->
                        loginViewModel.login(email, password)
                        if (loginViewModel.isLogged) {
                            onLogged()
                        }
                    }
                )

            } else {
                title.value = stringResource(id = R.string.logout_title)
                setTitle(title.value)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LogoutCard(
                        onLogoutClick = { loginViewModel.logout() }
                    )
                }

            }
        }
    }



}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val title = remember {
        mutableStateOf("false")
    }
    LoginScreen(
        onLogged = {},
        title = title,
        setTitle = { it }
    )
}
