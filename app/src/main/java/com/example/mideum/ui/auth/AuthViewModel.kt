package com.example.mideum.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mideum.models.domain.User
import com.example.mideum.models.request.signIn.SignInUser
import com.example.mideum.models.request.signIn.SignInUserDetails
import com.example.mideum.models.request.signUp.SignUpUser
import com.example.mideum.models.request.signUp.SignUpUserDetails
import com.example.mideum.repository.Repository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    private val _logOut = MutableLiveData<Boolean>()
    val logout : LiveData<Boolean> = _logOut

    private val _loginSignUpDone = MutableLiveData<Boolean>()
    val loginSignUpDone : LiveData<Boolean> = _loginSignUpDone

    private val _updateProfileDone = MutableLiveData<Boolean>()
    val updateProfileDone : LiveData<Boolean> = _updateProfileDone

    fun resetLoginSignUpDone() {
        _loginSignUpDone.value = false
    }

    fun resetUpdateProfileDone() {
        Log.i("f" , "_______F__________")
        _updateProfileDone.value = false
    }

    fun resetLogout() {
        _logOut.value = false
    }


    fun initiateLogOut() {
        _user.value = null
        _logOut.value = true
    }

    fun login(email : String , password : String) {
        viewModelScope.launch {
            val responseUser = Repository.login(SignInUser(SignInUserDetails(email , password)))
            _user.value = responseUser
            _loginSignUpDone.value = true
        }
    }

    fun signUp(username : String , email : String , password : String) {
        viewModelScope.launch {
            val user = Repository.signUp(SignUpUser(SignUpUserDetails(email = email , password = password , username = username)))
            _user.postValue(user)
            _loginSignUpDone.value = true
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            val user = Repository.currentUser()
            _user.value = user
        }
    }

    fun updateUser(email: String? , username: String? , password: String? , imageUrl : String? , bio : String?) {
        viewModelScope.launch {
            val user = Repository.updateUser(email , username , password , imageUrl , bio)
            _user.postValue((user))
            _updateProfileDone.value = true
        }
    }

}