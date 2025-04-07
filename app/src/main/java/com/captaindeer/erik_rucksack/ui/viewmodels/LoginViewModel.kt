package com.captaindeer.erik_rucksack.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.captaindeer.erik_rucksack.data.login.model.Artist
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Created by suffered on 02/04/25
 */

class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {

    private val _user = MutableStateFlow(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _authResult = MutableStateFlow<AuthResult?>(null)
    val authResult: StateFlow<AuthResult?> = _authResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist: StateFlow<List<Artist>> = _artist

    private var db: FirebaseFirestore = Firebase.firestore


    init {
        getArtists()
    }


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

    fun logout(navigateToInitial: () -> Unit) {
        auth.signOut()
        _user.value = null
        navigateToInitial()
    }

    fun getArtists() {
        viewModelScope.launch {
            val result: List<Artist> = withContext(Dispatchers.IO) {
                getAllArtists()
            }
            println("Valor de la consulta = $result")
            println("Valor  = ${result.size}")
            _artist.value = result
        }
    }

    suspend fun getAllArtists(): List<Artist> {
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Artist::class.java)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

}
