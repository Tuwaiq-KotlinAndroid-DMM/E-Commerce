package sa.edu.twuaiq.e_commerce.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sa.edu.twuaiq.e_commerce.model.product.Product

/**
 * Primary components
There are three major components in Room:
The database class that holds the database and serves as the main access point for the underlying connection to your app's persisted data.
Data entities that represent tables in your app's database.
Data access objects (DAOs) that provide methods that your app can use to query, update, insert, and delete data in the database.
 * */

/***
 * Database
The following code defines an InventoryDatabase class to hold the database. InventoryDatabase defines the database configuration and serves as the app's main access point to the persisted data. The database class must satisfy the following conditions:
The class must be annotated with a @Database annotation that includes an entities array that lists all of the data entities associated with the database.
The class must be an abstract class that extends RoomDatabase.
For each DAO class that is associated with the database, the database class must define an abstract method that has zero arguments and returns an instance of the DAO class.
 * */
@Database(entities = [Product::class], version = 1)
abstract class ProductsDatabase: RoomDatabase() {
    /***
     * The database class provides your app with instances of the DAOs associated with that database. In turn,
     * the app can use the DAOs to retrieve data from the database as instances of the associated data entity objects.
     * The app can also use the defined data entities to update rows from the corresponding tables,
     * or to create new rows for insertion.
     * */
    abstract fun productsDao(): ProductsDao
}