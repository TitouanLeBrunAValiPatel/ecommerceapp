package fr.titouan.ecommerceapp.data

import fr.titouan.ecommerceapp.model.Category
import fr.titouan.ecommerceapp.model.Color
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.model.ProductInCart
import fr.titouan.ecommerceapp.model.ProductsCategory
import kotlin.random.Random

object GetSampleData {

    fun getProducts(
        length : Int
    ) : List<Product> {
        val products = List(length) {
            Product(id = it, name = "Name of the product", description = "Description $it", price = it.toFloat())
        }
        return products
    }

    fun getProduct(productId : Int): Product {
        return Product(
            id = productId,
            name = "Name of the product",
            description = "Description $productId",
            price = productId.toFloat()
        )
    }

    fun getProductsCategory(
        categoryId : Int,
        lengthProductList: Int
    ) : ProductsCategory {
        val products = List(lengthProductList) {
            Product(id = it, name = "Name of the product", description = "Description $it", price = it.toFloat())
        }
        val category = Category(id = categoryId, name = "Name of the category")

        return ProductsCategory(
            products = products,
            category = category
        )

    }

    fun getProductInCart() : ProductInCart {
        return ProductInCart(
            product = getProduct(2),
            color = getColor(2),
            quantity = Random.nextInt(1, 11)
        )
    }
    fun getProductsInCart(length: Int) : List<ProductInCart> {
        val productsInCart = List(length) {
            ProductInCart(
                product = getProduct(it),
                color = getColor(it),
                quantity = Random.nextInt(1, 11)
            )
        }
        return productsInCart
    }

    fun getCategories(length: Int) : List<Category> {
        val categories = List(length) {
            Category(id = it, name = "Category $it")
        }
        return categories
    }

    fun getCategory() : Category {
        return Category(
            id = 1,
            name = "Category 1"
        )
    }

    fun getColor(colorId: Int) : Color {
        return Color(
            id = colorId,
            name = "ma couleur $colorId",
            codeColor = "#OOOOOO"
        )
    }

    fun getColors(length: Int) : List<Color> {
        val colors = List(length) {
            Color(
                id = it,
                name = "ma couleur $it",
                codeColor = "FF00DE"
            )
        }
        return colors
    }


}