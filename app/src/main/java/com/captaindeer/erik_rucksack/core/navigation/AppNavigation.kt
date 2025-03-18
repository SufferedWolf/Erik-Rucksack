package com.captaindeer.erik_rucksack.core.navigation

/**
 * Created by suffered on 18/03/25
 */

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.captaindeer.erik_rucksack.ui.screens.ApiConsumptionScreen
import com.captaindeer.erik_rucksack.ui.screens.CameraScreen
import com.captaindeer.erik_rucksack.ui.screens.FirebaseScreen
import com.captaindeer.erik_rucksack.ui.screens.LocalDatabaseScreen
import com.captaindeer.erik_rucksack.ui.screens.ResumeScreen
import com.captaindeer.erik_rucksack.ui.screens.SensorScreen
import com.captaindeer.erik_rucksack.ui.screens.WatchTalkScreen
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    var expanded by remember { mutableStateOf(false) }

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
                            text = { Text("🏠 firebaseScreen") },
                            onClick = {
                                expanded = false
                                navController.navigate("firebaseScreen")
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
        }
    }
}

@Composable
fun DoubleBackToExitScren(navController: NavController) {
    val context = LocalContext.current
    var backPressedTime by remember { mutableStateOf(0L) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Presiona atrás dos veces para salir")

        // Intercepta el botón de atrás
        BackHandler {
            val currentTime = System.currentTimeMillis()

            // Si el tiempo entre los dos clics es menor a 2 segundos, cierra la app
            if (currentTime - backPressedTime < 2000) {
                exitProcess(0) // Cierra la app completamente
            } else {
                backPressedTime = currentTime
                Toast.makeText(context, "Presiona nuevamente para salir", Toast.LENGTH_SHORT).show()

                // Limpia el stack de navegación para evitar que regrese a otra pantalla
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack(navController.graph.startDestinationId, inclusive = true)
                }
            }
        }
    }
}
