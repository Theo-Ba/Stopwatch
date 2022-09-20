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
        val STATE_TIME = "current time"
        val STATE_STOPPED = "stopped boolean"
        val STATE_BASE = "base when stopped"
    }

    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var timer: Chronometer
    private lateinit var background: ConstraintLayout
    private lateinit var settingsButton: Button
    private lateinit var timerGroup: Group
    private lateinit var settingsGroup: Group
    private lateinit var darkMode: Switch
    private lateinit var zawarudo: Switch
    private lateinit var stopTime: Button
    private var stopped = true
    private var currentTime = 0L
    private var showingTimer = true
    private var base = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        initializeWidgets()
        customizeWidgets()

        //restore instance state if it exists
        if(savedInstanceState != null) {
            currentTime = savedInstanceState.getLong(STATE_TIME)
            stopped = savedInstanceState.getBoolean(STATE_STOPPED)
            base = savedInstanceState.getLong(STATE_BASE)
            if(!stopped) {
                timer.base = SystemClock.elapsedRealtime() - currentTime
                timer.start()
                startButton.text = "STOP"
                startButton.setBackgroundColor(Color.RED)
                stopped = false
            }
            else {
                timer.base = base - currentTime + SystemClock.elapsedRealtime()
                timer.stop()
                startButton.text = "START"
                startButton.setBackgroundColor(Color.GREEN)
                stopped = true
                currentTime = SystemClock.elapsedRealtime()
            }
        }

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
                zawarudo.setTextColor(Color.WHITE)
            }
            else
            {
                startButton.setTextColor(Color.WHITE)
                resetButton.setTextColor(Color.WHITE)
                background.setBackgroundColor(Color.WHITE)
                timer.setTextColor(Color.BLACK)
                darkMode.setTextColor(Color.BLACK)
                zawarudo.setTextColor(Color.BLACK)
            }
        }

        zawarudo.setOnCheckedChangeListener { compoundButton, b ->
            stopTime.isGone = !b
        }

        stopTime.setOnClickListener {
            tokiWoTomare()
        }
    }

    // use this to preserve state through orientation changes
    override fun onSaveInstanceState(outState: Bundle) {
        //calculate the currentTime if it's currently running
        if(!stopped)
            currentTime = SystemClock.elapsedRealtime() - timer.base
        // save key-value pairs to the bundle before the superclass call
        outState.putLong(STATE_TIME, currentTime)
        outState.putBoolean(STATE_STOPPED, stopped)
        outState.putLong(STATE_BASE, base)
        super.onSaveInstanceState(outState)
    }

    private fun tokiWoTomare() {
        if(!stopped) {
            timer.stop()
            background.setBackgroundColor(Color.rgb(97,72,96))
            startButton.setTextColor(Color.rgb(97,72,96))
            resetButton.setTextColor(Color.rgb(97,72,96))
            timer.setTextColor(Color.rgb(194, 165, 193))
            startButton.setBackgroundColor(Color.rgb(59, 77, 59))
            resetButton.setBackgroundColor(Color.rgb(71, 55, 54))
            object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    startButton.setTextColor(Color.BLACK)
                    resetButton.setTextColor(Color.BLACK)
                    background.setBackgroundColor(Color.BLACK)
                    timer.setTextColor(Color.WHITE)
                    darkMode.setTextColor(Color.WHITE)
                    startButton.setBackgroundColor(Color.GREEN)
                    resetButton.setBackgroundColor(Color.rgb(115, 11, 0))
                    timer.start()
                }
            }.start()
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
        stopTime.isGone = true
        stopTime.text = "Toki wo Tomare"
        zawarudo.text = "Za Warudo"
        stopTime.setBackgroundColor(Color.YELLOW)
        stopTime.setTextColor(Color.BLACK)
        zawarudo.isChecked = false
        zawarudo.setTextColor(Color.WHITE)
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
            base = timer.base
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
        zawarudo = findViewById(R.id.switch_main_Zawarudo)
        stopTime = findViewById(R.id.button_main_stopTime)
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