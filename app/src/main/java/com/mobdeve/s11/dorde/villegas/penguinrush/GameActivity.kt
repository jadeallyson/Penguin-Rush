package com.mobdeve.s11.dorde.villegas.penguinrush

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import com.mobdeve.s11.dorde.villegas.penguinrush.databinding.ActivityGameBinding
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameCanvas: GameCanvas
    private var windowHeight = 0f
    private var windowWidth = 0f

    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private var paint: Paint? = null

    private lateinit var detector: GestureDetectorCompat
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        gameCanvas = findViewById<GameCanvas>(R.id.game_canvas)

        val currentDisplay = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(currentDisplay)
        windowHeight = (currentDisplay.heightPixels * 2).toFloat()
        windowWidth = (currentDisplay.widthPixels * 2).toFloat()

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display2 = windowManager.defaultDisplay
        val outPoint = Point()

        display2.getSize(outPoint)

        var width = 0
        var height = 0

        if(outPoint.y > outPoint.x){
            height = outPoint.y
            width = outPoint.x
        } else{
            height = outPoint.x
            width = outPoint.y
        }

        bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
        canvas = Canvas(bitmap!!)
        paint = Paint()

        detector = GestureDetectorCompat(this, SwipeDetector())
        setUpSensor()

        gameCanvas.animateIce()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitProcess(0)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if(detector.onTouchEvent(event)){
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    inner class SwipeDetector() : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            downEvent: MotionEvent?,
            moveEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            val diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0f
            val diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0f

            //swipe up or down
            return if (Math.abs(diffX) < Math.abs(diffY)) {
                //swipe bottom to top
                if (Math.abs(diffY) > 100 && Math.abs(velocityY) > 100) {
                    if (diffY < 0) {
                        gameCanvas.onSwipeUp()
                        true
                    } else {
                        super.onFling(downEvent, moveEvent, velocityX, velocityY)
                    }
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            } else {
                super.onFling(downEvent, moveEvent, velocityX, velocityY)
            }
        }
    }

    private fun setUpSensor(){
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also{
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val sides = event.values[0]
            when {
                sides < -3f -> gameCanvas.onTiltRight()
                sides > 3f -> gameCanvas.onTiltLeft()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}