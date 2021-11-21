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

    /***
     * REQUEST METHOD
    Every method must have an HTTP annotation that provides the request method and relative URL.
    There are eight built-in annotations: HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD.
    The relative URL of the resource is specified in the annotation.
     * */


    /** Authorization
     * As a security measure,
     * most API access points require users to provide an authentication
     * token that can be used to verify the identity of the user making the request so as to grant
     * them access to data/ resources from the backend. The client app usually fetches the token upon successful login
     * or registration then saves the token locally and appends it to subsequent requests
     * so that the server can authenticate the user.
     * */


    @GET("/Common/Product/GetProductsForSell?page=0")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ) : Response<ProductModel>
    // --------------------------------------------------------------

    @GET("/Ecommerce/User/GetFavorites")
    suspend fun getFavoriteProducts(
        @Header("Authorization") token: String
    ): Response<List<Product>>


    /***
     * Query parameters are added with the @Query annotation on a method parameter.
     * They are automatically added at the end of the URL.
     * */
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


    /***
     * the @POST annotation, Indicates that we want to execute a POST request when this method is called.
     * The argument value for the @POST annotation is the endpoint—which is /Common/Identity/Register.
     * So the full URL will be http://18.196.156.64/Common/Identity/Register.
     * */

    /***
     * We can also use the @Body annotation on a service method parameter instead of specifying a form-style
     * request body with a number of individual fields.
     * The object will be serialized using the Retrofit instance Converter specified during creation.
     * This is only used when performing either a POST or PUT operation.
     * */

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


    /**
     *  We use the MultipartBody.Part class that allows us to send the actual
     *  file name besides the binary file data with the request.
     *  You’ll see how to create the file object correctly within repository file
     * **/
    @Multipart
    @POST("/Common/Identity/UploadImage")
    suspend fun uploadUserImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>


}