package com.example.prevenapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prevenapp.R
import com.example.prevenapp.databinding.FragmentPasswordRecoveryNewBinding

class PasswordRecoveryNewFragment : Fragment(R.layout.fragment_password_recovery_new) {
    private var _binding: FragmentPasswordRecoveryNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextStep3.setOnClickListener {
            findNavController().navigate(R.id.action_recoveryNew_to_success)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
