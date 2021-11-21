package sa.edu.twuaiq.e_commerce.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.edu.twuaiq.e_commerce.model.product.Product
import sa.edu.twuaiq.e_commerce.repositories.ApiServiceRepository
import sa.edu.twuaiq.e_commerce.repositories.RoomServiceRepository
import java.lang.Exception


/**
 * The ViewModel is a class whose role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 * You can also use a ViewModel to share data between fragments.
 *
 * A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration changes.
 * Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle:
 * Your activities and fragments are responsible for drawing data to the screen,
 * while your ViewModel is responsible for holding and processing all the data needed for the UI.
 * */
private const val TAG = "ProductsViewModel"
class ProductsViewModel : ViewModel() {

    // Getting instance from Api Service Repository with companion object function
    private val apiRepo = ApiServiceRepository.get()

    // Getting instance from Room Service Repository with companion object function
    private val databaseRepo = RoomServiceRepository.get()


    val productsLiveDate = MutableLiveData<List<Product>>()
    val productsErrorLiveData = MutableLiveData<String>()


    fun callProducts() {

        /***
         * Scope in Kotlin’s coroutines can be defined as the restrictions within which the Kotlin coroutines are being executed
         * Scopes help to predict the lifecycle of the coroutines. There are basically 4 scopes in Kotlin coroutines:
         * */

        /***
         * 1. Global Scope
        Global Scope is one of the ways by which coroutines are launched.
        When Coroutines are launched within the global scope, they live long as the application does.
        If the coroutines finish it’s a job, it will be destroyed and will not keep alive until the application dies
         * */

        /**
         * 2. LifeCycle Scope
        The lifecycle scope is the same as the global scope,
        but the only difference is that when we use the lifecycle scope,
        all the coroutines launched within the activity also dies when the activity dies.
         * */

        /***
         * 3. ViewModel Scope
        It is also the same as the lifecycle scope,
        only difference is that the coroutine in this scope will live as long the view model is alive.
         * */

        /***
         * 4. Coroutine Scope
         *  It creates a new scope and does not complete until all children’s coroutines complete.
         *  So we are creating a scope, we are running coroutines and inside the scope,
         *  we can create other coroutines.
         *  This coroutine that starts here does not complete until all the inner coroutines complete as well.
         * */

        viewModelScope.launch(Dispatchers.IO) {
            // Coroutines Dispatchers
            /**
             * Kotlin coroutines use dispatchers to determine which threads are used for coroutine execution.
             * To run code outside of the main thread, you can tell Kotlin coroutines to perform work on either the Default or IO dispatcher.
             * In Kotlin, all coroutines must run in a dispatcher, even when they're running on the main thread.
             * Coroutines can suspend themselves, and the dispatcher is responsible for resuming them.
             * */

            // To specify where the coroutines should run, Kotlin provides three dispatchers that you can use:

            /**
             * Dispatchers.Main -
             * Use this dispatcher to run a coroutine on the main Android thread.
             * This should be used only for interacting with the UI and performing quick work.
             * */

            /***
             * Dispatchers.IO -
             * This dispatcher is optimized to perform disk or network I/O outside of the main thread.
             * Examples include using the Room component, reading from or writing to files,
             * and running any network operations.
             * */

            /**
             * Dispatchers.Default -
             * This dispatcher is optimized to perform CPU-intensive work outside of the main thread.
             * */


            // Use try and catch for handling http exceptions
            try {

                // Calling the API Methods and handles the result
                val response = apiRepo.getProducts()

                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, this.toString())
                        // Send Response to view
                        productsLiveDate.postValue(products)

                        // Save response in local database
                        databaseRepo.insertProducts(products)
                    }
                } else {
                    Log.d(TAG,response.message())

                    // Send Error Response to view
                    productsErrorLiveData.postValue(response.message())

                    productsLiveDate.postValue(databaseRepo.getProducts())
                }

            } catch (e: Exception)
            {
                Log.d(TAG,e.message.toString())

                // Send Error Response to view
                productsErrorLiveData.postValue(e.message.toString())

                productsLiveDate.postValue(databaseRepo.getProducts())
            }
        }
    }

    fun addFavoriteProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.addFavoriteProduct(productId)

                if (!response.isSuccessful) {
                    Log.d(TAG,response.message())

                    productsErrorLiveData.postValue(response.message())
                }

            }catch (e:Exception) {
                Log.d(TAG, e.message.toString())
                productsErrorLiveData.postValue(e.message.toString())
            }
        }
    }

    fun removeFavoriteProduct(productId: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.removeFavoriteProduct(productId)

                if (!response.isSuccessful) {
                    Log.d(TAG, response.message())
                    productsErrorLiveData.postValue(response.message())
                }

            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                productsErrorLiveData.postValue(e.message.toString())
            }
        }

    }
}