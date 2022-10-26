package com.ergea.latihan_firebase.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.ergea.latihan_firebase.R
import com.ergea.latihan_firebase.databinding.FragmentLoginBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        auth = Firebase.auth
        firebaseAnalytics = Firebase.analytics
        toRegister()
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnAnalytics.setOnClickListener {
            testAnalytics()
        }
        binding.btnCrashlytics.setOnClickListener {
            testCrashlytics()
        }
    }

    private fun testAnalytics() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {

        }
    }

    private fun testCrashlytics() {
        throw RuntimeException("Test Crash")
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        if (email.isNotEmpty() || password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SUCCESS", "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            requireContext(), "${user?.email}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("FAILED", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(requireContext(), "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }

    }

    private fun toRegister() {
        binding.textBpa.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}