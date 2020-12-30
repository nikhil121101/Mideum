package com.example.mideum.repository

import android.util.Log
import com.example.mideum.models.domain.Article
import com.example.mideum.models.domain.Comment
import com.example.mideum.models.domain.Profile
import com.example.mideum.models.domain.User
import com.example.mideum.models.response.Errors
import com.example.mideum.models.request.addComment.AddComment
import com.example.mideum.models.request.addComment.NewComment
import com.example.mideum.models.request.createArticle.CreateArticle
import com.example.mideum.models.request.createArticle.NewArticle
import com.example.mideum.models.request.signIn.SignInUser
import com.example.mideum.models.request.signUp.SignUpUser
import com.example.mideum.models.request.updateUser.UpdateUser
import com.example.mideum.models.request.updateUser.UpdateUserDetails
import com.example.mideum.network.OpenNetwork
import com.example.mideum.network.SecureNetwork

object Repository {

    var token : String? = null

    suspend fun login(signInUser: SignInUser): User? {

        val response = OpenNetwork.api.login(signInUser)
        if(response.isSuccessful) {
            token = response.body()?.user?.token
        }
        return response.body()?.user

    }

    suspend fun signUp(signUpUser: SignUpUser): User? {

        val response = OpenNetwork.api.signUp(signUpUser)
        if(response.isSuccessful) {
            token = response.body()?.user?.token
        }
        else {
            Log.i("signUp error" , response.errorBody().toString())
            //TODO handel error
        }
        return response.body()?.user

    }

    suspend fun currentUser() : User?{
        val response = SecureNetwork.getAPI(token!!).currentUser()
        return response.body()?.user
    }

    suspend fun getGlobalFeed() : List<Article>? {
        val response = OpenNetwork.api.listArticles(null , null , null , 1000 , null)
        if(response.isSuccessful) {
            return response.body()?.articles
        }
        else {
            Log.i("getGlobalFeed" , response.errorBody().toString())
        }
        return null
    }

    suspend fun getMyFeed() : List<Article>? {
        //TODO Make sure user is logged in
        val response = SecureNetwork.getAPI(token!!).feedArticles(null , null)
        if(response.isSuccessful) {
            return response.body()?.articles
        }
        else {
            Log.i("getMyFeed error" , response.errorBody().toString())
        }
        return null
    }

    suspend fun createArticle(title : String , description: String , body : String) : Article?{
        return token?.let {
            SecureNetwork.getAPI(it).createArticle(CreateArticle(NewArticle(body , description ,
                null , title))).body()?.article
        }
    }

    suspend fun getComments(slug: String) : List<Comment>? {
        val response = OpenNetwork.api.getComments(slug)
        if(response.isSuccessful) {
            return response.body()?.comments
        }
        else {
            Log.i("getComments error" , response.errorBody().toString())
        }
        return null
    }

    suspend fun deleteComment(slug : String , id : Int) {
        val response = SecureNetwork.getAPI(token!!).deleteComment(slug , id)
    }

    suspend fun addComment(slug: String , text : String) {
        SecureNetwork.getAPI(token!!).addComment(AddComment(NewComment(text)) , slug)
    }

    suspend fun getArticleByAuthor(username : String) : List<Article>? {
        val response = OpenNetwork.api.listArticles(null , username , null , null , null)
        if(response.isSuccessful) {
            return response.body()?.articles
        }
        else {
            Log.i("getArticleAuthor error" , response.errorBody().toString())
        }
        return null
    }

    suspend fun getArticleByFavoritedBy(username : String) : List<Article>? {
        val response = OpenNetwork.api.listArticles(null , null , username , null , null)
        if(response.isSuccessful) {
            return response.body()?.articles
        }
        else {
            Log.i("getArticleFav error" , response.errorBody().toString())
        }
        return null
    }

    suspend fun followProfile(username: String) : Profile? {
        val response = SecureNetwork.getAPI(token!!).follow(username)
        if(response.isSuccessful) {
            return response.body()?.profile
        }
        else {
            Log.i("followProfile error" , response.errorBody().toString())
        }
        return null
    }

    suspend fun unFollowProfile(username: String) : Profile? {
        val response = SecureNetwork.getAPI(token!!).unFollow(username)
        if(response.isSuccessful) {
            return response.body()?.profile
        }
        else {
            Log.i("unfollowProfile error" , response.errorBody().toString())
        }
        return null
    }

    suspend fun updateUser(email: String? , username: String? , password: String? , imageUrl : String? , bio : String?) : User? {
        val response = SecureNetwork.getAPI(token!!).updateUser(UpdateUser(UpdateUserDetails(
                email = email,
                password = password,
                bio = bio,
                image = imageUrl,
                username = username
        )))
        if (response.isSuccessful) {
            return response.body()?.user
        }
        else {
            Log.i("updateUser error" , response.errorBody().toString())
        }
        return null
    }

    suspend fun favouriteArticle(slug: String): Article? {
        val response = SecureNetwork.getAPI(token!!).favouriteArticle(slug)
        return response.body()?.article
    }

    suspend fun unFavouriteArticle(slug: String): Article? {
        val response = SecureNetwork.getAPI(token!!).unFavouriteArticle(slug)
        return response.body()?.article
    }

}

//where to save the token
// i can save it in viewmodel or feagment