package com.androidnative.coroutineone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidnative.canvas.Battery
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val channel = Channel<Battery>()

        // button 1 click
        button1.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (battery_id3.getLevel() == 0f) {
                    launchBatteryLoadingJob(battery_id3).join()
                    channel.send(battery_id3)

                }
            }
        }

        // button 2 click
        button2.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (battery_id2.getLevel() == 0f) {
                    launchBatteryLoadingJob(battery_id2).join()
                    channel.send(battery_id2)
                }
            }
        }

        // channel queue
        CoroutineScope(Dispatchers.Main).launch {
            for (battery in channel) {
                launchBatteryUnloadingJob(battery).join()
            }
        }

    }

    /**
     *  SUSPENDING FUNCTIONS
     */

    // LITTLE BATTERIES LOADING PROCESS
    private suspend fun launchBatteryLoadingJob(battery: Battery): Job = CoroutineScope(Dispatchers.Main).launch {
        for (i in 0..100) {
            batteryReloadMovie(i, battery)
        }
    }

    // LITTLE BATTERIES UNLOADING PROCESS
    private suspend fun launchBatteryUnloadingJob(battery: Battery): Job = CoroutineScope(Dispatchers.Main).launch {
        launchBigOneLoadingJob()

        for (i in 100 downTo 0) {
            batteryReloadMovie(i, battery)
        }
    }

    // BIG ONE LOADING PROCESS
    private suspend fun launchBigOneLoadingJob(): Job = CoroutineScope(Dispatchers.Main).launch {
        val value = battery_id.getLevel().toInt()

        if (value != 100) {
            for (i in 0..100) {
                batteryReloadMovie(i / 5 + value, battery_id)
            }
        }
    }

    /**
     *  GENERAL MOVIE
     */

    // BATTERY MOVIE
    private suspend fun batteryReloadMovie(counter: Int, battery: Battery){
        delay(50)
        battery.defineLevel(counter.toFloat())
    }



}
