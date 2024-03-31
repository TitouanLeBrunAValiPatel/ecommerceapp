package fr.titouan.ecommerceapp.model

import android.util.Log
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Color(
    val id : Int,
    val name : String?,
    @SerialName("codeColor") val codeColor : String? = null
) {

    val safeName: String
        get() = name ?: "No name"
    val safeCodeColor: String
        get() = codeColor ?: "#FF0000"


    fun getLongCodeColor(): Long {
        val codeColor = safeCodeColor

        // Supprimer "#" si présent
        val cleanedCodeColor = codeColor.removePrefix("#")

        // Ajouter "0xFF" devant le code de couleur
        val fullCodeColor = "0xFF$cleanedCodeColor"

        // Convertir le code de couleur en Long
        return try {
            Log.e("ColorConversion", "Long: $fullCodeColor")
            "FF0DD000".toLongOrNull() ?: 0xFFFF0000

        } catch (e: NumberFormatException) {
            Log.e("ColorConversion", "Error converting color to Long: $fullCodeColor")
            0xFFFF0
        }
    }
}
