package fr.titouan.ecommerceapp.ui.screens.login.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LogoutCard(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Êtes-vous sûr de vouloir vous déconnecter ?")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onLogoutClick) {
                Text("Se déconnecter")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogoutCard() {
    LogoutCard(onLogoutClick = {})
}
