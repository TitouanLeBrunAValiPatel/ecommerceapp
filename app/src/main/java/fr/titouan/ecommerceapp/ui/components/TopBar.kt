package fr.titouan.ecommerceapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.screens.account.Account
import fr.titouan.ecommerceapp.ui.screens.cart.Cart
import fr.titouan.ecommerceapp.ui.screens.category.Category
import fr.titouan.ecommerceapp.ui.screens.login.Login
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    currentScreen: String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    actionText: MutableState<Boolean>

) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = currentScreen,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = {
            var isTopLevel = when (currentScreen) {
                Home.Title.value -> true
                Category.Title.value -> true
                Cart.Title.value -> true
                Account.Title.value -> true
                Login.Title.value -> true
                else -> false
            }
            if(!isTopLevel) {
                IconButton(onClick = navigateBack ) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.navbar_back_button)
                    )
                }
            }

        },
        actions = {
            var showTextAction = when(currentScreen) {
                Cart.Title.value -> true
                else -> false
            }

            if(showTextAction) {
                var text =
                    if(actionText.value)  stringResource(id = R.string.navbar_checked_action)
                    else  stringResource(id = R.string.navbar_edit_action)
                Text(text = text,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable {
                            actionText.value = !actionText.value

                        },
                    style = MaterialTheme.typography.headlineSmall,
                )

            }




        },
        modifier = modifier
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    EcommerceappTheme {
        val cartAction = remember { mutableStateOf(false) }

        TopAppBar(currentScreen = "Home.Title.value",
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            navigateBack = {  },
            actionText = cartAction
        )
    }
}