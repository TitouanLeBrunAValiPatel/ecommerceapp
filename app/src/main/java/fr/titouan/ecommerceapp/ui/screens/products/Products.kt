package fr.titouan.ecommerceapp.ui.screens.products

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
import fr.titouan.ecommerceapp.ui.components.ProductsGridScreen
import fr.titouan.ecommerceapp.ui.screens.products.views.ProductsUiState
import fr.titouan.ecommerceapp.ui.screens.products.views.ProductsViewModel

object Products {
    private const val RouteBase = "Products/Categories"
    private const val CategoryIdArgument = "categoryId"
    const val Route = "$RouteBase/{$CategoryIdArgument}"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.productsCategoryNavigationEntry(
        setTitle: (String) -> Unit,
        onProductClicked: (Int) -> Unit
    ) {
        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) },
            arguments = listOf(
                navArgument(name = CategoryIdArgument) {
                    type = NavType.IntType
                }
            )
        ) {
            val categoryId = it.arguments?.getInt(CategoryIdArgument) ?: 1
            Screen(
                categoryId = categoryId,
                setTitle = setTitle,
                onProductClicked = onProductClicked
            )
        }
    }
    fun NavHostController.navigateToProductsCategory(
        categoryId: Int,
    ) {
        navigate("$RouteBase/$categoryId")
    }

    @Composable
    fun Screen(
        categoryId : Int,
        setTitle: (String) -> Unit,
        onProductClicked: (Int) -> Unit
    ) {
        val productsViewModel: ProductsViewModel = viewModel(factory = ProductsViewModel.Factory)
        val productsUiState = productsViewModel.mProductsUiState

        LaunchedEffect(null) {
            productsViewModel.getProducts(categoryId)
        }

        when (productsUiState) {

            is ProductsUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is ProductsUiState.Success -> {
                Title.value = stringResource(id = R.string.category_title, "Type")
                setTitle(Title.value)
                ProductsGridScreen(
                    onProductClicked = onProductClicked,
                    products = productsUiState.productsCategory
                )
            }

            is ProductsUiState.Error -> {
                Log.d(Debug.TAG, productsUiState.message)
                ErrorScreen(
                    retryAction = { productsViewModel.getSafeProducts(categoryId) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}