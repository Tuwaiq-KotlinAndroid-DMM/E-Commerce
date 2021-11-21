package sa.edu.twuaiq.e_commerce.view.main

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.FragmentFavoritesBinding
import sa.edu.twuaiq.e_commerce.view.adapters.FavoritesRecyclerViewAdapter

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private val favoriteViewModel: FavoritesViewModel by activityViewModels()
    private lateinit var favoritesRecyclerViewAdapter: FavoritesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()
        favoritesRecyclerViewAdapter = FavoritesRecyclerViewAdapter()
        binding.favoritesRecyclerview.adapter = favoritesRecyclerViewAdapter

        favoriteViewModel.callFavorites()

        binding.favoriteLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_loginFragment)
        }

    }

    fun observers() {
        favoriteViewModel.favoritesLiveData.observe(viewLifecycleOwner , {
            binding.favoriteProgressBar.animate().alpha(0f)
            favoritesRecyclerViewAdapter.submitList(it)
        })

        favoriteViewModel.favoritesErrorLiveData.observe(viewLifecycleOwner, {
            binding.favoriteProgressBar.animate().alpha(0f)
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                if (it == "Unauthorized"){
                    binding.favoriteLoginButton.visibility = View.VISIBLE
                    favoritesRecyclerViewAdapter.submitList(listOf())
                }
                favoriteViewModel.favoritesErrorLiveData.postValue(null)
            }
        })
    }

}