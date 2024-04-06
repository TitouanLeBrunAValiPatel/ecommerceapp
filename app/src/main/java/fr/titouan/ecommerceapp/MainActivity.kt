package fr.titouan.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fr.titouan.ecommerceapp.ui.EcommerceApp
import fr.titouan.ecommerceapp.ui.theme.EcommerceappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            EcommerceappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    EcommerceApp()
                }
            }
        }
    }
}