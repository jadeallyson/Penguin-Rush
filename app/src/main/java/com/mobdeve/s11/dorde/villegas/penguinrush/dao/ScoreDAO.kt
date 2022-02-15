package com.mobdeve.s11.dorde.villegas.penguinrush.dao

import com.mobdeve.s11.dorde.villegas.penguinrush.model.Score


interface ScoreDAO {
    fun addScore(score: Score?):Long
    fun getScores():ArrayList<Score?>?
    fun getScore(scoreId: Int): Score?
    fun updateScore(score: Score?):Int
    fun deleteScore(scoreId: Int):Int

}