package fr.titouan.ecommerceapp.network

import fr.titouan.ecommerceapp.model.Category
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductsCategory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EcommerceApiService {
//    Products
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/categories/{idCategory}")
    suspend fun getProductsCategory(@Path("idCategory") idCategory: Int): ProductsCategory

    @GET("products/bestSellers")
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
}