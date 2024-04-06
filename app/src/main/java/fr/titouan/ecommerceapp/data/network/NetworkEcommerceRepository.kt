package fr.titouan.ecommerceapp.data.network

import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.model.AuthToken
import fr.titouan.ecommerceapp.model.Category
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Credentials
import fr.titouan.ecommerceapp.model.GoogleToken
import fr.titouan.ecommerceapp.model.Order
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductsCategory
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.network.EcommerceApiService
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String?) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
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

//    Orders
    override suspend fun getOrders(): List<Order> = ecommerceApiService.getOrders()

//    Users
    override suspend fun getUser(idUser: Int): NetworkResult<User> {
        return try {
            val response = ecommerceApiService.getUser(idUser)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    NetworkResult.Success(user)
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

//    Access with Token
//    override suspend fun login(email: String, password: String): NetworkResult<AuthToken?> {
//        return try {
//            val credentials = Credentials(email, password)
//            val response = ecommerceApiService.authenticateUser(credentials)
//
//            if (response.isSuccessful) {
//                val authToken = response.body()
//                if (authToken is AuthToken) {
//                    NetworkResult.Success(authToken)
//                } else {
//                    NetworkResult.Error("Erreur de conversion.")
//                }
//            } else {
//                NetworkResult.Error("Réponse de l'API non réussie: ${response.code()}")
//            }
//        } catch (e: Exception) {
//            NetworkResult.Error("Impossible de récupérer le produit à partir de l'API. ${e.message}")
//        }
//    }

//    Access with id
    override suspend fun loginWithId(email: String, password: String): NetworkResult<User> {
        return try {
            val credentials = Credentials(email, password)
            val response = ecommerceApiService.authenticateUserWithId(credentials)

            if (response.isSuccessful) {
                val user = response.body()
                if (user is User) {
                    NetworkResult.Success(user)
                } else {
                    NetworkResult.Error("Erreur de conversion.")
                }
            } else {
                NetworkResult.Error("Réponse de l'API non réussie: ${response.code()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Impossible de récupérer le produit à partir de l'API. ${e.message}")
        }
    }

//  Access with id
    override suspend fun authenticateWithGoogleWithId(googleToken: String): NetworkResult<User> {
        return try {
            val googleTokenRequest = GoogleToken(googleToken)
            val response = ecommerceApiService.authenticateWithGoogleWithId(googleTokenRequest)
            if (response.isSuccessful) {
                val user = response.body()
                if (user is User) {
                    NetworkResult.Success(user)
                } else {
                    NetworkResult.Error("Erreur de conversion en user.")
                }
            } else {
                NetworkResult.Error("Réponse de l'API non réussie: ${response.code()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Impossible de récupérer le produit à partir de l'API. ${e.message}")
        }
    }
//    Access with Token
//    override suspend fun authenticateWithGoogle(googleToken: String): NetworkResult<AuthToken?> {
//        return try {
//            val googleTokenRequest = GoogleToken(googleToken)
//            val response = ecommerceApiService.authenticateWithGoogle(googleTokenRequest)
//            if (response.isSuccessful) {
//                val authToken = response.body()
//                if (authToken is AuthToken) {
//                    NetworkResult.Success(authToken)
//                } else {
//                    NetworkResult.Error("Erreur de conversion.")
//                }
//            } else {
//                NetworkResult.Error("Réponse de l'API non réussie: ${response.code()}")
//            }
//        } catch (e: Exception) {
//            NetworkResult.Error("Impossible de récupérer le produit à partir de l'API. ${e.message}")
//        }
//    }
}