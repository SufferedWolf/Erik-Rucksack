package com.captaindeer.erik_rucksack

/**
 * Created by suffered on 18/03/25
 */

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.captaindeer.erik_rucksack.core.navigation.AppNavigation
import com.captaindeer.erik_rucksack.ui.theme.ErikRucksackTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            ErikRucksackTheme {
                AppNavigation(auth)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //navegar a la home
            Log.i("erik", "Estoy logado")

            auth.signOut()
        }
    }
}
