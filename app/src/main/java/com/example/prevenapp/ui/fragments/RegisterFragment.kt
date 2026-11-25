package com.example.prevenapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prevenapp.R
import com.example.prevenapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirmar.setOnClickListener {
            val nombres = binding.etNombres.text.toString().trim()
            val apellidos = binding.etApellidos.text.toString().trim()
            val correo = binding.etCorreo.text.toString().trim()
            val rh = binding.etRH.text.toString().trim()
            val contrasena = binding.etContrasena.text.toString().trim()

            if (rh.isEmpty()) {
                val bundle = bundleOf("errorMessage" to "Para crear la cuenta debes llenar el campo RH")
                findNavController().navigate(R.id.action_register_to_error, bundle)
            } else if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                val bundle = bundleOf("errorMessage" to "Por favor completa todos los campos obligatorios")
                findNavController().navigate(R.id.action_register_to_error, bundle)
            } else {
                val bundle = bundleOf("nombreUsuario" to nombres)
                findNavController().navigate(R.id.action_register_to_success, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
