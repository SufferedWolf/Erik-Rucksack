package com.captaindeer.erik_rucksack.ui

/**
 * Created by suffered on 18/03/25
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.captaindeer.erik_rucksack.R
import com.captaindeer.erik_rucksack.core.navigation.AppNavigation
import com.captaindeer.erik_rucksack.ui.theme.ErikRucksackTheme

class ErikRucksackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_erik_rucksack)

        setContent {
            ErikRucksackTheme {
                AppNavigation()
            }
        }
    }
}

