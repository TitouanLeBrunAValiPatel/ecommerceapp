package fr.titouan.ecommerceapp.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldEcommerceApp(
    currentScreenTitle: MutableState<String>,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    action: MutableState<Boolean>,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
){
    Scaffold(
        topBar = {
            TopAppBar(
                currentScreen = currentScreenTitle.value,
                scrollBehavior = scrollBehavior,
                navigateBack = { navController.popBackStack() },
                actionText = action
            )
        },
        bottomBar = {
            BottomBar(
                onAccountIconClick = {
                    coroutineScope.launch {
                        drawerState.open()}
                },

                navController = navController
            )
        }
    ) { innerPadding ->
        NavHostEcommerApp(
            innerPadding,
            navController,
            currentScreenTitle,
            action
        )
    }
}