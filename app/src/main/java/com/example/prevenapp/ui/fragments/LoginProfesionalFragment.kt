package com.example.prevenapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.content.Intent
import com.example.prevenapp.MainActivity
import com.example.prevenapp.R
import com.example.prevenapp.databinding.FragmentLoginProfesionalBinding

class LoginProfesionalFragment : Fragment(R.layout.fragment_login_profesional) {
    private var _binding: FragmentLoginProfesionalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginProfesionalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdjuntarArchivo.setOnClickListener {
            binding.tvNombreArchivo.text = "documento_credencial.pdf"
            binding.tvNombreArchivo.setTextColor(resources.getColor(android.R.color.black, null))
        }

        binding.btnIniciarSesion.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnAtras.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvRecuperarContrasena.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_recoveryEmail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
