package fr.titouan.ecommerceapp.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import fr.titouan.ecommerceapp.model.User

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    val isLoggedIn: Boolean
        get() = getUser() != null

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
    fun saveUser(user: User) {
        val gson = Gson()
        val userJson = gson.toJson(user)
        editor.putString(KEY_USER_DATA, userJson)
        editor.apply()
    }

    fun getUser(): User? {
        val gson = Gson()
        val userJson = sharedPreferences.getString(KEY_USER_DATA, null)
        return gson.fromJson(userJson, User::class.java)
    }
    companion object {
        private const val PREF_NAME = "UserSession"
        private const val KEY_USER_DATA = "user_data"

    }
}