package fr.titouan.ecommerceapp.ui.screens.cart.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import fr.titouan.ecommerceapp.EcommerceApplication
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.data.network.NetworkResult
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.data.sharedPreferences.CartManager
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductInCart
import fr.titouan.ecommerceapp.ui.screens.home.views.BestSellersUiState
import fr.titouan.ecommerceapp.ui.screens.home.views.HomeViewModel
import fr.titouan.ecommerceapp.ui.screens.product.views.ProductUiState
import fr.titouan.ecommerceapp.ui.screens.product.views.ProductViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CartUiState {
    data class Success(val productsInCart: List<ProductInCart>) : CartUiState
    data class Error(val message : String) : CartUiState
    data object Loading : CartUiState
}

class CartViewModel(
    private val ecommerceRepository: EcommerceRepository,
    private val cartManager: CartManager
) : ViewModel() {
    var totalPrice: Float by mutableFloatStateOf(0f)
        private set

    var mCartUiState: CartUiState by mutableStateOf(CartUiState.Loading)
        private set
    var mProductsInCart: List<ProductInCart> by mutableStateOf(listOf())
        private set

    var mProductUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    var mProduct: Product? by mutableStateOf(null)
        private set


    suspend fun getProductFromCart(productId: Int, colorId: Int, quantity: Int): ProductInCart? {
        var productInCart: ProductInCart? = null
        try {
            mCartUiState = CartUiState.Loading
            val colorResult = ecommerceRepository.getColor(colorId)
            val product = getProduct(productId) // Utilisation de la fonction getProduct que nous avons créée
            if(product is ProductUiState.Success) {
                productInCart = ProductInCart(
                    product = product.product,
                    color = colorResult,
                    quantity = quantity
                )
                Log.d("TITOUAN", "Suceess: $product")

            } else {
                mCartUiState = CartUiState.Error("$product")
                Log.d("TITOUAN", "erreur ou loading mais pas success dans  getproductfromcart: $product")

            }

        } catch (e: Exception) {
            mCartUiState = CartUiState.Error(e.message.toString())
            Log.e("TITOUAN", "Exception lors de la récupération du produit: ${e.message}")

        }
        return productInCart
    }

    private suspend fun getProduct(productId: Int): ProductUiState {
        mProductUiState = ProductUiState.Loading
        val result = ecommerceRepository.getProduct(productId)
         when (result) {
            is NetworkResult.Success -> {
                mProductUiState = ProductUiState.Success(result.data)
                Log.d("TITOUAN", "getproduct sucess ${result.data}")
            }
            is NetworkResult.Error -> {
                mProductUiState = ProductUiState.Error(result.message ?: "Une erreur inconnue est survenue.")
                Log.d("TITOUAN", "erreur ${result.message}")
            }
            NetworkResult.Loading -> {
                mProductUiState = ProductUiState.Loading
                Log.d("TITOUAN", "loading $result")
            }
        }
        return mProductUiState
    }


    fun getProductsInCart() {
        viewModelScope.launch {
            val productsFromCart = cartManager.getContentCart(cartViewModel = this@CartViewModel)
            if (productsFromCart != null) {
                mProductsInCart = productsFromCart
                mCartUiState = CartUiState.Success(productsInCart = mProductsInCart)
                updateTotal()
            } else {
                mCartUiState = CartUiState.Error(message = "Return null")

            }
        }

    }

    fun getSafeProductsInCart() {
        mProductsInCart = GetSampleData.getProductsInCart(7)
        mCartUiState = CartUiState.Success(productsInCart = mProductsInCart)
    }


    fun updateQuantity(productInCart: ProductInCart, newQuantity: Int) {
            val productToUpdate = mProductsInCart.find { it.product.id == productInCart.product.id }

            if (productToUpdate != null) {
                productToUpdate.quantity = newQuantity
                mProductsInCart = mProductsInCart
            }
            Log.d("UPDATEQTY", productInCart.product.id.toString() + productInCart.quantity.toString() + newQuantity.toString())
            updateTotal()
    }

    fun removeProductToCart(productInCart: ProductInCart) {
        Log.d("RemoveProduct", "${mProductsInCart.size}")

        val currentCart = mProductsInCart.orEmpty().toMutableList()

        val productToRemove = mProductsInCart.find { it.product.id == productInCart.product.id }
        if (productToRemove != null) {
            productToRemove?.let {
                currentCart.remove(it)
            }
        }
        mProductsInCart = currentCart
        Log.d("RemoveProduct", "${mProductsInCart.size}")
        updateTotal()
    }

    fun updateTotal() {
        Log.d("TIOUAN", "changed price $totalPrice")
        totalPrice = mProductsInCart.map { productInCart ->
            productInCart.product.price * productInCart.quantity
        }.sum()

        Log.d("TIOUAN", "changed price after $totalPrice")

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                val cartManager : CartManager = application.container.provideCartManager(application.applicationContext)
                CartViewModel(
                    ecommerceRepository = ecommerceRepository,
                    cartManager = cartManager)
            }
        }
    }
}