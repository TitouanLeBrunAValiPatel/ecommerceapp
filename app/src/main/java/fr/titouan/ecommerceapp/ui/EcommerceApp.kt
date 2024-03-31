package fr.titouan.ecommerceapp.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.titouan.ecommerceapp.ui.components.BottomBar
import fr.titouan.ecommerceapp.ui.components.TopAppBar
import fr.titouan.ecommerceapp.ui.screens.category.Category
import fr.titouan.ecommerceapp.ui.screens.category.views.CategoryViewModel
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.ui.screens.home.views.HomeViewModel
import fr.titouan.ecommerceapp.ui.screens.product.Product.navigateToProduct
import fr.titouan.ecommerceapp.ui.screens.product.Product.productNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.products.Products.navigateToProductsCategory
import fr.titouan.ecommerceapp.ui.screens.products.Products.productsCategoryNavigationEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcommerceApp(){
    val navController = rememberNavController()

    var currentScreenTitle by rememberSaveable { mutableStateOf("Home") }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(
            currentScreen = currentScreenTitle,
            scrollBehavior = scrollBehavior,
            navigateBack = { navController.popBackStack() }
        ) },
        bottomBar = { BottomBar(
            navController = navController
        ) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home.Route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = Home.Route) {
                Home.Screen(
                    setTitle = { currentScreenTitle = it },
                    onProductClicked = { navController.navigateToProduct(it) },
                )
            }
            composable(route = Category.Route,
                enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) }
            ) {
                Category.Screen(
                    setTitle = { currentScreenTitle = it },
                    onCategoryClicked = { navController.navigateToProductsCategory(it) },
                )
            }

            productsCategoryNavigationEntry(
                setTitle = { currentScreenTitle = it },
                onProductClicked = { navController.navigateToProduct(it) }
            )

            productNavigationEntry(
                setTitle = { currentScreenTitle = it }
            )

        }
    }
}