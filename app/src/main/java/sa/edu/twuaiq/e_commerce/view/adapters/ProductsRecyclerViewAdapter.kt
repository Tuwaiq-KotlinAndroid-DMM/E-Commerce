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

class ProductsRecyclerViewAdapter(val viewModel: ProductsViewModel) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductsViewHolder>() {


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return  oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)


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