package com.captaindeer.erik_rucksack

/**
 * Created by suffered on 18/03/25
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.captaindeer.erik_rucksack.core.navigation.AppNavigation
import com.captaindeer.erik_rucksack.ui.theme.ErikRucksackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ErikRucksackTheme {
                AppNavigation()
            }
        }
    }
}
