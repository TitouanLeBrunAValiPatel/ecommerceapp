package fr.titouan.ecommerceapp.ui.screens.product

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
import fr.titouan.ecommerceapp.ui.commonsUi.ErrorScreen
import fr.titouan.ecommerceapp.ui.screens.product.views.ProductUiState
import fr.titouan.ecommerceapp.ui.screens.product.views.ProductViewModel
import fr.titouan.ecommerceapp.ui.screens.products.Products
import fr.titouan.ecommerceapp.ui.screens.product.views.ProductDetail

object Product {
    private const val RouteBase = "Products"
    private const val ProductIdArgument = "productId"
    const val Route = "$RouteBase/{$ProductIdArgument}"
    var Title: MutableState<String> = mutableStateOf("")


    fun NavGraphBuilder.productNavigationEntry(
        setTitle: (String) -> Unit,
    ) {
        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) },
            arguments = listOf(
                navArgument(name = ProductIdArgument) {
                    type = NavType.IntType
                }
            )
        ) {
            val productId = it.arguments?.getInt(ProductIdArgument) ?: 1
            Screen(
                productId = productId,
                setTitle = setTitle,
            )
        }
    }
    fun NavHostController.navigateToProduct(
        productId: Int,
    ) {
        navigate("${RouteBase}/$productId")
    }

    @Composable
    fun Screen(
        productId : Int,
        setTitle: (String) -> Unit
    ) {
        val productViewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory)
        val productUiState = productViewModel.mProductUiState

        LaunchedEffect(null) {
            productViewModel.getProduct(productId)
        }

        when (productUiState) {

            is ProductUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is ProductUiState.Success -> {
                Title.value = stringResource(id = R.string.product_title, productUiState.product.name)
                setTitle(Title.value)
                ProductDetail(
                    product = productUiState.product
                )
            }

            is ProductUiState.Error -> {
                val defaultTitlePage = stringResource(id = R.string.product_title_default)
                Title.value = stringResource(id = R.string.product_title, defaultTitlePage)
                setTitle(Title.value)
                Log.d(Debug.TAG, productUiState.message)
                ErrorScreen(
                    retryAction = { productViewModel.getSafeProduct(productId) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}