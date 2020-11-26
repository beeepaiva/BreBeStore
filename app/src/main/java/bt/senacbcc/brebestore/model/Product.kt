package bt.senacbcc.brebestore.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var desc: String,
    var price: Float,
    var urlImg: String?,
    var qtd: Int?,
    var purchased: Boolean = false
)