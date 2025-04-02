package com.captaindeer.erik_rucksack.core.navigation

/**
 * Created by suffered on 18/03/25
 */

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.captaindeer.erik_rucksack.ui.screens.ApiConsumptionScreen
import com.captaindeer.erik_rucksack.ui.screens.CameraScreen
import com.captaindeer.erik_rucksack.ui.screens.FirebaseScreen
import com.captaindeer.erik_rucksack.ui.screens.InitialScreen
import com.captaindeer.erik_rucksack.ui.screens.LocalDatabaseScreen
import com.captaindeer.erik_rucksack.ui.screens.LoginScreen
import com.captaindeer.erik_rucksack.ui.screens.ResumeScreen
import com.captaindeer.erik_rucksack.ui.screens.SensorScreen
import com.captaindeer.erik_rucksack.ui.screens.SignUpScreen
import com.captaindeer.erik_rucksack.ui.screens.WatchTalkScreen
import com.captaindeer.erik_rucksack.ui.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(auth: FirebaseAuth) {

    val navController = rememberNavController()
    var expanded by remember { mutableStateOf(false) }
    val loginVM = LoginViewModel(auth)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi App") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.Settings, contentDescription = "menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("🏠 apiConsumptionScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("apiConsumptionScreen")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("🏠 cameraScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("cameraScreen")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("🏠 initialScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("initialScreen")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("🏠 localDatabaseScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("localDatabaseScreen")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("🏠 resumeScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("resumeScreen")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("🏠 sensorScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("sensorScreen")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("🏠 watchTalkScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("watchTalkScreen")
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "resumeScreen",
            modifier = Modifier.padding(padding)
        ) {
            composable("apiConsumptionScreen") { ApiConsumptionScreen() }
            composable("cameraScreen") { CameraScreen() }
            composable("firebaseScreen") { FirebaseScreen() }
            composable("localDatabaseScreen") { LocalDatabaseScreen() }
            composable("resumeScreen") { ResumeScreen() }
            composable("sensorScreen") { SensorScreen() }
            composable("watchTalkScreen") { WatchTalkScreen() }
            composable("initialScreen") {
                InitialScreen(
                    navigateToLogin = { navController.navigate("loginScreen") },
                    navigateToSignUp = { navController.navigate("signUpScreen") })
            }
            composable("loginScreen") {
                LoginScreen(
                    loginVM,
                    navigateToHome = { navController.navigate("initialScreen") })
            }
            composable("signUpScreen") {
                SignUpScreen(
                    loginVM,
                    navigateToHome = { navController.navigate("initialScreen") })
            }
        }
    }
}
