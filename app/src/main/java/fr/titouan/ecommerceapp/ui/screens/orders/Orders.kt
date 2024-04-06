package fr.titouan.ecommerceapp.ui.screens.account.login.orders

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.components.ErrorScreen
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.views.OrdersRowScreen
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.views.OrdersUiState
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.views.OrdersViewModel
import fr.titouan.ecommerceapp.ui.screens.product.Product.navigateToProduct

object Orders {
    private const val RouteBase = "Orders"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.ordersNavigationEntry (
        setTitle: (String) -> Unit,
        onOrderClicked: (Int) -> Unit
    ) {
        composable(route = Route) {
            Screen(
                setTitle = setTitle,
                onOrderClicked = onOrderClicked,
            )
        }

    }
    @Composable
    fun Screen(
        setTitle: (String) -> Unit,
        onOrderClicked: (Int) -> Unit,
    ) {
        val ordersViewModel : OrdersViewModel = viewModel(factory = OrdersViewModel.Factory)
        val ordersUiState = ordersViewModel.ordersUiState
        Title.value = stringResource(id = R.string.orders_title)
        setTitle(Title.value)

        LaunchedEffect(null) {
            ordersViewModel.getOrders()
        }

        when (ordersUiState) {

            is OrdersUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is OrdersUiState.Success -> {
                OrdersRowScreen(
                    onOrderClicked = onOrderClicked,
                    orders = ordersUiState.orders
                )
            }

            is OrdersUiState.Error -> {
                Log.d(Debug.TAG, ordersUiState.message)
                ErrorScreen(
                    retryAction = { ordersViewModel.getSafeOrders() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}