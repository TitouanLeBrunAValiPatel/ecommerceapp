package fr.titouan.ecommerceapp.ui.screens.home.views

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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface BestSellersUiState {
    data class Success(val products: List<Product>) : BestSellersUiState
    data class Error(val message : String) : BestSellersUiState
    data object Loading : BestSellersUiState
}

class HomeViewModel(
    private val ecommerceRepository: EcommerceRepository,
) : ViewModel() {
    var mBestSellersUiState: BestSellersUiState by mutableStateOf(BestSellersUiState.Loading)
        private set
    var mBestSellers: List<Product> by mutableStateOf(listOf())
        private set

    fun getBestSellers() {
        viewModelScope.launch {
            mBestSellersUiState = BestSellersUiState.Loading
            mBestSellersUiState = try {
                mBestSellers = ecommerceRepository.getBestSellers()

                BestSellersUiState.Success(
                    mBestSellers
                )
            } catch (e: IOException) {
                BestSellersUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                BestSellersUiState.Error(e.message.toString())
            }

        }
    }

    fun getSafeBestSellers(){
        mBestSellers = GetSampleData.getProducts(8)
        mBestSellersUiState = BestSellersUiState.Success(products = mBestSellers)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                HomeViewModel(ecommerceRepository = ecommerceRepository)
            }
        }
    }
}