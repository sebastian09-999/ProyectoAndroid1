package com.example.prevenapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prevenapp.R
import com.example.prevenapp.databinding.FragmentPasswordRecoveryCodeBinding

class PasswordRecoveryCodeFragment : Fragment(R.layout.fragment_password_recovery_code) {
    private var _binding: FragmentPasswordRecoveryCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextStep2.setOnClickListener {
            findNavController().navigate(R.id.action_recoveryCode_to_newPass)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
