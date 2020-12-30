package com.example.mideum.ui.auth.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mideum.databinding.FragmentSignInBinding
import com.example.mideum.ui.auth.AuthViewModel

class SignInFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        authViewModel =
                ViewModelProvider(this).get(AuthViewModel::class.java)
        val binding = FragmentSignInBinding.inflate(inflater , container, false)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        binding.button.setOnClickListener {
            showLoading(binding)
            authViewModel.login(binding.email.text.toString() , binding.password.text.toString())
        }

        authViewModel.loginSignUpDone.observe(viewLifecycleOwner , {
            if(it) {
                hideLoading(binding)
                authViewModel.resetLoginSignUpDone()
            }
        })

        return binding.root
    }

    private fun showLoading(binding: FragmentSignInBinding) {
        binding.signInLoadingBar.visibility = View.VISIBLE
    }

    private fun hideLoading(binding: FragmentSignInBinding) {
        binding.signInLoadingBar.visibility = View.GONE
    }

}