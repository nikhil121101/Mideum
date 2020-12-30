package com.example.mideum.ui.auth.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mideum.databinding.FragmentSignUpBinding
import com.example.mideum.ui.auth.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        authViewModel =
                ViewModelProvider(this).get(AuthViewModel::class.java)
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding.button.setOnClickListener {
            showLoading(binding)
            authViewModel.signUp(binding.username.text.toString() , binding.email.text.toString() , binding.password.text.toString())
        }

        authViewModel.loginSignUpDone.observe(viewLifecycleOwner , {
            if(it) {
                hideLoading(binding)
                authViewModel.resetLoginSignUpDone()
            }
        })

        return binding.root
    }

    fun showLoading(binding: FragmentSignUpBinding) {
        binding.signUpLoadingBar.visibility = View.VISIBLE
    }

    fun hideLoading(binding: FragmentSignUpBinding) {
        binding.signUpLoadingBar.visibility = View.GONE
    }

}