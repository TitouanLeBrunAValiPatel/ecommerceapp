package fr.titouan.ecommerceapp.ui.screens.payment

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.components.ErrorScreen
import fr.titouan.ecommerceapp.ui.screens.cart.Cart
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.ui.screens.payment.views.PaymentDeliveryDetail
import fr.titouan.ecommerceapp.ui.screens.payment.views.PaymentInformationDetail
import fr.titouan.ecommerceapp.ui.screens.payment.views.PaymentSuccessDetail
import fr.titouan.ecommerceapp.ui.screens.payment.views.PaymentUiState
import fr.titouan.ecommerceapp.ui.screens.payment.views.PaymentViewModel

enum class PaymentProcessID(val processId: Int) {
    CARD(1),
    DELIVERY(2),
    PAYMENT(3),
    SUCCESS(4)
}

object PaymentProcess {
    private const val RouteBase = "PaymentsProcess"
    private const val ProcessIdArgument = "ProcessId"
    const val Route = "$RouteBase/{$ProcessIdArgument}"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.paymentNavigationEntry(
        setTitle: (String) -> Unit,
        navController: NavHostController,
        paymentViewModel: PaymentViewModel
    ) {
        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) },
            arguments = listOf(
                navArgument(name = ProcessIdArgument) {
                    type = NavType.IntType
                }
            )
        ) {
            val processId = it.arguments?.getInt(ProcessIdArgument) ?: 1
            Screen(
                setTitle = setTitle,
                navController = navController,
                processId = processId,
                paymentViewModel = paymentViewModel

            )
        }
    }
    fun NavHostController.navigateToDeliveryInformation() {
        navigate("${RouteBase}/${PaymentProcessID.DELIVERY.processId}")
    }

    fun NavHostController.navigateToPaymentInformation() {
        navigate("${RouteBase}/${PaymentProcessID.PAYMENT.processId}")
    }

    fun NavHostController.navigateToPaymentSuccess() {
        navigate("${RouteBase}/${PaymentProcessID.SUCCESS.processId}")
    }


    @Composable
    fun Screen(
        setTitle: (String) -> Unit,
        navController: NavHostController,
        processId : Int,
        paymentViewModel: PaymentViewModel

    ) {
        val paymentUiStateUiState by rememberUpdatedState(newValue = paymentViewModel.paymentUiStateUiState)

        when (paymentUiStateUiState) {

            is PaymentUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is PaymentUiState.Success -> {
                val user = remember { mutableStateOf(paymentViewModel.user) }

                Log.d("TITOUAN", user.toString())
                when (processId) {
                    PaymentProcessID.CARD.processId -> {
                        navController.navigate(Cart.Route)
                    }
                    PaymentProcessID.DELIVERY.processId -> {
                        Title.value = stringResource(id = R.string.payment_title, stringResource(id = R.string.payment_title_success_delivery_information))
                        setTitle(Title.value)
                        PaymentDeliveryDetail(
                            user = user
                        ) {
                            navController.navigateToPaymentInformation()
                        }
                    }
                    PaymentProcessID.PAYMENT.processId -> {
                        Title.value = stringResource(id = R.string.payment_title, stringResource(id = R.string.payment_title_success_payment_information))
                        setTitle(Title.value)
                        PaymentInformationDetail(
                            user = user

                        ) { navController.navigateToPaymentSuccess() }
                    }
                    PaymentProcessID.SUCCESS.processId -> {
                        val title = stringResource(id = R.string.payment_title_success, (paymentUiStateUiState as PaymentUiState.Success).user.name.toString())
                        Title.value = stringResource(id = R.string.payment_title, title)
                        setTitle(Title.value)
                        PaymentSuccessDetail(
                            user = user
                        )
                    }
                    else -> {
                        navController.navigate(Home.Route)
                    }
                }

            }
            is PaymentUiState.Error -> {
                val defaultTitlePage = stringResource(id = R.string.payment_title_default)
                Title.value = stringResource(id = R.string.payment_title, defaultTitlePage)
                setTitle(Title.value)
                Log.d(Debug.TAG, (paymentUiStateUiState as PaymentUiState.Error).message)
                ErrorScreen(
                    retryAction = { paymentViewModel.getSafeInformationUser() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}