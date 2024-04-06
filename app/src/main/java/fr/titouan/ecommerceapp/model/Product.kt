package fr.titouan.ecommerceapp.model

import android.util.Log
import androidx.compose.runtime.MutableState
import fr.titouan.ecommerceapp.Debug
import fr.titouan.ecommerceapp.data.GetSampleData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.NumberFormat

@Serializable
data class Product(
    val id : Int,
    @SerialName("name") val name : String,
    val description : String,
    val price : Float,
    @SerialName("image") val images : List<Image>? = null,
) {
    val safeName : String
        get() = name ?: "No name"
    val safeImages: List<Image>
        get() = images ?: listOf(
            Image(
                1,
                "https://www.atmosphera.com/fstrz/r/s/www.atmosphera.com/fr/phototheque/atmosphera.com/65000/medium/01W064824A.jpg?frz-v=1548",
                2
            ),
            Image(
                1,
                "https://www.atmosphera.com/fstrz/r/s/www.atmosphera.com/fr/phototheque/atmosphera.com/65000/medium/01W064824A.jpg?frz-v=1548",
                2
            ),
            Image(
                1,
                "https://www.atmosphera.com/fstrz/r/s/www.atmosphera.com/fr/phototheque/atmosphera.com/65000/medium/01W064824A.jpg?frz-v=1548",
                2
            )
        )
}

@Serializable
data class ProductsCategory(
    val category : Category? = null,
    val products: List<Product>
) {
    val safeCategory: Category
        get() = category ?: GetSampleData.getCategory()
}

@Serializable
data class ProductInCart(
    val product: Product,
    val color: Color,
    var quantity : Int
) {

}

@Serializable
data class ProductInOrder(
    val product : Product,
    val priceTotal : Float,
    val color : Color?,
    @SerialName("thumbnail") val image : String,
    var quantity : Int
){
    val safeDescription: String
        get() = product.description ?: "There is no description for this product"
    val safeColor: Color
        get() = color ?: Color(2, name = "Red", "#OOOOOO")
}