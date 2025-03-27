package com.captaindeer.erik_rucksack.ui.viewmodels

import android.app.Application
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Created by suffered on 27/03/25
 */

class LightViewModel(application: Application) : AndroidViewModel(application),
    SensorEventListener {
    private val sensorManager = application.getSystemService(SensorManager::class.java)
    private val lightSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
    private val _lightValue = MutableStateFlow(0f)
    val lightValue: StateFlow<Float> = _lightValue

    init {
        registerSensor()
    }

    private fun registerSensor() {
        lightSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.firstOrNull()?.let { lux ->
            viewModelScope.launch {
                _lightValue.value = lux
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager?.unregisterListener(this)
    }
}

class CompassViewModel(application: Application) : AndroidViewModel(application) {
    private val sensorManager = application.getSystemService(SENSOR_SERVICE) as SensorManager
    private val _magneticField = MutableStateFlow(0f)
    val magneticField: StateFlow<Float> = _magneticField.asStateFlow()

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    val x = it.values[0]
                    val y = it.values[1]
                    val azimuth = atan2(y, x) * (180 / Math.PI).toFloat() // Convertir a grados
                    _magneticField.value = azimuth
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    init {
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensor?.let {
            sensorManager.registerListener(
                sensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(sensorEventListener)
    }
}

class AccelerometerViewModel(application: Application) : AndroidViewModel(application),
    SensorEventListener {
    private val sensorManager = application.getSystemService(SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val _energyLevel = MutableStateFlow(50f) // Energía inicial
    val energyLevel: StateFlow<Float> = _energyLevel
    private val _speed = MutableStateFlow(0f)
    val speed: StateFlow<Float> = _speed
    private var lastAcceleration = 0f
    private var lastUpdateTime = 0L

    //buscar como incorporar la opcion de vibracion
    //private val vibrator = application.getSystemService(VIBRATOR_SERVICE) as Vibrator
    //private val mediaPlayer = MediaPlayer.create(application, R.raw.charge_sound) // Agrega charge_sound.mp3 en res/raw

    init {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        decreaseEnergyOverTime()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]
            val acceleration = sqrt(x * x + y * y + z * z)
            val currentTime = System.currentTimeMillis()

            if (acceleration - lastAcceleration > 5) { // Detecta sacudida
                _energyLevel.value = (_energyLevel.value + 10).coerceAtMost(100f)
                // Cuando la energía se llena
                if (_energyLevel.value == 100f) {
                    println("Cien porciento")
                    // mediaPlayer.start()
                }
            }
            // Calcular velocidad
            if (currentTime - lastUpdateTime > 500) {
                val deltaTime = (currentTime - lastUpdateTime) / 1000f
                val speedMps = acceleration * deltaTime
                val speedKmh = speedMps * 3.6f
                _speed.value = speedKmh
                lastUpdateTime = currentTime
            }
            lastAcceleration = acceleration
        }
    }

    private fun decreaseEnergyOverTime() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _energyLevel.value = (_energyLevel.value - 2).coerceAtLeast(0f) // Baja cada segundo
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        sensorManager.unregisterListener(this)
        //mediaPlayer.release()
        println("Pasando por onCLeared")
        super.onCleared()
    }
}

class TemperatureViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {
    private val sensorManager = application.getSystemService(SENSOR_SERVICE) as SensorManager
    private val temperatureSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    private val _temperature = MutableStateFlow(25f) // Temperatura inicial
    val temperature: StateFlow<Float> = _temperature

    init {
        temperatureSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            _temperature.value = it.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        sensorManager.unregisterListener(this)
        super.onCleared()
    }
}

class GyroscopeViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager = application.getSystemService(SENSOR_SERVICE) as SensorManager
    private val gyroscopeSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val _rotationSpeed = MutableStateFlow(0f) // Velocidad angular inicial
    val rotationSpeed: StateFlow<Float> = _rotationSpeed

    init {
        gyroscopeSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // Magnitud de la velocidad angular combinada de los tres ejes
            val speed = sqrt(it.values[0].pow(2) + it.values[1].pow(2) + it.values[2].pow(2))
            _rotationSpeed.value = speed
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        sensorManager.unregisterListener(this)
        super.onCleared()
    }
}
