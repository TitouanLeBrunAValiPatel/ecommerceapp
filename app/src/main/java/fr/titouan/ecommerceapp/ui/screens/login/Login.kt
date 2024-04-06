package fr.titouan.ecommerceapp.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginScreen
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.Orders

object Login {
    private const val RouteBase = "Auth"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.loginNavigationEntry (
        setTitle: (String) -> Unit,
        onLogged: () -> Unit
    ) {
        composable(route = Route) {
            LoginScreen(
                onLogged = onLogged,
                title = Title,
                setTitle = setTitle
            )
        }

    }
}