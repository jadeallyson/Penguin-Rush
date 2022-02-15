package com.mobdeve.s11.dorde.villegas.penguinrush.util

import android.content.Context
import android.content.SharedPreferences


class SharedPrefUtility {

    private var appPreferences: SharedPreferences? = null
    private val PREFS = "appPreferences" // file to be used

    constructor(context: Context){
        appPreferences  = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun saveStringPreferences(key: String?, value:String?){
        val prefsEditor = appPreferences!!.edit()
        prefsEditor.putString(key, value).commit()//CALL THIS TO SAVE DATA
    }

    fun getStringPreferences(key: String?): String?
            = appPreferences!!.getString(key, "Nothing Saved")

    fun removeStringPreferences(key: String?){
        val prefsEditor = appPreferences!!.edit()
        prefsEditor.remove(key).commit()
    }

    fun removeAllPreferences(){
        appPreferences!!.edit().clear().commit()
    }


}