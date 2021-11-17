package sa.edu.twuaiq.e_commerce.view.identity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.edu.twuaiq.e_commerce.model.indentity.RegisterBody
import sa.edu.twuaiq.e_commerce.repositories.ApiServiceRepository

private const val TAG = "RegisterViewModel"
class RegisterViewModel: ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val registerLiveData = MutableLiveData<String>()
    val registerErrorLiveData = MutableLiveData<String>()

    fun register(firstName: String, lastName: String, email: String, password:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.register(RegisterBody(email,firstName,lastName,password, 1))

                if (response.isSuccessful) {
                    Log.d(TAG,response.body().toString())
                    registerLiveData.postValue("Successful")
                } else {
                    Log.d(TAG,response.message())
                    registerErrorLiveData.postValue(response.message())
                }

            }catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                registerErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}