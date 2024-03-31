package fr.titouan.ecommerceapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.ui.commonsUi.ProductCard
import fr.titouan.ecommerceapp.ui.screens.category.Category
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme

@Composable
fun BottomBar(
    navController: NavController
) {
    BottomAppBar(
        actions = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                IconButton(onClick = { navController.navigate(Home.Route) }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_menu_home),
                        contentDescription = stringResource(id = R.string.nav_home)
                    )
                }
                IconButton(onClick = { navController.navigate(Category.Route) }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_menu_category),
                        contentDescription = stringResource(id = R.string.nav_categories)
                    )
                }
                IconButton(onClick = { navController.navigate(Home.Route) }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_favorite_product),
                        contentDescription = stringResource(id = R.string.nav_favorite_product)
                    )
                }
                IconButton(onClick = { navController.navigate(Home.Route) }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_menu_cart),
                        contentDescription = stringResource(id = R.string.nav_cart)
                    )
                }
                IconButton(onClick = { navController.navigate(Home.Route) }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_menu_account),
                        contentDescription = stringResource(id = R.string.nav_account)
                    )
                }
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    EcommerceappTheme {
        BottomBar(navController = NavController(LocalContext.current))
    }
}