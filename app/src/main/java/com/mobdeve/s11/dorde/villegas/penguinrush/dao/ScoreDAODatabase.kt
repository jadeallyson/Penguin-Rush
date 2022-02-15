package com.mobdeve.s11.dorde.villegas.penguinrush.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.mobdeve.s11.dorde.villegas.penguinrush.model.Score

class ScoreDAODatabase : ScoreDAO {
    private var scoreDatabase: ScoreDatabase? = null
    private var database: SQLiteDatabase? = null

    constructor(context: Context?){
        scoreDatabase = ScoreDatabase(context)
    }

    override fun addScore(score: Score?): Long {
        val values = ContentValues()

        values.put(ScoreDatabase.SCORESUSERNAME, score!!.username)
        values.put(ScoreDatabase.SCORESSCORE, score!!.score)

        database = scoreDatabase!!.writableDatabase

        val id: Long = database!!.insert(
            ScoreDatabase.TABLESCORES,
            null,
            values
        )

        if(database != null){
            scoreDatabase!!.close()
        }

        return id
    }



    override fun getScores(): ArrayList<Score?>? {
       val result = ArrayList<Score?>()
        val columns = arrayOf<String>(
            ScoreDatabase.SCORESUSERNAME,
            ScoreDatabase.SCORESSCORE
        )

        //readable only
        database = scoreDatabase!!.readableDatabase


        var cursor = database!!.query(
            ScoreDatabase.TABLESCORES,
            columns,
            null,
            null,
            null,
            null,
            "CAST(" + ScoreDatabase.SCORESSCORE + " as integer) DESC ",
            "10"
        )

        cursor!!.moveToFirst()

        while(!cursor!!.isAfterLast){
            val temp =  Score()
            temp.username = cursor!!.getString(0)
            temp.score = cursor!!.getInt(1)
            result.add(temp)
            cursor!!.moveToNext()
        }

        if(cursor != null){
            cursor.close()
        }

        if (database != null){
            scoreDatabase!!.close()
        }

        return result

    }

    override fun getScore(scoreId: Int): Score? {
        var score: Score? = null
        val columns = arrayOf<String>(
            ScoreDatabase.SCORESUSERNAME,
            ScoreDatabase.SCORESSCORE
        )

        val query = "SELECT" + ScoreDatabase.SCORESUSERNAME.toString() + " , " +
                ScoreDatabase.SCORESUSERNAME.toString() +
                " from " + ScoreDatabase.TABLESCORES.toString() +
                " where " + ScoreDatabase.SCORESUSERNAME.toString() + " = " + scoreId

        var cursor: Cursor?

        database = scoreDatabase!!.readableDatabase

        try{
            cursor = database!!.rawQuery(query, null)
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                score = Score()
                score.username = cursor.getString(0)
                score.score = cursor.getInt(1)
                cursor.moveToNext()
            }
        }catch(e: SQLiteException){
            return null
        }

        cursor?.close()

        if (database != null) {
            scoreDatabase!!.close()
        }

        return score

    }



    override fun updateScore(score: Score?): Int {
       val values = ContentValues()

        values.put(ScoreDatabase.SCORESUSERNAME, score!!.username)
        values.put(ScoreDatabase.SCORESSCORE, score!!.score)

        database = scoreDatabase!!.writableDatabase

        val records = database!!.update(
            ScoreDatabase.TABLESCORES,
            values,
            ScoreDatabase.SCORESUSERNAME.toString() + " = " + score!!.username,
            null
        )

        if(database != null){
            scoreDatabase!!.close()
        }

        return records
    }



    override fun deleteScore(scoreId: Int): Int {
        TODO("Not yet implemented")
    }


}