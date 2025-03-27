package com.captaindeer.erik_rucksack.ui.screens

/**
 * Created by suffered on 18/03/25
 */

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.captaindeer.erik_rucksack.ui.viewmodels.AccelerometerViewModel
import com.captaindeer.erik_rucksack.ui.viewmodels.CompassViewModel
import com.captaindeer.erik_rucksack.ui.viewmodels.GyroscopeViewModel
import com.captaindeer.erik_rucksack.ui.viewmodels.LightViewModel
import com.captaindeer.erik_rucksack.ui.viewmodels.TemperatureViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SensorScreen() {
    val viewModel: LightViewModel = viewModel()
    val light by viewModel.lightValue.collectAsState()

    //Trabajo realizado
    // LightSensor(light)
    //CompassScreen()
    //ShakeEnergyScreen()
    //WeatherMoodScreen()
    SpinSpeedMeterScreen()

}

@Composable
fun LightSensor(light: Float) {
    val maxLux = 1000f
    val quarter = maxLux / 4

    val backgroundColor = when {
        light < quarter -> Color.Black
        light < quarter * 2 -> Color.Cyan
        light < quarter * 3 -> Color.Green
        light < quarter * 4 -> Color.Yellow
        else -> Color.White
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Sensor de Luz", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Intensidad de luz: $light lux",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CompassScreen(viewModel: CompassViewModel = viewModel()) {
    val azimuth by viewModel.magneticField.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Brújula", fontSize = 24.sp, color = Color.White)

        Canvas(modifier = Modifier.size(250.dp)) {
            val center = Offset(size.width / 2, size.height / 2)

            // Dibujar círculo
            drawCircle(
                color = Color.White,
                center = center,
                radius = size.minDimension / 2,
                style = Stroke(width = 8f)
            )

            // Dibujar aguja roja (Norte)
            val needleLength = size.minDimension / 2.5f
            val angle = -azimuth // Invertimos el ángulo para que funcione correctamente
            val needleEnd = Offset(
                x = center.x + needleLength * cos(Math.toRadians(angle.toDouble())).toFloat(),
                y = center.y + needleLength * sin(Math.toRadians(angle.toDouble())).toFloat()
            )

            drawLine(
                color = Color.Red, start = center, end = needleEnd, strokeWidth = 8f
            )

            // Dibujar el punto central
            drawCircle(
                color = Color.Red, center = center, radius = 10f
            )
        }

        Text(text = "Azimuth: ${azimuth.toInt()}°", fontSize = 18.sp, color = Color.White)
    }
}

@Composable
fun ShakeEnergyScreen(viewModel: AccelerometerViewModel = viewModel()) {
    val energy by viewModel.energyLevel.collectAsState()
    val speed by viewModel.speed.collectAsState()
    val animatedEnergy by animateFloatAsState(targetValue = energy, animationSpec = tween(500))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Shake to Charge", fontSize = 24.sp, color = Color.White)

        // Barra de batería animada
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(16.dp)
        ) {
            val batteryWidth = size.width * (animatedEnergy / 100)
            drawRect(Color.Green, size = Size(batteryWidth, size.height))
        }

        Text("Battery: ${energy.toInt()}%", color = Color.Green, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Velocímetro
        Text("Speed: ${speed.toInt()} Km/h", color = Color.Cyan, fontSize = 20.sp)
    }
}

@Composable
fun WeatherMoodScreen(viewModel: TemperatureViewModel = viewModel()) {
    val temperature by viewModel.temperature.collectAsState()

    val (bgColor, emoji, mood) = when {
        temperature < 20 -> Triple(Color(0xFF87CEFA), "🥶", "Frío")
        temperature in 21f..28f -> Triple(Color(0xFF90EE90), "🙂", "Normal")
        else -> Triple(Color(0xFFFF6347), "🥵", "Calor")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Weather Mood", fontSize = 24.sp, color = Color.White)
            Text(text = "$emoji", fontSize = 80.sp)
            Text(text = "$mood (${temperature.toInt()}°C)", fontSize = 24.sp, color = Color.White)
        }
    }
}

@Composable
fun SpinSpeedMeterScreen(viewModel: GyroscopeViewModel = viewModel()) {
    val speed by viewModel.rotationSpeed.collectAsState()

    // Definir colores según la velocidad
    val (bgColor, textColor) = when {
        speed < 6 -> Pair(Color(0xFFADD8E6), Color.Black)  // Azul claro (lento)
        speed in 6f..16f -> Pair(Color(0xFFFFA500), Color.Black)  // Naranja (moderado)
        else -> Pair(Color(0xFFFF0000), Color.White)  // Rojo (rápido)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Spin Speed Meter", fontSize = 24.sp, color = textColor)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Velocidad: ${"%.2f".format(speed)} rad/s",
                fontSize = 32.sp,
                color = textColor
            )
        }
    }
}
