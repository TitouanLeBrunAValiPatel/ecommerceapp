package fr.titouan.ecommerceapp

import android.app.Application
import fr.titouan.ecommerceapp.data.AppContainer
import fr.titouan.ecommerceapp.data.DefaultAppContainer

class EcommerceApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}