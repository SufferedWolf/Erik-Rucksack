package com.captaindeer.erik_rucksack.ui.screens

/**
 * Created by suffered on 18/03/25
 */

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.captaindeer.erik_rucksack.core.utils.ScreensBackHandlerCustom

@Composable
fun FirebaseScreen(){
    ScreensBackHandlerCustom.DoubleBackToExit()
    Text(text = "Hello Firebase Screen!")
}