package sa.edu.twuaiq.e_commerce.repositories

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sa.edu.twuaiq.e_commerce.api.CommerceApi
import sa.edu.twuaiq.e_commerce.model.indentity.LoginBody
import sa.edu.twuaiq.e_commerce.model.indentity.RegisterBody
import java.io.File
import java.lang.Exception

const val SHARED_PREF_FILE = "Auth"
const val TOKEN_KEY = "token"
private const val BASE_URL = "http://18.196.156.64"
class ApiServiceRepository(val context: Context) {

    /***
     *
     * To work with Retrofit you basically need the following three classes:
    Model class which is used as a JSON model
    Interfaces that define the possible HTTP operations
    Retrofit.Builder class - Instance which uses the interface and the Builder API to allow defining the URL end point for the HTTP operations.
     * */

    // Retrofit.Builder
    // And we need to specify a factory for deserializing the response using the Gson library

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //  Builder API
    private val retrofitApi = retrofitService.create(CommerceApi::class.java)

    private val sharedPref = context.getSharedPreferences(SHARED_PREF_FILE , Context.MODE_PRIVATE)


   // private val accessToken = sharedPref.getString(TOKEN_KEY,"")

    suspend fun getProducts() =
        retrofitApi.getProducts("Bearer ${sharedPref.getString(TOKEN_KEY,"")}")

    suspend fun addFavoriteProduct(productId: Int) =
        retrofitApi.addFavoriteProduct("Bearer ${sharedPref.getString(TOKEN_KEY,"")}" , productId)

    suspend fun removeFavoriteProduct(productId: Int) =
        retrofitApi.removeFavoriteProduct("Bearer ${sharedPref.getString(TOKEN_KEY,"")}" , productId)

    suspend fun register(registerBody: RegisterBody) =
        retrofitApi.userRegister(registerBody)

    suspend fun login(loginBody: LoginBody) =
        retrofitApi.userLogin(loginBody)

    suspend fun getUserInfo() =
        retrofitApi.getUserInfo("Bearer ${sharedPref.getString(TOKEN_KEY,"")}")

    suspend fun uploadUserImage(file: File) : Response<ResponseBody> {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("imageFile",file.name,requestFile)

        return retrofitApi.uploadUserImage("Bearer ${sharedPref.getString(TOKEN_KEY,"")}", body)
    }

    companion object {
        private var instance: ApiServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiServiceRepository(context)
        }

        fun get() : ApiServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }
    }
}