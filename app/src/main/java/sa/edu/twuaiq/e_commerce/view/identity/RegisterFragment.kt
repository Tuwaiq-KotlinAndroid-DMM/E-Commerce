package sa.edu.twuaiq.e_commerce.view.identity

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.FragmentRegisterBinding
import sa.edu.twuaiq.e_commerce.util.RegisterValidations


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val registerViewModel: RegisterViewModel by activityViewModels()
    private val validator = RegisterValidations()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)

        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()

        binding.registerButton.setOnClickListener {

            val firstName = binding.firstNameEdittext.text.toString()
            val lastName = binding.lastNameEdittext.text.toString()
            val email = binding.emailEdittext.text.toString()
            val password = binding.passwordEdittext.text.toString()
            val confirmPassword = binding.confirmPasswordEdittext.text.toString()

            if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                if(password == confirmPassword) {
                   if (validator.emailIsValid(email)) {
                       if (validator.passwordIsValid(password)) {
                           progressDialog.show()
                           registerViewModel.register(firstName,lastName,email,password)
                       } else
                           Toast.makeText(requireActivity(), "Make sure your password is strong.", Toast.LENGTH_SHORT).show()
                   } else
                       Toast.makeText(requireActivity(), "Make sure you typed your email address correctly.", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(requireActivity(), "Password and confirm password don't match", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(requireActivity(), "Registration fields must not be empty", Toast.LENGTH_SHORT).show()

        }
    }

    fun observers() {
        registerViewModel.registerLiveData.observe(viewLifecycleOwner, {
            it?.let {
                progressDialog.dismiss()
                findNavController().popBackStack()
                registerViewModel.registerLiveData.postValue(null)
            }
        })

        registerViewModel.registerErrorLiveData.observe(viewLifecycleOwner, {
            progressDialog.dismiss()
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

    }

}