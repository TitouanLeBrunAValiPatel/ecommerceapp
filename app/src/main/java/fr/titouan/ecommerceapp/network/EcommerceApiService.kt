package fr.titouan.ecommerceapp.network

import fr.titouan.ecommerceapp.model.AuthToken
import fr.titouan.ecommerceapp.model.Category
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Credentials
import fr.titouan.ecommerceapp.model.GoogleToken
import fr.titouan.ecommerceapp.model.Order
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductsCategory
import fr.titouan.ecommerceapp.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EcommerceApiService {
//    Products
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/categories/{idCategory}")
    suspend fun getProductsCategory(@Path("idCategory") idCategory: Int): List<Product>

    @GET("products/best-sellers")
    suspend fun getBestSellers(): List<Product>

    @GET("products/{idProduct}")
    suspend fun getProduct(@Path("idProduct") idProduct: Int): Response<Product>

//    Categories
    @GET("categories")
    suspend fun getCategories(): List<Category>

//    Colors
    @GET("colors")
    suspend fun getColors() : List<Color>

    @GET("colors/{idColor}")
    suspend fun getColor(@Path("idColor") idColor : Int) : Color


//    Orders
    @GET("orders")
    suspend fun getOrders() : List<Order>

    @GET("orders/{idOrder}")
    suspend fun getOrder(@Path("idOrder") orderId: Int): Response<Order>


//    Users

    @GET("users")
    suspend fun getUsers() : List<User>

    @GET("users/{idUser}")
    suspend fun getUser(@Path("idUser") idUser: Int) : Response<User>

//    Access with Token
//    @POST("/auth/login")
//    suspend fun authenticateUser(@Body credentials: Credentials): Response<AuthToken>

//    Access with id
    @POST("/auth/login")
    suspend fun authenticateUserWithId(@Body credentials: Credentials): Response<User>

//    Access with id
    @POST("/auth/google")
    suspend fun authenticateWithGoogleWithId(@Body googleToken: GoogleToken): Response<User>

//    Access with Token
//    @POST("/auth/google")
//    suspend fun authenticateWithGoogle(@Body googleToken: GoogleToken): Response<AuthToken>

    @PATCH("/users/{idUser}/edit")
    suspend fun updateUserInformation(@Path("idUser") idUser: Int, @Body user: User): Response<User>
}