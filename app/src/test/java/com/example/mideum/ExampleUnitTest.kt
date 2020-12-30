package com.example.mideum

import com.example.mideum.network.SecureNetwork
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    //val coroutineScope = CoroutineScope(Dispatchers.Default)
//
//    @Test
//    fun testListArticles() {
//        //coroutineScope.launch {
//        runBlocking {
//            val articles = Network.apiClient.listArticles(null , "444" , null , null , null)
//            assert(articles.body()?.articles?.isNotEmpty()!!)
//        }
//        //}
//    }
//    @Test
//    fun testSignUp() {
//        //coroutineScope.launch {
//        runBlocking {
//            val user = Network.apiClient.signUp(SignUpUser(SignUpUserDetails(
//                email = "undertaker69@gmail.com",
//                password = "undertaker69@42",
//                username = "undertaker69@42"
//            )))
//            assertEquals("undertaker69@42" , user.body()?.user?.username)
//        }
//        //}
//    }

    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTMxMTMxLCJ1c2VybmFtZSI6IntoZWxsc3Rhcn0iLCJleHAiOjE2MTM5Njg1Njh9.hVzhqxQpIEyhbh8g3AYIzgTLaQ7Mc_K7lHtNH3dO-R0"

    @Test
    fun testCurrentUser() {
        runBlocking {
            val user = SecureNetwork(token).api.currentUser()
            assertEquals("{hellstar}" , user.body()?.user?.username)
        }
    }
//
//    public lateinit var token : String
////
//    @Test
//    fun testSingInUser() {
//        runBlocking {
//            val user = Network.apiClient.login(SignInUser(SignInUserDetails(
//                email = "undertaker69@gmail.com",
//                password = "undertaker69@42",
//            )))
//            assertEquals("undertaker69@42" , user.body()?.user?.username)
//            token = user.body()?.user?.token!!
//        }
//    }

//    @Test
//    fun testUpdateUser() {
//        runBlocking {
//            val user = Network.apiClient.updateUser(
//                UpdateUser(
//                    UpdateUserDetails(
//                    email = "undertaker69@gmail.com",
//                    bio = "hi my name is undertaker",
//                    image = "https://img.mensxp.com/media/content/2020/May/How-Vince-McMahon-Forced-The-Undertaker-To-Lose-Against-Lesnar-600x900_5ec66fa4d7612_600x900.jpeg"
//                    )
//                )
//            )
//            assertEquals("hi my name is undertaker" , user.body()?.user?.bio)
//        }
//    }


}