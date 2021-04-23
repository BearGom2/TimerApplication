package com.example.timerapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFiveMinBtn.setOnClickListener {
            reload(30000)
        }
        addMinBtn.setOnClickListener {
            reload(6000)
        }
        addSecBtn.setOnClickListener {
            reload(3000)
        }

        startBtn.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) {
                start()
                startBtn.text = "중지하기"
            } else {
                pause()
                startBtn.text = "시작하기"
            }
        }

        stopBtn.setOnClickListener {
            reset()
        }

    }

    private fun start() {
        if (time > 0) {
            timerTask = fixedRateTimer(period = 10) {
                time--
                var sec = time / 100
                val mil = time % 100
                var min: Int = sec / 60
                runOnUiThread {
                    setText(min, sec, mil)
                }
            }
        }

    }

    private fun reload(t: Int) {
        time += t
        var sec = time / 100
        val mil = time % 100
        var min: Int = sec / 60
        runOnUiThread {
            setText(min, sec, mil)
        }
    }

    private fun pause() {
        timerTask?.cancel()
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        minTv.text = "00"
        secTv.text = ":00"
        milTv.text = ".00"
        startBtn.text = "시작하기"
    }

    private fun setText(min: Int, sec: Int, mil: Int) {
        minTv.text = "$min"
        secTv.text = ":${sec % 60}"
        milTv.text = ".$mil"
        if (sec <= 0) {
            pause()
            reset()
        }
        if (min < 10) {
            minTv.text = "0$min"
        }
        if ((sec % 60) < 10) {
            secTv.text = ":0${sec % 60}"
        }
        if (mil < 10) {
            milTv.text = ".0$mil"
        }
    }

}