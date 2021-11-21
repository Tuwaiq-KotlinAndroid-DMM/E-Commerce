package sa.edu.twuaiq.e_commerce.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.FavoriteItemLayoutBinding
import sa.edu.twuaiq.e_commerce.model.product.Product

class FavoritesRecyclerViewAdapter:
    RecyclerView.Adapter<FavoritesRecyclerViewAdapter.FavoritesViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)

    fun submitList(list: List<Product>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesRecyclerViewAdapter.FavoritesViewHolder {

        val binding = FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

        val item = differ.currentList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class FavoritesViewHolder(val binding: FavoriteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.favoriteNameTextview.text = item.title
            binding.favoritePriceTextview.text = "${item.price} SAR"

            Picasso.get().load(item.imagePath).into(binding.favoriteImageview)
        }
    }

}