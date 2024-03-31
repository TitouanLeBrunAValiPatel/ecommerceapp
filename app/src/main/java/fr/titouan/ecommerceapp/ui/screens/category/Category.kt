package fr.titouan.ecommerceapp.ui.screens.category

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
import fr.titouan.ecommerceapp.ui.screens.category.views.CategoriesRowScreen
import fr.titouan.ecommerceapp.ui.screens.category.views.CategoriesUiState
import fr.titouan.ecommerceapp.ui.screens.category.views.CategoryViewModel

object Category {
    private const val RouteBase = "Categories"

    const val Route = "$RouteBase"

    var Title: MutableState<String> = mutableStateOf("")

    @Composable
    fun Screen(
        setTitle: (String) -> Unit,
        onCategoryClicked: (Int) -> Unit,
    ) {
        val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory)
        val categoriesUiState = categoryViewModel.mCategoriesUiState
        Title.value = stringResource(id = R.string.categories_title)
        setTitle(Title.value)

        LaunchedEffect(null){
            categoryViewModel.getCategories()
        }

        when (categoriesUiState) {

            is CategoriesUiState.Loading -> Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                )
            }

            is CategoriesUiState.Success -> {
                CategoriesRowScreen(
                    onCategoryClicked = onCategoryClicked,
                    categories = categoriesUiState.categories
                )
            }

            is CategoriesUiState.Error -> {
                Log.d(Debug.TAG, categoriesUiState.message)
                ErrorScreen(
                    retryAction = { categoryViewModel.getSafeCategories() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}