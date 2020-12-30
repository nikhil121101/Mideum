package com.example.mideum.network

import com.example.mideum.models.request.addComment.AddComment
import com.example.mideum.models.request.createArticle.CreateArticle
import com.example.mideum.models.request.updateArticle.UpdatedArticle
import com.example.mideum.models.request.updateUser.UpdateUser
import com.example.mideum.models.response.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface SecureApiServices {

    //Authentication

    @GET("user")
    suspend fun currentUser() : Response<ResponseUser>

    @PUT("user")
    suspend fun updateUser(@Body user : UpdateUser) : Response<ResponseUser>

    //Profile

    @POST("profiles/{username}/follow")
    suspend fun follow(@Path("username") username : String) : Response<ResponseProfile>

    @DELETE("profiles/{username}/follow")
    suspend fun unFollow(@Path("username") username : String) : Response<ResponseProfile>

    //Articles

    @GET("articles/feed")
    suspend fun feedArticles(@Query("limit")limit : Int?,
                             @Query("offset")offset : Int?) : Response<MultipleArticles>

    @POST("articles")
    suspend fun createArticle(@Body article : CreateArticle) : Response<SingleArticle>

    @PUT("articles/{slug}")
    suspend fun updateArticle(@Body article : UpdatedArticle , @Path("slug") slug : String) : Response<SingleArticle>

    @DELETE("articles/{slug}")
    suspend fun deleteArticle(@Path("slug")slug : String)

    @POST("articles/{slug}/comments")
    suspend fun addComment(@Body comment : AddComment , @Path("slug") slug : String) : Response<SingleComment>

    @DELETE("articles/{slug}/comments/{id}")
    suspend fun deleteComment(@Path("slug") slug : String , @Path("id") id : Int)

    @POST("articles/{slug}/favorite")
    suspend fun favouriteArticle(@Path("slug") slug : String) : Response<SingleArticle>

    @DELETE("articles/{slug}/favorite")
    suspend fun unFavouriteArticle(@Path("slug") slug : String) : Response<SingleArticle>

}

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//
class SecureNetwork() {
     //Configure retrofit to parse JSON and use coroutines

    companion object {

        @Volatile private var instance : SecureApiServices? = null

        fun getAPI(token: String) = instance ?: synchronized(this) {
            instance ?: createRetrofitObject(token).also {
                instance = it;
            }
        }

        private fun getOkHttpClientBuilder(token : String): OkHttpClient.Builder {

            val okHttpBuilder = OkHttpClient.Builder()

            okHttpBuilder.addInterceptor { chain ->

                val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Token $token")
                        .build()

                chain.proceed(request)
            }

            return okHttpBuilder
        }

        fun createRetrofitObject(token : String) : SecureApiServices{
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getOkHttpClientBuilder(token).build())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build().create(SecureApiServices::class.java)
        }

    }

}
