package com.example.mideum.ui.profileView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mideum.models.domain.Article
import com.example.mideum.models.domain.Profile
import com.example.mideum.repository.Repository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _article = MutableLiveData<List<Article>>()
    val article : LiveData<List<Article>> = _article

    private val _profile = MutableLiveData<Profile>()
    val profile : LiveData<Profile> = _profile

    private val _stopLoadingBar = MutableLiveData<Boolean>()
    val stopLoadingBar : LiveData<Boolean> = _stopLoadingBar

    fun getArticlesOf(username : String) {
        viewModelScope.launch {
            _stopLoadingBar.value = false
            val res = Repository.getArticleByAuthor(username)
            _article.value = res
            _stopLoadingBar.value = true
        }
    }

    fun getFavouriteArticlesOf(username: String) {
        viewModelScope.launch {
            _stopLoadingBar.value = false
            val res = Repository.getArticleByFavoritedBy(username)
            _article.value = res
            _stopLoadingBar.value = true
        }
    }

    fun followProfile(username: String) {
        viewModelScope.launch {
            val responseProfile = Repository.followProfile(username)
            _profile.value = responseProfile
        }
    }

    fun unFollowProfile(username: String) {
        viewModelScope.launch {
            val responseProfile = Repository.unFollowProfile(username)
            _profile.value = responseProfile
        }
    }

}