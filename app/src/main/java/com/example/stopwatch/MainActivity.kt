package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.Switch
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isGone
import androidx.core.view.isVisible
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
    private lateinit var settingsButton: Button
    private lateinit var timerGroup: Group
    private lateinit var settingsGroup: Group
    private lateinit var darkMode: Switch
    private var stopped = true
    private var currentTime = 0L
    private var showingTimer = true

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

        settingsButton.setOnClickListener {
            settings()
        }

        darkMode.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                startButton.setTextColor(Color.BLACK)
                resetButton.setTextColor(Color.BLACK)
                background.setBackgroundColor(Color.BLACK)
                timer.setTextColor(Color.WHITE)
                darkMode.setTextColor(Color.WHITE)
            }
            else
            {
                startButton.setTextColor(Color.WHITE)
                resetButton.setTextColor(Color.WHITE)
                background.setBackgroundColor(Color.WHITE)
                timer.setTextColor(Color.BLACK)
                darkMode.setTextColor(Color.BLACK)
            }
        }
    }

    private fun settings() {
        if(showingTimer) {
            timerGroup.isGone = true
            settingsGroup.isGone = false
            showingTimer = false
        }
        else {
            timerGroup.isGone = false
            settingsGroup.isGone = true
            showingTimer = true
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
        settingsButton.text = ""
        settingsButton.setBackgroundColor(Color.GRAY)
        darkMode.toggle()
        darkMode.setTextColor(Color.WHITE)
        darkMode.text = "Dark Mode"
        settingsGroup.isGone = true
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
        settingsButton = findViewById(R.id.button_main_settings)
        timerGroup = findViewById(R.id.group_main_showTimer)
        settingsGroup = findViewById(R.id.group_main_settings)
        darkMode = findViewById(R.id.switch_main_darkMode)
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