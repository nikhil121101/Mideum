package com.example.mideum.network

import com.example.mideum.models.domain.Tags
import com.example.mideum.models.request.signIn.SignInUser
import com.example.mideum.models.request.signUp.SignUpUser
import com.example.mideum.models.response.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

const val baseUrl = "https://conduit.productionready.io/api/"

interface ApiServices {

    //Authentication

    @POST("users/login")
    suspend fun login(@Body user : SignInUser) : Response<ResponseUser>

    @POST("users")
    suspend fun signUp(@Body user : SignUpUser) : Response<ResponseUser>

    //Profile

    @GET("profiles/{username}")
    suspend fun getProfile(@Path("username")username : String) : Response<ResponseProfile>


    //Articles

    @GET("articles")
    suspend fun listArticles(@Query("tag") tag : String? ,
                             @Query("author")author : String? ,
                             @Query("favorited")favoritedBy : String?,
                             @Query("limit")limit : Int?,
                             @Query("offset")offset : Int?) : Response<MultipleArticles>


    @GET("articles/{slug}")
    suspend fun getArticle(@Path("slug")slug : String) : Response<SingleArticle>

    @GET("articles/{slug}/comments")
    suspend fun getComments(@Path("slug")slug : String) : Response<MultipleComments>

    @GET("tags")
    suspend fun listTags() : Response<Tags>

}

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

object OpenNetwork {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    val api = retrofit.create(ApiServices::class.java)
}
