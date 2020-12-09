package bt.senacbcc.brebestore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import bt.senacbcc.brebestore.dao.ProductDao
import bt.senacbcc.brebestore.model.Product

@Database(entities = arrayOf(Product::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

