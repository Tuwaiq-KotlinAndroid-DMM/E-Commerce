package sa.edu.twuaiq.e_commerce.model.product


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*** You define each Room entity as a class that is annotated with @Entity.
A Room entity includes fields for each column in the corresponding table in the database,
including one or more columns that comprise the primary key.
 **/

/***
 * The following code defines a ItemModel data entity.
 * Each instance of ItemModel represents a row in a ItemModel table in the app's database.
 * */
@Entity
data class Product(
    @SerializedName("description")
    val description: String,
    /*
     * Each Room entity must define a primary key that uniquely identifies each row in the corresponding database table.
     * The most straightforward way of doing this is to annotate a single column with @PrimaryKey:
     * */
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String
)