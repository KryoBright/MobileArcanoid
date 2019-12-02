package com.example.mobilearcanoid

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.app.Service


class PlatformController (pl:ViewPlusClass,smg:SensorManager?):SensorEventListener{

    private var mSensorManager : SensorManager?= smg
    private var mGyro : Sensor ?= null
    var platform=pl

    init {
        mGyro = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        mSensorManager!!.registerListener(this,mGyro,
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (ScoreKeeper.playing) {
            if (p0 != null) {
                platform.xspeed += p0.values[1]
                platform.yspeed += p0.values[0]
                platform.stepChange {
                    if (platform.y < (platform.maxy - 120)) {
                        platform.y = (platform.maxy - 120).toFloat()
                        platform.yspeed = 0F
                    }
                    if (platform.y > platform.maxy) {
                        platform.y = (platform.maxy).toFloat()
                        platform.yspeed = 0F
                    }
                    if (platform.yspeed > 20) {
                        platform.yspeed = 20F
                    }

                    if (platform.xspeed > 40) {
                        platform.xspeed = 40F
                    }
                    if (platform.yspeed < -20) {
                        platform.yspeed = -20F
                    }

                    if (platform.xspeed < -40) {
                        platform.xspeed = -40F
                    }
                    if (platform.yspeed > 0) {
                        platform.yspeed -= 0.2F
                    } else {
                        platform.yspeed += 0.2F
                    }

                    if (platform.xspeed > 0) {
                        platform.xspeed -= 0.2F
                    } else {
                        platform.xspeed += 0.2F
                    }
                }
            }
        }
    }

}