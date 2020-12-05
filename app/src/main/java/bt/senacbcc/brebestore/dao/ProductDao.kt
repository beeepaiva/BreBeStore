package bt.senacbcc.brebestore.dao

import androidx.room.*
import bt.senacbcc.brebestore.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Insert()
    fun insert(product: Product)

    //Inserindo conforme quantidade
    @Query("UPDATE Product SET qtd = :qtd WHERE idFB = :id")
    fun insertMore(qtd: Int, id: Int)

    @Update
    fun edit(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("UPDATE Product SET qtd = :qtd WHERE id = :id")
    fun deleteUnity(id: Int, qtd: Int)
}