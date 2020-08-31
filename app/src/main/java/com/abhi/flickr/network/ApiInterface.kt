package com.abhi.flickr.network



import com.abhi.flickr.repository.model.ResponsePhotoItemHolder
import com.abhi.flickr.utils.Flickrutils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("?format=json&nojsoncallback=1&method=" + Flickrutils.METHOD_SEARCH + "&safe_search=1")
    fun getImageItemList(
        @Query("api_key") key: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("text") query: String
    ): Call<ResponsePhotoItemHolder>
}