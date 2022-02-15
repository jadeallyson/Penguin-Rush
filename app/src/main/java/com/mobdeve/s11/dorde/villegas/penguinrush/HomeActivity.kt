package com.mobdeve.s11.dorde.villegas.penguinrush

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mobdeve.s11.dorde.villegas.penguinrush.databinding.ActivityHomeBinding
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var btnPlay: Button? = null
    private var btnHighScores: Button? = null
    private var btnExit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        btnPlay = findViewById(R.id.btn_play)
        btnHighScores = findViewById(R.id.btn_highscores)
        btnExit = findViewById(R.id.btn_exit)

        btnPlay!!.setOnClickListener {
            val gotoGameActivity = Intent(applicationContext, GameActivity::class.java)

            startActivity(gotoGameActivity)
            finish()
        }

        binding!!.btnHighscores.setOnClickListener {
            val gotoScoresActivity = Intent(applicationContext, ScoresActivity::class.java)

            startActivity(gotoScoresActivity)
        }

        btnExit!!.setOnClickListener {
            finishAffinity()
        }
    }
}