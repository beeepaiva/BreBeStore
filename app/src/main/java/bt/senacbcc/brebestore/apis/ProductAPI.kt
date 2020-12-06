package bt.senacbcc.brebestore.apis

import bt.senacbcc.brebestore.model.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductAPI {

    @GET("/users/{name}/history")
    fun getAll(@Path("name") name : String?): Call<List<Product>>

    @POST("/users/{uid}/history.json")
    fun insert(@Path("uid") name : String?, @Body purchase: Product): Call<Void>
}