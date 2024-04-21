package fr.titouan.ecommerceapp.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.data.sharedPreferences.SessionManager
import fr.titouan.ecommerceapp.ui.screens.account.Account
import fr.titouan.ecommerceapp.ui.screens.login.Login
import fr.titouan.ecommerceapp.ui.screens.account.login.orders.Orders
import fr.titouan.ecommerceapp.ui.screens.account.views.AccountViewModel
import fr.titouan.ecommerceapp.ui.screens.account.views.ButtonWithArrow
import fr.titouan.ecommerceapp.ui.screens.payment.views.PaymentViewModel
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerEcommerceApp() {
    val navController = rememberNavController()
    var currentScreenTitle = rememberSaveable { mutableStateOf("Home") }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val action = remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    fun closeDrawer(){
        coroutineScope.launch {
            drawerState.close()

        }
    }

    val accountViewModel: AccountViewModel = viewModel(factory =  AccountViewModel.Factory)
    val paymentViewModel: PaymentViewModel = viewModel(factory = PaymentViewModel.Factory)

    val sessionManager = SessionManager(LocalContext.current)
    val sessionState = remember { mutableStateOf(sessionManager.isLoggedIn) }

    LaunchedEffect(sessionManager) {
        while (true) {

            val isLoggedIn = sessionManager.isLoggedIn // Obtenir l'état actuel de la session
            if (isLoggedIn != sessionState.value) {
                sessionState.value = isLoggedIn // Mettre à jour l'état de la session
            }
            delay(5000) // Attendre 5 secondes avant de vérifier à nouveau l'état de la session
        }
    }
    ModalNavigationDrawer(
        modifier = Modifier,
        gesturesEnabled = true,
        drawerContent = {

            DrawerContent(
                onButtonOrdersClick = {
                    closeDrawer()
                    navController.navigate(Orders.Route)
                                 },
                onButtonProfileClick = {
                    closeDrawer()

                    navController.navigate(Account.Route)
                                 },
                onButtonLoginClick = {
                    closeDrawer()

                    navController.navigate(Login.Route)
                },
                sessionState
            )
        },
        drawerState = drawerState,
        content = {
            ScaffoldEcommerceApp(
                currentScreenTitle = currentScreenTitle,
                scrollBehavior = scrollBehavior,
                navController =navController,
                action = action,
                coroutineScope = coroutineScope,
                drawerState = drawerState,
                accountViewModel,
                paymentViewModel = paymentViewModel
            )
        }
    )
}

@Composable
fun DrawerContent(
    onButtonOrdersClick: () -> Unit,
    onButtonProfileClick: () -> Unit,
    onButtonLoginClick: () -> Unit,
    sessionState: MutableState<Boolean>
) {
    var isLoggedText by remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .width(300.dp)
            .padding(16.dp)
            .safeDrawingPadding()
    ) {


        Row {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.loading_img), contentDescription = stringResource(
                id = R.string.logo_app))
            Text(text = stringResource(id = R.string.app_name))
        }
        if(!sessionState.value) {
            Log.d("COUCOU", sessionState.value.toString())
            Row {
                SessionManager(LocalContext.current).getUser()?.mail?.let { Text(text = it) }
            }

            isLoggedText = stringResource(id = R.string.login_button_signin)


        } else {

            Spacer(modifier = Modifier.height(40.dp))

            ButtonWithArrow(text = stringResource(id = R.string.nav_orders_title), onClick = onButtonOrdersClick)
            Spacer(modifier = Modifier.height(16.dp))
            ButtonWithArrow(text = stringResource(id = R.string.nav_profile_title), onClick = onButtonProfileClick)

            isLoggedText = stringResource(id = R.string.logout_button)

        }
        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                onButtonLoginClick() }
        ) {

            Text(text = isLoggedText)
        }
    }



}

//@Preview(showBackground = true)
//@Composable
//fun DrawerContentPreview(){
//    EcommerceappTheme {
//        DrawerContent(onButtonOrdersClick = { /*TODO*/ }, onButtonProfileClick = { /*TODO*/ }) {
//
//        }
//    }
//}
