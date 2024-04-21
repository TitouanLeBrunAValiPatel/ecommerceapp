package fr.titouan.ecommerceapp.ui.screens.account.informations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

            val user = remember {
                viewModel.user
            }


            if (user != null) {
                InformationsScreen(
                    user = user,
                    onSetInformationsClick = { viewModel.setInformations(it) }
                )
            }
        }
    }
    fun NavHostController.navigateToSetInformations() {
        navigate("$Route")
    }
}
