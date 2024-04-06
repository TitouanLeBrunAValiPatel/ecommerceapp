package fr.titouan.ecommerceapp.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.titouan.ecommerceapp.ui.screens.account.Account.accountNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.account.informations.Informations.navigateToSetInformations
import fr.titouan.ecommerceapp.ui.screens.account.informations.Informations.setInformationsNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.login.Login
import fr.titouan.ecommerceapp.ui.screens.login.Login.loginNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.Orders
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.Orders.ordersNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.account.password.Password.navigateToSetPassowrd
import fr.titouan.ecommerceapp.ui.screens.account.password.Password.setPasswordNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.account.views.AccountViewModel
import fr.titouan.ecommerceapp.ui.screens.cart.Cart
import fr.titouan.ecommerceapp.ui.screens.category.Category
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.ui.screens.product.Product.navigateToProduct
import fr.titouan.ecommerceapp.ui.screens.product.Product.productNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.products.Products.navigateToProductsCategory
import fr.titouan.ecommerceapp.ui.screens.products.Products.productsCategoryNavigationEntry

@Composable
fun NavHostEcommerApp(
    innerPadding: PaddingValues,
    navController : NavHostController,
    currentScreenTitle: MutableState<String>,
    action: MutableState<Boolean>

) {
    val accountViewModel: AccountViewModel = viewModel(factory =  AccountViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = Home.Route,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = Home.Route) {
            Home.Screen(
                setTitle = { currentScreenTitle.value = it },
                onProductClicked = { navController.navigateToProduct(it) },
            )
        }
        composable(route = Category.Route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -it }) + fadeIn(
                    animationSpec = tween(2000)
                )
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(
                    animationSpec = tween(2000)
                )
            }
        ) {
            Category.Screen(
                setTitle = { currentScreenTitle.value = it },
                onCategoryClicked = { navController.navigateToProductsCategory(it) },
            )
        }
        composable(route = Cart.Route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -it }) + fadeIn(
                    animationSpec = tween(2000)
                )
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(
                    animationSpec = tween(2000)
                )
            }
        ) {
            Cart.Screen(
                setTitle = { currentScreenTitle.value = it },
                cartAction = action,
                onProductClicked = { navController.navigateToProduct(it) }

            )
        }
        ordersNavigationEntry(
            setTitle = { currentScreenTitle.value = it },
            onOrderClicked = { navController.navigateToProduct(it) }
        )
        productsCategoryNavigationEntry(
            setTitle = { currentScreenTitle.value = it },
            onProductClicked = { navController.navigateToProduct(it) }
        )

        productNavigationEntry(
            setTitle = { currentScreenTitle.value = it }
        )

        loginNavigationEntry(
            setTitle = { currentScreenTitle.value = it },
            onLogged = { navController.navigate(Home.Route) }
        )

        accountNavigationEntry(
            setTitle = { currentScreenTitle.value = it },
            onSetInformationsClick = { navController.navigateToSetInformations() },
            onSetPasswordClick = { navController.navigateToSetPassowrd() }
        )
        setInformationsNavigationEntry(
            setTitle = { currentScreenTitle.value = it },
            viewModel = accountViewModel
        )
        setPasswordNavigationEntry(
            setTitle = { currentScreenTitle.value = it },
            viewModel = accountViewModel
        )

    }
}
