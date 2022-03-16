package com.example.pinterest.network.service

import com.example.thecatapi.model.Photo
import com.example.thecatapi.model.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface PhotoService {

    companion object {
        private const val ACCESS_KEY = "7a8af53f-33d7-411e-9df7-c7b6a971ffaf"
    }

    @Headers("x-api-key: $ACCESS_KEY")
    @GET("images/search")
    fun getPhotos(@Query("page") page: Int, @Query("limit") limit: Int): Call<ArrayList<Photo>>

    @Headers("x-api-key: $ACCESS_KEY")
    @GET("images")
    fun getMyCats(): Call<ArrayList<Post>>

    @Headers("x-api-key: $ACCESS_KEY")
    @Multipart
    @POST("images/upload")
    fun uploadFile(@Part image: MultipartBody.Part?, @Part("sub_id") sub_id: String?): Call<ResponseBody>
}