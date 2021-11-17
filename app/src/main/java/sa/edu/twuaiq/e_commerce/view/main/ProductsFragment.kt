package sa.edu.twuaiq.e_commerce.view.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.FragmentProductsBinding
import sa.edu.twuaiq.e_commerce.view.adapters.ProductsRecyclerViewAdapter


class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding

    private lateinit var productAdapter: ProductsRecyclerViewAdapter
    private val productsViewModel: ProductsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

        productAdapter = ProductsRecyclerViewAdapter()
        binding.productsRecyclerview.adapter = productAdapter

        observers()
        productsViewModel.callProducts()
    }

    fun observers() {
        productsViewModel.productsLiveDate.observe(viewLifecycleOwner , {
        binding.productsProgressBar.animate().alpha(0f)
            productAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu,menu)
    }
}