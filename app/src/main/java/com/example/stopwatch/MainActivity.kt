package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import kotlinx.coroutines.NonCancellable.start

class MainActivity : AppCompatActivity() {

    //make a classwide static constant in Kotlin
    companion object {
        //all your static constants go here
        val TAG = "MainActivity"
    }

    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var timer: Chronometer
    private var stopped = true
    private var currentTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        initializeWidgets()
        currentTime = timer.base
        startButton.setOnClickListener {
            startTimer()
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun resetTimer() {

    }

    private fun startTimer() {
        if(stopped) {
            timer.base = currentTime
            timer.start()
            startButton.text = "STOP"
            stopped = false
        }
        else {
            startButton.text = "START"
            timer.stop()
            stopped = true
            currentTime = timer.base
        }
    }

    private fun initializeWidgets() {
        startButton = findViewById(R.id.button_main_start)
        resetButton = findViewById(R.id.button_main_reset)
        timer = findViewById(R.id.chronometer_main_stopwatch)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}