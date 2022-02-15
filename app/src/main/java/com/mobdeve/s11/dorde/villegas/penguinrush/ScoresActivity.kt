package com.mobdeve.s11.dorde.villegas.penguinrush

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s11.dorde.villegas.penguinrush.dao.ScoreDAO
import com.mobdeve.s11.dorde.villegas.penguinrush.dao.ScoreDAODatabase
import com.mobdeve.s11.dorde.villegas.penguinrush.databinding.ActivityScoresBinding
import com.mobdeve.s11.dorde.villegas.penguinrush.model.Score

class ScoresActivity : AppCompatActivity() {

    var binding: ActivityScoresBinding? = null
    var scoreAdapter: ScoresAdapter? = null
    var scoreList: ArrayList<Score?> = ArrayList<Score?>()
    lateinit var scoreDAO: ScoreDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        //DAO database
        scoreDAO = ScoreDAODatabase(applicationContext)

        binding = ActivityScoresBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        populateList()
        scoreAdapter = ScoresAdapter(applicationContext, scoreList!!)

        binding!!.scorelist.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,
            false)

        binding!!.scorelist.adapter = scoreAdapter

        binding!!.btnBack.setOnClickListener {
            val gotoHomeActivity = Intent(applicationContext, HomeActivity::class.java)

            startActivity(gotoHomeActivity)
            finish()
        }
    }

    override fun onResume(){
        super.onResume()
        populateList()
    }

    fun populateList(){
        scoreList = scoreDAO.getScores()!!
    }
}