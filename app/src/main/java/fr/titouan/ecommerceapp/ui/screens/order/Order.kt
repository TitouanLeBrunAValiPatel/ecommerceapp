package fr.titouan.ecommerceapp.ui.screens.order

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.components.ErrorScreen
import fr.titouan.ecommerceapp.ui.screens.order.views.OrderUiState
import fr.titouan.ecommerceapp.ui.screens.order.views.OrderViewModel
import fr.titouan.ecommerceapp.ui.screens.order.views.OrderDetail

object Order {
    private const val RouteBase = "Orders"
    private const val OrderIdArgument = "OrderId"
    const val Route = "$RouteBase/{$OrderIdArgument}"
    var Title: MutableState<String> = mutableStateOf("")


    fun NavGraphBuilder.orderNavigationEntry(
        setTitle: (String) -> Unit,
    ) {
        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) },
            arguments = listOf(
                navArgument(name = OrderIdArgument) {
                    type = NavType.IntType
                }
            )
        ) {
            val orderId = it.arguments?.getInt(OrderIdArgument) ?: 1
            Screen(
                orderId = orderId,
                setTitle = setTitle,
            )
        }
    }
    fun NavHostController.navigateToOrder(
        orderId: Int,
    ) {
        navigate("$RouteBase/$orderId")
    }

    @Composable
    fun Screen(
        orderId : Int,
        setTitle: (String) -> Unit
    ) {
        val orderViewModel: OrderViewModel = viewModel(factory = OrderViewModel.Factory)
        val orderUiState = orderViewModel.mOrderUiState

        LaunchedEffect(null) {
            orderViewModel.getOrder(orderId)
        }

        when (orderUiState) {

            is OrderUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is OrderUiState.Success -> {
                Title.value = stringResource(id = R.string.product_title, orderUiState.order.idOrder)
                setTitle(Title.value)
                OrderDetail(
                    order = orderUiState.order
                )
            }

            is OrderUiState.Error -> {
                val defaultTitlePage = stringResource(id = R.string.order_title_default)
                Title.value = stringResource(id = R.string.product_title, defaultTitlePage)
                setTitle(Title.value)
                Log.d(Debug.TAG, orderUiState.message)
                ErrorScreen(
                    retryAction = { orderViewModel.getSafeOrder(orderId) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}