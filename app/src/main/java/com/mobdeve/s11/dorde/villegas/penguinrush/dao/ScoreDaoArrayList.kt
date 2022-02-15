package com.mobdeve.s11.dorde.villegas.penguinrush.dao

import com.mobdeve.s11.dorde.villegas.penguinrush.model.Score


class ScoreDaoArrayList:ScoreDAO {

    private var scoreSaveList = ArrayList<Score?>()

    override fun addScore(score: Score?): Long {
        scoreSaveList.add(score!!)
        return 1L
    }

    override fun getScores(): ArrayList<Score?>?  = scoreSaveList

    override fun getScore(scoreId: Int): Score? {
        TODO("Not yet implemented")
    }

    override fun updateScore(score: Score?): Int {
        TODO("Not yet implemented")
    }

    override fun deleteScore(scoreId: Int): Int {
        TODO("Not yet implemented")
    }
}