package com.example.mideum.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mideum.R
import com.example.mideum.databinding.FragmentSettingsBinding
import com.example.mideum.ui.auth.AuthViewModel

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding =  FragmentSettingsBinding.inflate(layoutInflater , container, false)

        val authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        authViewModel.user.observe(viewLifecycleOwner , {
            binding.user = it
        })

        authViewModel.updateProfileDone.observe(viewLifecycleOwner , {
            if(it) {
                Toast.makeText(context , "Profile updated" , Toast.LENGTH_SHORT).show()
                authViewModel.resetUpdateProfileDone()
            }
        })

        binding.updateSettings.setOnClickListener{
            authViewModel.updateUser(
                binding.emailSettings.text.toString().takeIf { it.isNotBlank() },
                binding.usernameSettings.text.toString().takeIf { it.isNotBlank() },
                binding.passwordSettings.text.toString().takeIf { it.isNotBlank() },
                binding.imageUrlSettings.text.toString(),
                binding.bioSettings.text.toString()
            )
        }

        binding.logoutSettings.setOnClickListener {
            authViewModel.initiateLogOut()
            findNavController().navigate(R.id.action_nav_settings_to_nav_auth)
        }

        return binding.root
    }

}