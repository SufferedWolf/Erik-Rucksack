package com.captaindeer.erik_rucksack.core.utils

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

/**
 * Created by suffered on 18/03/25
 */

object ScreensBackHandlerCustom {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun DoubleBackToExit(){
        var backPressedTime by remember { mutableStateOf(0L) }
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) {
            BackHandler(true) {
                val currentTime = System.currentTimeMillis()
                // Si el tiempo entre los dos clics es menor a 2 segundos, cierra la app
                if (currentTime - backPressedTime < 2000) {
                    exitProcess(0) // Cierra la app completamente
                } else {
                    backPressedTime = currentTime
                    scope.launch {
                        snackBarHostState.showSnackbar("Double back to exit")
                    }
                }
            }
        }
    }
}