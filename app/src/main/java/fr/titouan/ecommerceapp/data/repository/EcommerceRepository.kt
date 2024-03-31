package fr.titouan.ecommerceapp.data.repository

import fr.titouan.ecommerceapp.model.Category
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductsCategory
import fr.titouan.ecommerceapp.network.EcommerceApiService
import java.io.IOException

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String?) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}


interface EcommerceRepository {
//    Products
    suspend fun getProducts(): List<Product>
    suspend fun getProductsCategory(idCategory : Int): ProductsCategory
    suspend fun getBestSellers(): List<Product>
    suspend fun getProduct(idProduct: Int): NetworkResult<Product>

//    Categories
    suspend fun getCategories(): List<Category>

//    Colors
    suspend fun getColors() : List<Color>
    suspend fun getColor(idColor: Int) : Color
}
class NetworkEcommerceRepository(
    private val ecommerceApiService: EcommerceApiService
) : EcommerceRepository {
//    Products
    override suspend fun getProducts(): List<Product> = ecommerceApiService.getProducts()
    override suspend fun getProductsCategory(idCategory: Int): ProductsCategory = ecommerceApiService.getProductsCategory(idCategory)

    override suspend fun getBestSellers(): List<Product> = ecommerceApiService.getBestSellers()
    override suspend fun getProduct(idProduct: Int): NetworkResult<Product> {
        return try {
            val response = ecommerceApiService.getProduct(idProduct)
            if (response.isSuccessful) {
                val product = response.body()
                if (product != null) {
                    NetworkResult.Success(product)
                } else {
                    NetworkResult.Error("La réponse de l'API est vide.")
                }
            } else {
                NetworkResult.Error("Réponse de l'API non réussie: ${response.code()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Impossible de récupérer le produit à partir de l'API. ${e.message}")
        }
    }
//    Categories
    override suspend fun getCategories(): List<Category> = ecommerceApiService.getCategories()

//    Colors

    override suspend fun getColors(): List<Color> = ecommerceApiService.getColors()

    override suspend fun getColor(idColor: Int): Color = ecommerceApiService.getColor(idColor)
}