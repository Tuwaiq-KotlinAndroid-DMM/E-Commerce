package sa.edu.twuaiq.e_commerce.view.identity

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


class ProfileFragment : Fragment() {

    private val IMAGE_PICKER = 0
    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    fun showImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(),false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true,"sa.edu.twuaiq.e_commerce"))
            .forResult(IMAGE_PICKER)
    }

    fun observers() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner, {
            binding.profileProgressBar.animate().alpha(0f)

            binding.emailTextview.text = it.email
            binding.fullnameTextview.text = it.fullName

            Picasso.get().load("http://18.196.156.64/Images/${it.image}").into(binding.profileImageview)
        })

        profileViewModel.profileErrorsLiveData.observe(viewLifecycleOwner , {
            binding.profileProgressBar.animate().alpha(0f)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
    }

}