package com.mobdeve.s11.dorde.villegas.penguinrush

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GestureDetectorCompat
import org.w3c.dom.Text
import kotlin.system.exitProcess

const val PENGUIN_DEFAULT_X = 235f
const val PENGUIN_DEFAULT_Y = 970f
const val PENGUIN_LEFT_X = 0f
const val PENGUIN_RIGHT_X = 460f

const val LEFT_ICE_X = 0f
const val CENTER_ICE_X = 250f
const val RIGHT_ICE_X = 500f

const val LEFT_FISH_X = 50f
const val CENTER_FISH_X = 300f
const val RIGHT_FISH_X = 550f

const val OUTSIDE_ICE_X = 2000f
const val OUTSIDE_ICE_Y = -1000f
const val OUTSIDE_FISH_X = 2000f
const val OUTSIDE_FISH_Y = -1000f

class GameCanvas(context: Context, attrs: AttributeSet): AppCompatImageView(context, attrs) {
    private val backColor = ResourcesCompat.getColor(resources, R.color.lightblue_bg, null)
    private val recPaint = Paint().apply {
        color = ResourcesCompat.getColor(resources, R.color.sky_blue, null)
        style = Paint.Style.FILL
    }
    private val recPaint2 = Paint().apply{
        color = ResourcesCompat.getColor(resources, R.color.orange, null)
        style = Paint.Style.FILL
    }
    private val line = Paint().apply {
        isAntiAlias = true
        strokeWidth = 5f
        color = ResourcesCompat.getColor(resources, R.color.blue_line, null)
        style = Paint.Style.STROKE
    }
    private val textPaint = Paint().apply{
        textSize = 40f
        textAlign = Paint.Align.CENTER
        color = ResourcesCompat.getColor(resources, R.color.white, null)
    }
    private var penguin = BitmapFactory.decodeResource(resources, R.drawable.penguin)
    private var obstacle = BitmapFactory.decodeResource(resources, R.drawable.obstacle)
    private var fish = BitmapFactory.decodeResource(resources, R.drawable.fish)

    private var rng = 1

    private var motionX = PENGUIN_DEFAULT_X
    private var motionY = PENGUIN_DEFAULT_Y

    private var obstacleX = OUTSIDE_ICE_X
    private var obstacleY = OUTSIDE_ICE_Y
    private var obstacle2X = OUTSIDE_ICE_X
    private var obstacle2Y =  OUTSIDE_ICE_Y
    private var obstacle3X = OUTSIDE_ICE_X
    private var obstacle3Y = OUTSIDE_ICE_Y

    private var fishX = OUTSIDE_FISH_X
    private var fishY = OUTSIDE_FISH_Y
    private var fish2X = OUTSIDE_FISH_X
    private var fish2Y = OUTSIDE_FISH_Y
    private var fish3X = OUTSIDE_FISH_X
    private var fish3Y = OUTSIDE_FISH_Y

    var bundle = Bundle()
    var currentScore = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(backColor)
        canvas.drawRect(0f, 80f, 800f, 300f, recPaint)

        canvas.drawLine(240f, 300f, 240f, 1500f, line)
        canvas.drawLine(490f, 300f, 490f, 1500f, line)
        canvas.drawLine(0f, 300f, 800f, 300f, line)

        canvas.drawBitmap(obstacle, obstacleX, obstacleY, line)
        canvas.drawBitmap(obstacle, obstacle2X, obstacle2Y, line)
        canvas.drawBitmap(obstacle, obstacle3X, obstacle3Y, line)
        canvas.drawBitmap(fish, fishX, fishY, line)
        canvas.drawBitmap(fish, fish2X, fish2Y, line)
        canvas.drawBitmap(fish, fish3X, fish3Y, line)
        canvas.drawBitmap(penguin, motionX, motionY, line)

        canvas.drawRect(0f, 0f, 800f, 80f, recPaint2)
        canvas.drawText(currentScore.toString(), 365f, 55f, textPaint)
    }

    private fun fishPlacementRandomizer(){
        rng = (1..7).random()
        when (rng){
            1 -> {
                fishX = LEFT_FISH_X
                fish2X = OUTSIDE_FISH_X
                fish3X = OUTSIDE_FISH_X
            }
            2 -> {
                fishX = OUTSIDE_FISH_X
                fish2X = CENTER_FISH_X
                fish3X = OUTSIDE_FISH_X
            }
            3 -> {
                fishX = OUTSIDE_FISH_X
                fish2X = OUTSIDE_FISH_X
                fish3X = RIGHT_FISH_X
            }
            4 -> {
                fishX = LEFT_FISH_X
                fish2X = CENTER_FISH_X
                fish3X = OUTSIDE_FISH_X
            }
            5 -> {
                fishX = CENTER_FISH_X
                fish2X = RIGHT_FISH_X
                fish3X = OUTSIDE_FISH_X
            }
            6 -> {
                fishX = LEFT_FISH_X
                fish2X = RIGHT_FISH_X
                fish3X = OUTSIDE_FISH_X
            }
            7 -> {
                fishX = LEFT_FISH_X
                fish2X = CENTER_FISH_X
                fish3X = RIGHT_FISH_X
            }
        }
    }

    private fun icePlacementRandomizer(){
        rng = (1..7).random()
        when (rng) {
            1 -> {
                obstacleX = LEFT_ICE_X
                obstacle2X = OUTSIDE_ICE_X
                obstacle3X = OUTSIDE_ICE_X
            }
            2 -> {
                obstacleX = CENTER_ICE_X
                obstacle2X = OUTSIDE_ICE_X
                obstacle3X = OUTSIDE_ICE_X
            }
            3 -> {
                obstacleX = RIGHT_ICE_X
                obstacle2X = OUTSIDE_ICE_X
                obstacle3X = OUTSIDE_ICE_X
            }
            4 -> {
                obstacleX = LEFT_ICE_X
                obstacle2X = CENTER_ICE_X
                obstacle3X = OUTSIDE_ICE_X
            }
            5 -> {
                obstacleX = CENTER_ICE_X
                obstacle2X = RIGHT_ICE_X
                obstacle3X = OUTSIDE_ICE_X
            }
            6 -> {
                obstacleX = LEFT_ICE_X
                obstacle2X = RIGHT_ICE_X
                obstacle3X = OUTSIDE_ICE_X
            }
            7 -> {
                obstacleX = LEFT_ICE_X
                obstacle2X = CENTER_ICE_X
                obstacle3X = RIGHT_ICE_X
            }
        }
    }

    private fun animateFish(){
        val valueAnimator = ValueAnimator.ofFloat(280f, 1300f)

        fishPlacementRandomizer()
        invalidate()

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            fishY = value
            fish2Y = value
            fish3Y = value
            invalidate()

                if(fishY in 880f..1500f && motionY == PENGUIN_DEFAULT_Y
                    && ((fishX == LEFT_FISH_X && motionX == PENGUIN_LEFT_X)
                    || (fishX == RIGHT_FISH_X && motionX == PENGUIN_RIGHT_X)
                    || (fishX == CENTER_FISH_X && motionX == PENGUIN_DEFAULT_X))){

                    currentScore += 1

                    fishX = OUTSIDE_FISH_X
                    fishY = OUTSIDE_FISH_Y
                    invalidate()
                } else if(fish2Y in 880f..1500f && motionY == PENGUIN_DEFAULT_Y
                    && ((fish2X == CENTER_FISH_X && motionX == PENGUIN_DEFAULT_X)
                    || (fish2X == RIGHT_FISH_X && motionX == PENGUIN_RIGHT_X))){

                    currentScore += 1

                    fish2X = OUTSIDE_FISH_X
                    fish2Y = OUTSIDE_FISH_Y
                    invalidate()
                } else if(fish3Y in 880f..1500f && motionY == PENGUIN_DEFAULT_Y
                    && (fish3X == RIGHT_FISH_X && motionX == PENGUIN_RIGHT_X)){

                    currentScore += 1

                    fish3X = OUTSIDE_FISH_X
                    fish3Y = OUTSIDE_FISH_Y
                    invalidate()
                } else{
                    valueAnimator.doOnRepeat {
                        fishPlacementRandomizer()
                        invalidate()
                    }
                }
        }

        valueAnimator.startDelay = 1500
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.duration = 3000
        valueAnimator.start()
    }

    fun animateIce(){
        val valueAnimator = ValueAnimator.ofFloat(55f, 1300f)

        icePlacementRandomizer()
        invalidate()

        animateFish()

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            obstacleY = value
            obstacle2Y = value
            obstacle3Y = value
            invalidate()

            if(obstacleY in 850f..1000f && motionY == PENGUIN_DEFAULT_Y
                && ((obstacleX == CENTER_ICE_X && motionX == PENGUIN_DEFAULT_X)
                || (obstacleX == LEFT_ICE_X && motionX == PENGUIN_LEFT_X)
                || (obstacleX == RIGHT_ICE_X && motionX == PENGUIN_RIGHT_X))) {
                valueAnimator.removeAllListeners()
                valueAnimator.pause()
                goToGameOver()
            } else if(obstacle2Y in 850f..1000f && motionY == PENGUIN_DEFAULT_Y
                && ((obstacle2X == CENTER_ICE_X && motionX == PENGUIN_DEFAULT_X)
                || (obstacle2X == RIGHT_ICE_X && motionX == PENGUIN_RIGHT_X))){
                valueAnimator.removeAllListeners()
                valueAnimator.pause()
                goToGameOver()
            } else if (obstacle3Y in 850f..1000f && motionY == PENGUIN_DEFAULT_Y
                && (obstacle3X == RIGHT_ICE_X && motionX == PENGUIN_RIGHT_X)){
                valueAnimator.removeAllListeners()
                valueAnimator.pause()
                goToGameOver()
            } else{
                valueAnimator.doOnRepeat {
                    icePlacementRandomizer()
                    invalidate()
                }
            }
        }

        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.duration = 3000
        valueAnimator.start()
    }

    private fun goToGameOver(){
        val gotoGameOverActivity = Intent(context, GameOverActivity::class.java)
        gotoGameOverActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        bundle.putInt("finalscore", currentScore)
        gotoGameOverActivity.putExtras(bundle)

        context.startActivity(gotoGameOverActivity)
        exitProcess(0)
    }

    fun onSwipeUp(){
        val valueAnimator = ValueAnimator.ofFloat(PENGUIN_DEFAULT_Y, 500f, 400f, PENGUIN_DEFAULT_Y)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            motionY = value
            invalidate()
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 1000
        valueAnimator.start()
    }

    fun onTiltLeft(){
        if(motionX == PENGUIN_DEFAULT_X) {
            val valueAnimator = ValueAnimator.ofFloat(PENGUIN_DEFAULT_X, PENGUIN_LEFT_X)

            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                motionX = value
                invalidate()
            }

            valueAnimator.interpolator = AccelerateInterpolator(1f)
            valueAnimator.duration = 500
            valueAnimator.start()
        } else if(motionX == PENGUIN_RIGHT_X){
            val valueAnimator = ValueAnimator.ofFloat(PENGUIN_RIGHT_X, PENGUIN_DEFAULT_X)

            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                motionX = value
                invalidate()
            }

            valueAnimator.interpolator = AccelerateInterpolator(1f)
            valueAnimator.duration = 500
            valueAnimator.start()
        }
    }

    fun onTiltRight(){
        if(motionX == PENGUIN_DEFAULT_X) {
            val valueAnimator = ValueAnimator.ofFloat(PENGUIN_DEFAULT_X, PENGUIN_RIGHT_X)

            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                motionX = value
                invalidate()
            }

            valueAnimator.interpolator = AccelerateInterpolator(1f)
            valueAnimator.duration = 500
            valueAnimator.start()
        } else if(motionX == PENGUIN_LEFT_X) {
            val valueAnimator = ValueAnimator.ofFloat(PENGUIN_LEFT_X, PENGUIN_DEFAULT_X)

            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                motionX = value
                invalidate()
            }

            valueAnimator.interpolator = AccelerateInterpolator(1f)
            valueAnimator.duration = 500
            valueAnimator.start()
        }
    }
}