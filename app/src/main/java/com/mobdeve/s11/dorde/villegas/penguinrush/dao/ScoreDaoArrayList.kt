package com.mobdeve.s11.dorde.villegas.penguinrush.dao

import com.mobdeve.s11.dorde.villegas.penguinrush.model.Score


class ScoreDaoArrayList:ScoreDAO {

    private var scoreSaveList = ArrayList<Score?>()

    init{
//        scoreSaveList.add(Score("angel_dorde", "21"))
//        scoreSaveList.add(Score("jade_villegas", "20"))
//        scoreSaveList.add(Score("zoe_avila", "14"))
//        scoreSaveList.add(Score("lovely_dorde", "13"))
//        scoreSaveList.add(Score("sam_ty", "8"))
//        scoreSaveList.add(Score("czarina_tiu", "7"))
//        scoreSaveList.add(Score("jared_anigan", "6"))
//        scoreSaveList.add(Score("lui_quiros", "5"))
//        scoreSaveList.add(Score("allona_fabre", "3")) //
    //        scoreSaveList.add(Score("paolo_cervantes", "2"))
    }

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