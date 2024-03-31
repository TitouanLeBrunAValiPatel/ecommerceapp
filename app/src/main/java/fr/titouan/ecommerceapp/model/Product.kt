package fr.titouan.ecommerceapp.model

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
    val formattedPrice = NumberFormat.getCurrencyInstance().format(price)

}

@Serializable
data class ProductsCategory(
    val category : Category? = null,
    val products: List<Product>
) {
    val safeCategory: Category
        get() = category ?: GetSampleData.getCategory()
}