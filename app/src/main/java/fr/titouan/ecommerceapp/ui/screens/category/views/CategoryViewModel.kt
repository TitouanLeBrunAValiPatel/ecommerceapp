package fr.titouan.ecommerceapp.ui.screens.category.views

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
import fr.titouan.ecommerceapp.model.Category
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    data class Error(val message : String) : CategoriesUiState
    data object Loading : CategoriesUiState
}
class CategoryViewModel(
    private val ecommerceRepository: EcommerceRepository
) : ViewModel(){

    var mCategoriesUiState: CategoriesUiState by mutableStateOf(CategoriesUiState.Loading)
        private set
    var mCategories: List<Category> by mutableStateOf(listOf())
        private set

    fun getCategories() {
        viewModelScope.launch {
            mCategoriesUiState = CategoriesUiState.Loading
            mCategoriesUiState = try {
                mCategories = ecommerceRepository.getCategories()

                CategoriesUiState.Success(categories = mCategories)
            } catch (e: IOException) {
                CategoriesUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                CategoriesUiState.Error(e.message.toString())
            }

        }
    }

    fun getSafeCategories(){
        mCategories = GetSampleData.getCategories(8)
        mCategoriesUiState = CategoriesUiState.Success(categories = mCategories)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                CategoryViewModel(ecommerceRepository = ecommerceRepository)
            }
        }
    }
}