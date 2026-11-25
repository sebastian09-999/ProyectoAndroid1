package com.example.prevenapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prevenapp.R
import com.example.prevenapp.databinding.FragmentPasswordRecoveryEmailBinding

class PasswordRecoveryEmailFragment : Fragment(R.layout.fragment_password_recovery_email) {
    private var _binding: FragmentPasswordRecoveryEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextStep1.setOnClickListener {
            findNavController().navigate(R.id.action_recoveryEmail_to_code)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
