package com.example.prevenapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.content.Intent
import com.example.prevenapp.DashboardUsuarioActivity
import com.example.prevenapp.R
import com.example.prevenapp.databinding.FragmentRegisterSuccessBinding

class RegisterSuccessFragment : Fragment(R.layout.fragment_register_success) {
    private var _binding: FragmentRegisterSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombreUsuario = arguments?.getString("nombreUsuario")

        binding.btnContinuar.setOnClickListener {
            val intent = Intent(requireContext(), DashboardUsuarioActivity::class.java).apply {
                putExtra("NOMBRE_USUARIO", nombreUsuario)
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
