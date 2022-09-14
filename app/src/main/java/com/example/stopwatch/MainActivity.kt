package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import androidx.constraintlayout.widget.ConstraintLayout
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
    private lateinit var background: ConstraintLayout
    private var stopped = true
    private var currentTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        initializeWidgets()
        customizeWidgets()

        startButton.setOnClickListener {
            startTimer()
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun customizeWidgets() {
        currentTime = timer.base
        startButton.setBackgroundColor(Color.GREEN)
        startButton.setTextColor(Color.BLACK)
        resetButton.setBackgroundColor(Color.rgb(115, 11, 0))
        resetButton.setTextColor(Color.BLACK)
        background.setBackgroundColor(Color.BLACK)
        timer.setTextColor(Color.WHITE)
    }

    private fun resetTimer() {
        timer.base = SystemClock.elapsedRealtime()
    }

    private fun startTimer() {
        if(stopped) {
            timer.base = timer.base + (SystemClock.elapsedRealtime() - currentTime)
            timer.start()
            startButton.text = "STOP"
            startButton.setBackgroundColor(Color.RED)
            stopped = false
        }
        else {
            startButton.text = "START"
            startButton.setBackgroundColor(Color.GREEN)
            timer.stop()
            stopped = true
            currentTime = SystemClock.elapsedRealtime()
        }
    }

    private fun initializeWidgets() {
        startButton = findViewById(R.id.button_main_start)
        resetButton = findViewById(R.id.button_main_reset)
        timer = findViewById(R.id.chronometer_main_stopwatch)
        background = findViewById(R.id.constraintLayout_main_background)
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