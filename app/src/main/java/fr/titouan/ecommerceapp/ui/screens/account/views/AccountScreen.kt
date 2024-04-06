package fr.titouan.ecommerceapp.ui.screens.account.views

import android.icu.text.CaseMap.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.titouan.ecommerceapp.R

@Composable
fun AccountScreen(
    onSetPasswordClick: () -> Unit,
    onSetInformationsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .wrapContentWidth()
    ) {
        ButtonWithArrow(text = stringResource(id = R.string.nav_set_password), onClick = onSetPasswordClick)
        Spacer(modifier = Modifier.height(40.dp))

        ButtonWithArrow(text = stringResource(id = R.string.nav_set_informations), onClick = onSetInformationsClick)
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ButtonWithArrow(text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
            .border(width = 1.dp, color = Color.Black)
    ) {
        Text(text = text, modifier = Modifier.weight(1f).padding(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null
        )
    }
}
