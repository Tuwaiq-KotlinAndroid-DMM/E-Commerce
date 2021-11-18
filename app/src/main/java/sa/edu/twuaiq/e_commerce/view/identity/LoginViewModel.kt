package sa.edu.twuaiq.e_commerce.view.identity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.edu.twuaiq.e_commerce.model.indentity.LoginBody
import sa.edu.twuaiq.e_commerce.model.indentity.LoginModel
import sa.edu.twuaiq.e_commerce.repositories.ApiServiceRepository

private const val TAG = "LoginViewModel"
class LoginViewModel: ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val loginLiveData = MutableLiveData<LoginModel>()
    val loginErrorLiveData = MutableLiveData<String>()


    fun login(email: String,password: String) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = apiRepo.login(LoginBody(email,password,true))

                if (response.isSuccessful) {
                    Log.d(TAG,response.body().toString())
                    response.body()?.run {
                        loginLiveData.postValue(this)
                    }
                } else
                {
                    Log.d(TAG, response.message())
                    loginErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                loginErrorLiveData.postValue(e.message.toString())
            }

        }

    }

}