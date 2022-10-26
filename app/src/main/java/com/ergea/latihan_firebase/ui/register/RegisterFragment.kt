package com.ergea.latihan_firebase.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ergea.latihan_firebase.R
import com.ergea.latihan_firebase.databinding.FragmentLoginBinding
import com.ergea.latihan_firebase.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        auth = Firebase.auth
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val email = binding.etEmailRegister.text.toString().trim()
        val username = binding.etUsernameRegister.text.toString().trim()
        val password = binding.etPasswordRegister.text.toString().trim()
        val cPassword = binding.etKonfirmPasswordRegister.text.toString().trim()
        if (email.isNotEmpty() || username.isNotEmpty() || password.isNotEmpty() || cPassword.isNotEmpty()) {
            if (password.equals(cPassword, ignoreCase = false)) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DONE", "createUserWithEmail:success")
                            val user = auth.currentUser
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            Toast.makeText(
                                requireContext(), "$user",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FALSE", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                requireContext(), "${task.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Password tidak sesuai", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}