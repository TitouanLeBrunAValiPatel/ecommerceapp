package fr.titouan.ecommerceapp.ui.screens.account.password

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.screens.account.informations.Informations
import fr.titouan.ecommerceapp.ui.screens.account.views.AccountViewModel

object Password {
    private const val RouteBase = "Account/Password"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.setPasswordNavigationEntry(
        setTitle: (String) -> Unit,
        viewModel: AccountViewModel
    ) {
        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) }
        )
        {
            Title.value = stringResource(id = R.string.password_title)
            setTitle(Title.value)
            PasswordScreen(
                password = "",
                onPasswordClick = { oldPassword, newPassword -> {  }  }
            )
        }
    }
    fun NavHostController.navigateToSetPassowrd() {
        navigate("$Route")
    }
}