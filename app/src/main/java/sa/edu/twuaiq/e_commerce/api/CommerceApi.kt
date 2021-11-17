package sa.edu.twuaiq.e_commerce.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import sa.edu.twuaiq.e_commerce.model.indentity.LoginBody
import sa.edu.twuaiq.e_commerce.model.indentity.LoginModel
import sa.edu.twuaiq.e_commerce.model.indentity.RegisterBody
import sa.edu.twuaiq.e_commerce.model.indentity.UserInfoModel
import sa.edu.twuaiq.e_commerce.model.product.Product
import sa.edu.twuaiq.e_commerce.model.product.ProductModel

interface CommerceApi {

    @GET("/Common/Product/GetProductsForSell?page=0")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ) : Response<ProductModel>
    // --------------------------------------------------------------

    @GET("/Ecommerce/User/GetFavorites")
    suspend fun getFavoriteProducts(
        @Header("Authorization") token: String
    ): Response<List<Product>>

    @POST("/Ecommerce/User/AddProductToFavorites")
    suspend fun addFavoriteProduct(
        @Header("Authorization") token: String,
        @Query("productId") productId: Int
    ): Response<ResponseBody>

    @POST("/Ecommerce/User/RemoveProductFromFavorties")
    suspend fun removeFavoriteProduct(
        @Header("Authorization") token: String,
        @Query("productId") productId: Int
    ): Response<ResponseBody>

    // ----------------------------------------------------------------------

    @POST("/Common/Identity/Register")
    suspend fun userRegister(
        @Body registerBody: RegisterBody
    ): Response<ResponseBody>

    @POST("/Common/Identity/Login")
    suspend fun userLogin(
        @Body loginBody: LoginBody
    ): Response<LoginModel>

    @GET("/Common/Identity/GetUserData")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
        ): Response<UserInfoModel>

    @Multipart
    @POST("/Common/Identity/UploadImage")
    suspend fun uploadUserImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>


}