package fr.titouan.ecommerceapp.ui.screens.account.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import fr.titouan.ecommerceapp.EcommerceApplication
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.data.sharedPreferences.SessionManager
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginFormState
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginUiState
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginViewModel

sealed interface AccountUiState {
    data class Success(val user: User) : AccountUiState
    data class Error(val message : String) : AccountUiState
    data object Loading : AccountUiState
    data object Nothing : AccountUiState
}
data class AccountFormState(
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

class AccountViewModel(
    private val ecommerceRepository: EcommerceRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    var accountUiState: AccountUiState by mutableStateOf(AccountUiState.Nothing)
        private set

    var accountFormState: AccountFormState by mutableStateOf(AccountFormState())
        private set

    fun setInformations(user: User) {

    }

    fun setPassword(newPassword : String) {

    }

    fun oldPasswordIsValid(oldPassword: String) : Boolean {
        return false
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                val sessionManager : SessionManager = application.container.provideSessionManager(application.applicationContext)
                AccountViewModel(ecommerceRepository = ecommerceRepository,
                    sessionManager = sessionManager)
            }
        }
    }
}