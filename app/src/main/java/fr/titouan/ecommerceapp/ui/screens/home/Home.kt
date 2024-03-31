package fr.titouan.ecommerceapp.ui.screens.home

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
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.ui.components.ErrorScreen
import fr.titouan.ecommerceapp.ui.components.ProductsGridScreen
import fr.titouan.ecommerceapp.ui.screens.home.views.BestSellersUiState
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.screens.home.views.HomeViewModel

object Home {
    private const val RouteBase = "Start"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    @Composable
    fun Screen(
        setTitle: (String) -> Unit,
        onProductClicked: (Int) -> Unit,
    ) {
        val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
        val bestSellersUiState = homeViewModel.mBestSellersUiState
        Title.value = stringResource(id = R.string.home_title)
        setTitle(Title.value)

        LaunchedEffect(null) {
            homeViewModel.getBestSellers()
        }

        when (bestSellersUiState) {

            is BestSellersUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is BestSellersUiState.Success -> {
                ProductsGridScreen(
                    onProductClicked = onProductClicked,
                    products = bestSellersUiState.products
                )
            }

            is BestSellersUiState.Error -> {
                Log.d(Debug.TAG, bestSellersUiState.message)
                ErrorScreen(
                    retryAction = { homeViewModel.getSafeBestSellers() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}