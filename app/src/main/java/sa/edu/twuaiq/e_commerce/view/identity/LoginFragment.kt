package sa.edu.twuaiq.e_commerce.view.identity

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.FragmentLoginBinding
import sa.edu.twuaiq.e_commerce.repositories.SHARED_PREF_FILE
import sa.edu.twuaiq.e_commerce.repositories.TOKEN_KEY

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by activityViewModels()

    private lateinit var progressDialog: ProgressDialog

    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)

        sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        sharedPrefEditor = sharedPref.edit()

        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observers()

        binding.loginRegisterButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEdittext.text.toString()
            val password = binding.loginPasswordEdittext.text.toString()

            if (email.isNotBlank() && password.isNotBlank()){
                progressDialog.show()
                loginViewModel.login(email, password)
            } else
                Toast.makeText(requireActivity(), "Email and password must not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    fun observers() {
        loginViewModel.loginLiveData.observe(viewLifecycleOwner, {
            it?.let {
                sharedPrefEditor.putString(TOKEN_KEY,it.token)
                sharedPrefEditor.commit()

                progressDialog.dismiss()
                loginViewModel.loginLiveData.postValue(null)

                // If you want to back to the last fragment from where you come here just user the popBackStack method of NavController
                findNavController().popBackStack()
            }
        })

        loginViewModel.loginErrorLiveData.observe(viewLifecycleOwner, {
            progressDialog.dismiss()
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

    }
}