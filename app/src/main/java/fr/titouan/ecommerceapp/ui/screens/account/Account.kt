package fr.titouan.ecommerceapp.ui.screens.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.screens.login.Login
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginScreen
import fr.titouan.ecommerceapp.ui.screens.account.views.AccountScreen

object Account {
    private const val RouteBase = "Account"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.accountNavigationEntry (
        setTitle: (String) -> Unit,
        onSetPasswordClick: () -> Unit,
        onSetInformationsClick: () -> Unit
        ) {

        composable(route = Route) {
            Title.value = stringResource(id = R.string.account_title)
            setTitle(Title.value)
            AccountScreen(
                onSetInformationsClick = onSetInformationsClick,
                onSetPasswordClick = onSetPasswordClick
            )
        }

    }
}