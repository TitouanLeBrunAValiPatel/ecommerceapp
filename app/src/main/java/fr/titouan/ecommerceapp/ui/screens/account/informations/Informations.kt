package fr.titouan.ecommerceapp.ui.screens.account.informations

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
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.ui.screens.account.views.AccountViewModel

object Informations {
    private const val RouteBase = "Account/Informations"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.setInformationsNavigationEntry(
        setTitle: (String) -> Unit,
        viewModel: AccountViewModel
    ) {
        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) }
        )
        {
            Title.value = stringResource(id = R.string.informations_title)

            setTitle(Title.value)
            InformationsScreen(
                user = User(1,"", "", gender = ""),
                onSetInformationsClick = {}
            )
        }
    }
    fun NavHostController.navigateToSetInformations() {
        navigate("$Route")
    }
}
