package com.mobdeve.s11.dorde.villegas.penguinrush.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ScoreDatabase(context: Context?) :

    SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_SCORES_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLESCORES)
        onCreate(db)
    }

    companion object {
        private const val DATABASENAME = "score.db"
        private const val DATABASEVERSION = 1

        //column names
        const val TABLESCORES = "scores"
        const val SCORESUSERNAME = "username"
        const val SCORESSCORE = "score"
        private const val CREATE_SCORES_TABLE = ("create table " + TABLESCORES + " ( "
                + SCORESUSERNAME + " text, "
                + SCORESSCORE + " text ); ")
        //INTEGER, REAL, TEXT, BLOB, NULL
    }
}