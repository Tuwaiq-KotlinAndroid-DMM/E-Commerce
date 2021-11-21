package sa.edu.twuaiq.e_commerce.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sa.edu.twuaiq.e_commerce.model.product.Product


/**
 * The following code defines a DAO called InventoryDao.
 * InventoryDao provides the methods that the rest of the app uses to interact with data in the ItemModel table.
 * */
@Dao
interface ProductsDao {

    // The @Insert annotation allows you to define methods that insert their parameters into the appropriate table in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    /***
     * The @Query annotation allows you to write SQL statements and expose them as DAO methods.
     * Use these query methods to query data from your app's database,
     * or when you need to perform more complex inserts, updates, and deletes.
     *
     * Room validates SQL queries at compile time. This means that if there's a problem with your query,
     * a compilation error occurs instead of a runtime failure.
     * */
    @Query("SELECT * FROM product")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM product WHERE isFavorite")
    suspend fun getFavoriteProducts() : List<Product>
}