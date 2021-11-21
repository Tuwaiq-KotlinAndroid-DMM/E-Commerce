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


/**
 * A Repository is a class that abstracts access to multiple data sources (Room db, Network).
 * It is a suggested best practice for code separation and architecture. A Repository class handles data operations
 * */

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
    /**
     * You can then use the create method from the retrofitService to get an instance of the api.
     * */
    private val retrofitApi = retrofitService.create(CommerceApi::class.java)


    /*
    * what can SharedPreferences do?
    * SharedPreferences offers a framework to save persistent data in key-value pairs.
    * It works for any primitive data type (meaning booleans, ints, longs, floats, and strings).
    * Which also means that it’s pretty simple to work with.
    * */

    /**
     * Calling getSharedPreferences() enables you to retrieve a file by name,
     * which means you can have multiple files.
     * */
    private val sharedPref = context.getSharedPreferences(SHARED_PREF_FILE , Context.MODE_PRIVATE)




    /*
    * Read from SharedPreferences
    * Reading a value is straightforward. You just need to call the relevant get method (again).
    * So, calling getString() will return the String value associated with that key.
    * */
   // private val accessToken = sharedPref.getString(TOKEN_KEY,"")


    // In turn, you can use the methods from the API instance to interact with the remote datasource:

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

    suspend fun getFavoriteProducts() =
        retrofitApi.getFavoriteProducts("Bearer ${sharedPref.getString(TOKEN_KEY,"")}")



    suspend fun uploadUserImage(file: File) : Response<ResponseBody> {

        // Create RequestBody instance from file with content type: Image/* or you can change it with any type you want from this link below
        // https://www.lifewire.com/file-extensions-and-mime-types-3469109
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        // MultipartBody.Part is used to send also the actual file name
        val body = MultipartBody.Part.createFormData("imageFile",file.name,requestFile)

        // finally, execute the request
        return retrofitApi.uploadUserImage("Bearer ${sharedPref.getString(TOKEN_KEY,"")}", body)
    }

    /***
     * this companion object for restricts the instantiation of a class to one "single" instance.
     * This is useful when exactly one object is needed to coordinate actions across the system.
     * */

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