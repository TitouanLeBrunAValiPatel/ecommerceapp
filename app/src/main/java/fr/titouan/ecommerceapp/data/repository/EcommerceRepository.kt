package fr.titouan.ecommerceapp.data.repository

import fr.titouan.ecommerceapp.data.network.NetworkResult
import fr.titouan.ecommerceapp.model.AuthToken
import fr.titouan.ecommerceapp.model.Category
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Order
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductsCategory
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.network.EcommerceApiService
import java.io.IOException




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

//    Orders
    suspend fun getOrders(): List<Order>

//    Users
    suspend fun getUser(idUser: Int) : NetworkResult<User>

//    Access with token
//    suspend fun login(email: String, password: String): NetworkResult<AuthToken?>

//    Access with id
    suspend fun loginWithId(email: String, password: String): NetworkResult<User>

//    Access with token
//    suspend fun authenticateWithGoogle(googleToken: String): NetworkResult<AuthToken?>

//    Access with id
    suspend fun authenticateWithGoogleWithId(googleToken: String): NetworkResult<User>
}
