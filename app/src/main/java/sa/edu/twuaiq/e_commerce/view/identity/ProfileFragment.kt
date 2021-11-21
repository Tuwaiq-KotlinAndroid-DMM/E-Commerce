package sa.edu.twuaiq.e_commerce.view.identity

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.FragmentProductsBinding
import sa.edu.twuaiq.e_commerce.databinding.FragmentProfileBinding
import java.io.File


class ProfileFragment : Fragment() {

    private val IMAGE_PICKER = 0
    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by activityViewModels()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()
        profileViewModel.callUserProfile()

        binding.profileImageview.setOnClickListener {
            showImagePicker()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICKER && resultCode == RESULT_OK) {
            progressDialog.show()

            val imagePath = Matisse.obtainPathResult(data)[0]

            val imageFile = File(imagePath)

            profileViewModel.uploadUserImage(imageFile)
        }
    }

    fun showImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(),false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true,"sa.edu.twuaiq.e_commerce"))
            .forResult(IMAGE_PICKER)
    }

    fun observers() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner, {
            progressDialog.dismiss()
            binding.profileProgressBar.animate().alpha(0f)

            binding.emailTextview.text = it.email
            binding.fullnameTextview.text = it.fullName

            Picasso.get().load("http://18.196.156.64/Images/${it.image}").into(binding.profileImageview)
        })

        profileViewModel.uploadImageLiveData.observe(viewLifecycleOwner, {
            profileViewModel.callUserProfile()
        })

        profileViewModel.profileErrorsLiveData.observe(viewLifecycleOwner , {
            progressDialog.dismiss()
            binding.profileProgressBar.animate().alpha(0f)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
    }

}