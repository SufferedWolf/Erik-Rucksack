package com.captaindeer.erik_rucksack.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by suffered on 02/04/25
 */

class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _authResult = MutableStateFlow<AuthResult?>(null)
    val authResult: StateFlow<AuthResult?> = _authResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login(navigateToHome: () -> Unit) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(_email.value, _password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authResult.value = task.result
                        navigateToHome()
                        Log.i("Auth", "LOGIN OK")
                    } else {
                        _errorMessage.value = task.exception?.message ?: "Login failed"
                        Log.i("Auth", "LOGIN FAILED")
                    }
                }
        }
    }

    fun signUp(navigateToHome: () -> Unit) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(_email.value, _password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authResult.value = task.result
                        navigateToHome()
                        Log.i("Auth", "SIGNUP OK")
                    } else {
                        _errorMessage.value = task.exception?.message ?: "Signup failed"
                        Log.i("Auth", "SIGNUP FAILED")
                    }
                }
        }
    }
}
