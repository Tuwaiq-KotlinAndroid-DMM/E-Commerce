package sa.edu.twuaiq.e_commerce.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.model.product.Product
import sa.edu.twuaiq.e_commerce.view.main.ProductsViewModel

/***
Once you've determined your layout, you need to implement your Adapter and ViewHolder.
These two classes work together to define how your data is displayed.
The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
The Adapter creates ViewHolder objects as needed, and also sets the data for those views.
The process of associating views to their data is called binding.
When you define your adapter, you need to override three key methods:
onCreateViewHolder()
onBindViewHolder()
getItemCount()
 **/
class ProductsRecyclerViewAdapter(val viewModel: ProductsViewModel) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductsViewHolder>() {


    /**
     * DiffUtil is a utility class that can calculate the difference between two lists and output a list of update operations that converts the first list into the second one.
     * It can be used to calculate updates for a RecyclerView Adapter.
    Most of the time our list changes completely and we set new list to RecyclerView Adapter.
    And we call notifyDataSetChanged to update adapter. NotifyDataSetChanged is costly.
    DiffUtil class solves that problem now. It does its job perfectly!
     * */

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return  oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)



    /**
     * onCreateViewHolder(): RecyclerView calls this method whenever it needs to create a new ViewHolder.
    The method creates and initializes the ViewHolder and its associated View,
    but does not fill in the view's contents—the ViewHolder has not yet been bound to specific data.
     */

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsRecyclerViewAdapter.ProductsViewHolder {

        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_item_layout,
                parent,
                false
            )
        )
    }

    /**
     * onBindViewHolder(): RecyclerView calls this method to associate a ViewHolder with data.
    The method fetches the appropriate data and uses the data to fill in the view holder's layout.
    For example, if the RecyclerView displays a list of names,
    the method might find the appropriate name in the list and fill in the view holder's TextView widget.
     */
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {

        val item = differ.currentList[position]

        holder.titleTextView.text = item.title
        holder.priceTextView.text = "${item.price} SAR"
        holder.favoriteToggleButton.isChecked = item.isFavorite

        Picasso.get().load(item.imagePath).into(holder.productImageView)

        holder.favoriteToggleButton.setOnClickListener {
            if (holder.favoriteToggleButton.isChecked) {
                viewModel.addFavoriteProduct(item.id)
            } else {
                viewModel.removeFavoriteProduct(item.id)
            }
        }
    }

    /**
     * getItemCount(): RecyclerView calls this method to get the size of the data set.
    For example, in an address book app, this might be the total number of addresses.
    RecyclerView uses this to determine when there are no more items that can be displayed.
     */

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Product>) {
        differ.submitList(list)
    }


    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.product_name_textview)
        val priceTextView: TextView = itemView.findViewById(R.id.product_price_textview)
        val productImageView: ImageView = itemView.findViewById(R.id.product_image)
        val favoriteToggleButton: ToggleButton = itemView.findViewById(R.id.favorite_toggle_button)

    }

}