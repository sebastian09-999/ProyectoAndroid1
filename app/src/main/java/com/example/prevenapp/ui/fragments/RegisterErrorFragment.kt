package com.example.prevenapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prevenapp.R
import com.example.prevenapp.databinding.FragmentRegisterErrorBinding

class RegisterErrorFragment : Fragment(R.layout.fragment_register_error) {
    private var _binding: FragmentRegisterErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val errorMessage = arguments?.getString("errorMessage") ?: "Para crear la cuenta debes llenar el campo RH"
        binding.tvErrorMessage.text = errorMessage

        binding.btnVolverFormulario.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
