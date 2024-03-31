package fr.titouan.ecommerceapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fr.titouan.ecommerceapp.ui.screens.home.Home
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.screens.category.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    currentScreen: String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
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
        modifier = modifier
    )

}