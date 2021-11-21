package sa.edu.twuaiq.e_commerce.repositories

import android.content.Context
import androidx.room.Room
import sa.edu.twuaiq.e_commerce.database.ProductsDatabase
import sa.edu.twuaiq.e_commerce.model.product.Product

/**
 * A Repository is a class that abstracts access to multiple data sources (Room db, Network).
 * It is a suggested best practice for code separation and architecture. A Repository class handles data operations
 * */

private const val DATABASE_NAME = "products-database"
class RoomServiceRepository(context: Context) {


    /**
     * After you have defined the data entity,
     * the DAO, and the database object,
     * you can use the following code to create an instance of the database:
     * */

    private val database = Room.databaseBuilder(
        context,
        ProductsDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    /**
     * You can then use the abstract methods from the InventoryDatabase to get an instance of the DAO.
     * */

    private val productsDao = database.productsDao()

    // In turn, you can use the methods from the DAO instance to interact with the database:

    suspend fun insertProducts(products: List<Product>) =
        productsDao.insertProducts(products)

    suspend fun getProducts() = productsDao.getProducts()

    /***
     * this companion object for restricts the instantiation of a class to one "single" instance.
     * This is useful when exactly one object is needed to coordinate actions across the system.
     * */
    companion object {
        private var instance: RoomServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = RoomServiceRepository(context)
        }

        fun get(): RoomServiceRepository {
            return instance ?: throw Exception("Room Service Repository must be initialized")
        }
    }
}