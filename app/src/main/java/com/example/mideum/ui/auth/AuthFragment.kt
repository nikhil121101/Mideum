package com.example.mideum.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mideum.MainActivity
import com.example.mideum.R
import com.example.mideum.databinding.AuthFragmentBinding
import com.example.mideum.models.domain.User
import com.google.android.material.tabs.TabLayout

class AuthFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    var user : User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.i("AuthFragment" , "created")
        val binding = AuthFragmentBinding.inflate(inflater , container, false)

        val adapter = AuthAdapter(activity?.supportFragmentManager!!)
        binding.viewPager.adapter = adapter

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        authViewModel.user.observe(viewLifecycleOwner , {
            it?.let {
                findNavController().navigate(R.id.action_nav_auth_to_myFeedFragment)
            }
        })

        return binding.root
    }
}