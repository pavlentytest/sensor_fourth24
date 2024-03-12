package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    var lightSensor: Sensor? = null
    var pressureSensor: Sensor? = null
    var gyroSensor: Sensor? = null
    private lateinit var lightText: TextView
    private lateinit var pressureTextView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        println("Size = ${list.size}")
        println(list.joinToString("\n"));

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        lightText = findViewById(R.id.lightText)
        pressureTextView = findViewById(R.id.pressureText)
        imageView = findViewById(R.id.imageView)

        if(lightSensor != null) {
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_GAME)
        }

        if(pressureSensor != null) {
            sensorManager.registerListener(this,pressureSensor,SensorManager.SENSOR_DELAY_GAME)
        }

        if(gyroSensor != null) {
            sensorManager.registerListener(this,gyroSensor,SensorManager.SENSOR_DELAY_GAME)
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(lightSensor == null) {
            lightText.text = "No light sensor!"
        } else if(event!!.sensor.type == Sensor.TYPE_LIGHT) {
            lightText.text = "Light: ${event.values[0]}"
        }
        if(pressureSensor == null) {
            pressureTextView.text = "No pressure sensor!"
        } else if(event!!.sensor.type == Sensor.TYPE_PRESSURE) {
            pressureTextView.text = "Pressure: ${event.values[0]}"
        }
        if(event!!.sensor.type == Sensor.TYPE_GYROSCOPE) {
            imageView.rotation = event.values[2]*10
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}