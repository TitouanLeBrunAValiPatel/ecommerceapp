package fr.titouan.ecommerceapp.ui.screens.login.views

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.EcommerceApplication
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.data.network.NetworkResult
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.data.sharedPreferences.SessionManager
import fr.titouan.ecommerceapp.model.User
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID


sealed interface LoginUiState {
    data class Success(val user: User) : LoginUiState
    data class Error(val message : String) : LoginUiState
    data object Loading : LoginUiState
    data object Nothing : LoginUiState
}

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
class LoginViewModel(
    private val ecommerceRepository: EcommerceRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val MIN_LENGTH_PASSWORD = 5
    private val SERVER_CLIENT_ID_WEB = "1089102293303-15f5c614a6d4mce2g57deuglms1mlqmu.apps.googleusercontent.com"
    private val SERVER_CLIENT_ID_ANDROID ="1089102293303-e9cbe4f7r3phce04qe3cll50nufh15d8.apps.googleusercontent.com"
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Nothing)
        private set

    var loginFormState: LoginFormState by mutableStateOf(LoginFormState())
        private set
    var user: User? by mutableStateOf(null)
        private set

    var isLogged: Boolean by mutableStateOf(sessionManager.isLoggedIn)
// If you want to change for connexion with token you juste have to change the name of calls api
    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            val result = ecommerceRepository.loginWithId(email, password)
            loginUiState = when (result) {
                is NetworkResult.Success -> LoginUiState.Success(result.data)
                is NetworkResult.Error -> LoginUiState.Error(
                    result.message ?: "Une erreur inconnue est survenue."
                )
                NetworkResult.Loading -> LoginUiState.Loading
            }
        }
    }
    suspend fun loginWithGoogle(googleToken: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            val result = ecommerceRepository.authenticateWithGoogleWithId(googleToken)
            when (result) {
                is NetworkResult.Success -> {
                    loginUiState = LoginUiState.Success(result.data)
                    sessionManager.saveUser(GetSampleData.getUser(2))
                    isLogged()
                }
                is NetworkResult.Error -> {
                    loginUiState =
                        LoginUiState.Error(result.message ?: "Une erreur inconnue est survenue.")
                    sessionManager.saveUser(GetSampleData.getUser(2))
                    isLogged()
                }
                NetworkResult.Loading -> loginUiState = LoginUiState.Loading
            }
        }
    }


    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.length > MIN_LENGTH_PASSWORD
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            loginFormState = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            loginFormState = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            loginFormState = LoginFormState(isDataValid = true)
        }
    }




//    Google Oauth

    suspend fun logWithGoogle(context : Context) {
//        viewModelScope.launch {
            var isLogged = false
            val credentialManager = CredentialManager.create(context)

            val rawNonce = UUID.randomUUID().toString()
            val bytes = rawNonce.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(SERVER_CLIENT_ID_WEB)
                .setNonce(hashedNonce)
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                Log.d("TITOUAN", "You are log $request $context")

                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                Log.d("TITOUAN", "You are log ${request}")

                val credential = result.credential
                Log.d("TITOUAN", "You are log ${credential.data}")

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                Log.d("TITOUAN", "You are log ${googleIdTokenCredential.id}")

                val googleIdToken = googleIdTokenCredential.idToken
                Log.d("TITOUAN", "You are log ${googleIdTokenCredential.id}")

                loginWithGoogle(googleIdToken)
//            if(googleIdTokenCredential.id == "titouannn21@gmail.com"){
//                val userLogged = ecc.getUser(5)
//                _user.value = userLogged
//                isLogged = true
//                return isLogged
//            } else {
//                val userLogged = api.getUser(100)
//                _user.value = userLogged
//                return isLogged
//            }
                Toast.makeText(
                    context,
                    "You are log ${googleIdTokenCredential.id}",
                    Toast.LENGTH_LONG
                ).show()


                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.d("TITOUAN", "chihvs ${e.errorMessage}")
                Toast.makeText(context, "chihvs ${e.errorMessage}", Toast.LENGTH_LONG).show()

            }
//        }
//        }

    }

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
            }

            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
            }

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("TAG", "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("TAG", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("TAG", "Unexpected type of credential")
            }
        }
    }

    fun logout() {
        sessionManager.clearSession()
        isLogged = sessionManager.isLoggedIn
    }

    fun isLogged() {
        Log.d(Debug.SESSION, "${sessionManager.isLoggedIn}")
        isLogged = sessionManager.isLoggedIn
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                val sessionManager : SessionManager = application.container.provideSessionManager(application.applicationContext)
                LoginViewModel(ecommerceRepository = ecommerceRepository,
                    sessionManager = sessionManager)
            }
        }
    }
}