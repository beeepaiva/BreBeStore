package bt.senacbcc.brebestore.apis

import bt.senacbcc.brebestore.model.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductAPI {

    @GET("/users/{name}/history")
    fun getAll(@Path("name") name : String?): Call<List<Product>>

    @POST("/users/{name}/history.json")
    fun insert(@Path("name") name : String?, @Body purchase: Product): Call<Void>
}