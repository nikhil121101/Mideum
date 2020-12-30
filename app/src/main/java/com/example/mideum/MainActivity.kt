package com.example.mideum

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mideum.models.domain.User
import com.example.mideum.repository.Repository
import com.example.mideum.ui.auth.AuthViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var authViewModel: AuthViewModel

    companion object {
        const val TOKEN_SP_KEY = "token_key"
        var user : User? = null
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedPref : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /****************************************/
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_auth , R.id.nav_my_feed , R.id.nav_global_feed), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navView.menu.getItem(3).isChecked = false
        navView.menu.getItem(0).isChecked = true


        /****************************************/

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        authViewModel.user.observe(this , {
            it?.let {
                user = it
                loggedInMenu(navView)
            }
        })

        authViewModel.loginSignUpDone.observe(this) {
            if(it) {
                saveData()
            }
        }

        authViewModel.logout.observe(this) {
            if(it) {
                user = null
                Repository.token = null
                sharedPref.edit().clear().apply()
                loggedOutMenu(navView)
            }
        }

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val token = retrieveData()
        if(!token.isNullOrBlank()) {
            Repository.token = token
            authViewModel.getCurrentUser()
        }

    }

    private fun loggedInMenu(navView : NavigationView) {
        val menu = navView.menu
        menu.findItem(R.id.nav_auth).isVisible = false
        menu.findItem(R.id.nav_settings).isVisible = true
        menu.findItem(R.id.nav_my_feed).isVisible = true
        menu.findItem(R.id.profileFragment).isVisible = true
    }

    private fun loggedOutMenu(navView : NavigationView) {
        val menu = navView.menu
        menu.findItem(R.id.nav_auth).isVisible = true
        menu.findItem(R.id.nav_settings).isVisible = false
        menu.findItem(R.id.nav_my_feed).isVisible = false
        menu.findItem(R.id.profileFragment).isVisible = false
    }

    private fun saveData() {
        val sharedEditor = sharedPref.edit()
        sharedEditor.putString(TOKEN_SP_KEY , Repository.token)
        sharedEditor.apply()
    }

    private fun retrieveData() : String? {
        return sharedPref.getString(TOKEN_SP_KEY , "")
    }

    fun getTokeFromSharedPreference() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}