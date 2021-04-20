package com.example.timerapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFiveMinBtn.setOnClickListener {
            time += 30000
            reload()
        }
        addMinBtn.setOnClickListener {
            time += 6000
            reload()
        }
        addSecBtn.setOnClickListener {
            time += 3000
            reload()
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
            timerTask = timer(period = 10) {
                time--
                var sec = time / 100
                val mil = time % 100
                var min: Int = sec / 60
                runOnUiThread {
                    minTv.text = "$min"
                    secTv.text = ":${sec % 60}"
                    milTv.text = ".$mil"
                }
            }
        }

    }

    private fun reload() {
        var sec = time / 100
        val mil = time % 100
        var min: Int = sec / 60
        runOnUiThread {
            minTv.text = "$min"
            secTv.text = ":${sec % 60}"
            milTv.text = ".$mil"
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
        secTv.text = "00"
        milTv.text = "00"
        startBtn.text = "시작하기"
        Log.i("reset", "reset완료")
    }

}