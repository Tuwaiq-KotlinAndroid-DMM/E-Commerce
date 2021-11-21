package sa.edu.twuaiq.e_commerce.model.product


import com.google.gson.annotations.SerializedName

/**
 * Serializers: A serializer allows to convert a Json string to corresponding Kotlin Model or objects.
 * */
data class ProductModel(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("pageElements")
    val products: List<Product>,
    @SerializedName("totalPages")
    val totalPages: Int
)