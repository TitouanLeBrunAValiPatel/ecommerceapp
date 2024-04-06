package fr.titouan.ecommerceapp.ui

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import fr.titouan.ecommerceapp.data.sharedPreferences.SessionManager
import fr.titouan.ecommerceapp.ui.components.BottomBar
import fr.titouan.ecommerceapp.ui.components.DrawerEcommerceApp
import fr.titouan.ecommerceapp.ui.components.NavHostEcommerApp
import fr.titouan.ecommerceapp.ui.components.ScaffoldEcommerceApp
import fr.titouan.ecommerceapp.ui.components.TopAppBar
import fr.titouan.ecommerceapp.ui.screens.account.Account.accountNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.cart.Cart
import fr.titouan.ecommerceapp.ui.screens.category.Category
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.ui.screens.login.Login
import fr.titouan.ecommerceapp.ui.screens.login.Login.loginNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.Orders
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.Orders.ordersNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.product.Product.navigateToProduct
import fr.titouan.ecommerceapp.ui.screens.product.Product.productNavigationEntry
import fr.titouan.ecommerceapp.ui.screens.products.Products.navigateToProductsCategory
import fr.titouan.ecommerceapp.ui.screens.products.Products.productsCategoryNavigationEntry
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcommerceApp() {

        DrawerEcommerceApp()
}