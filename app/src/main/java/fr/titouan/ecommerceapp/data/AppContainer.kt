package fr.titouan.ecommerceapp.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import fr.titouan.ecommerceapp.data.network.NetworkEcommerceRepository
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.data.sharedPreferences.CartManager
import fr.titouan.ecommerceapp.data.sharedPreferences.SessionManager
import fr.titouan.ecommerceapp.network.EcommerceApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val ecommerceRepository: EcommerceRepository
    fun provideCartManager(context: Context): CartManager
    fun provideSessionManager(context: Context): SessionManager

}
class DefaultAppContainer() : AppContainer {
    private val baseUrl = "http://10.0.2.2:8080/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{
            ignoreUnknownKeys = true
        }.asConverterFactory("*/*".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: EcommerceApiService by lazy {
        retrofit.create(EcommerceApiService::class.java)
    }

    override val ecommerceRepository: EcommerceRepository by lazy {
        NetworkEcommerceRepository(retrofitService)
    }

    override fun provideCartManager(context: Context): CartManager {
        return CartManager(context)
    }

    override fun provideSessionManager(context: Context): SessionManager {
        return SessionManager(context)
    }
}