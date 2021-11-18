package sa.edu.twuaiq.e_commerce.view.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.FragmentProductsBinding
import sa.edu.twuaiq.e_commerce.model.product.Product
import sa.edu.twuaiq.e_commerce.repositories.SHARED_PREF_FILE
import sa.edu.twuaiq.e_commerce.repositories.TOKEN_KEY
import sa.edu.twuaiq.e_commerce.view.adapters.ProductsRecyclerViewAdapter


class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding

    private var allProducts = listOf<Product>()

    private lateinit var productAdapter: ProductsRecyclerViewAdapter
    private val productsViewModel: ProductsViewModel by activityViewModels()

    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    private lateinit var logoutItem: MenuItem
    private lateinit var profileItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE,Context.MODE_PRIVATE)
        sharedPrefEditor = sharedPref.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductsRecyclerViewAdapter(productsViewModel)
        binding.productsRecyclerview.adapter = productAdapter

        observers()
        productsViewModel.callProducts()
    }

    fun observers() {
        productsViewModel.productsLiveDate.observe(viewLifecycleOwner , {
        binding.productsProgressBar.animate().alpha(0f).setDuration(1000)
            productAdapter.submitList(it)
            allProducts = it
        })

        productsViewModel.productsErrorLiveData.observe(viewLifecycleOwner, { error ->
            error?.let {
                Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()

                if(error == "Unauthorized")
                    findNavController().navigate(R.id.action_productsFragment_to_loginFragment)
                productsViewModel.productsErrorLiveData.postValue(null)
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_item -> {
                sharedPrefEditor.putString(TOKEN_KEY,"")
                sharedPrefEditor.commit()

                logoutItem.isVisible = false
                profileItem.isVisible = false

                productsViewModel.callProducts()
            }
            R.id.profile_item -> {
                findNavController().navigate(R.id.action_productsFragment_to_profileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu,menu)

        val searchItem = menu.findItem(R.id.app_bar_search)
        logoutItem = menu.findItem(R.id.logout_item)
        profileItem = menu.findItem(R.id.profile_item)

        val searchView = searchItem.actionView as SearchView

        val token = sharedPref.getString(TOKEN_KEY,"")

        if (token!!.isEmpty()){
            logoutItem.isVisible = false
            profileItem.isVisible = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                productAdapter.submitList(
                    allProducts.filter {
                        it.description.lowercase().contains(query!!.lowercase())
                    }
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                productAdapter.submitList(
//                    allProducts.filter {
//                        it.description.lowercase().contains(newText!!.lowercase())
//                    }
//                )
                return true
            }

        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                productAdapter.submitList(allProducts)
                return true
            }

        })

    }
}