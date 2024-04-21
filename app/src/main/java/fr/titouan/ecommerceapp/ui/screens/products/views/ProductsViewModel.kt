package fr.titouan.ecommerceapp.ui.screens.products.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import fr.titouan.ecommerceapp.EcommerceApplication
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductsCategory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface ProductsUiState {
    data class Success(val productsCategory: List<Product>) : ProductsUiState
    data class Error(val message : String) : ProductsUiState
    data object Loading : ProductsUiState
}

class ProductsViewModel(
    private val ecommerceRepository: EcommerceRepository,
) : ViewModel() {

    var mProductsUiState: ProductsUiState by mutableStateOf(ProductsUiState.Loading)
        private set
    var mProducts: List<Product>? by mutableStateOf(null)
        private set

    fun getProducts(categoryId : Int) {
        viewModelScope.launch {
            mProductsUiState = ProductsUiState.Loading
            mProductsUiState = try {
                mProducts = ecommerceRepository.getProductsCategory(categoryId)

                ProductsUiState.Success(productsCategory = mProducts!!)
            } catch (e: IOException) {
                ProductsUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                ProductsUiState.Error(e.message.toString())
            }
        }
    }


    fun getSafeProducts(categoryId: Int) {
        val mockProducts = GetSampleData.getProducts(
             4
        )
        mProducts = mockProducts
        mProductsUiState = ProductsUiState.Success(productsCategory = mProducts!!)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                ProductsViewModel(ecommerceRepository = ecommerceRepository)
            }
        }
    }
}