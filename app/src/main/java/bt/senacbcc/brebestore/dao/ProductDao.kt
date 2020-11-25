package bt.senacbcc.brebestore.dao

import androidx.room.*
import bt.senacbcc.brebestore.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Insert()
    fun insert(product: Product)

    @Update
    fun edit(product: Product)

    @Delete
    fun delete(product: Product)
}