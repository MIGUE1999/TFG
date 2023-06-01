package com.padeltournaments.util

import android.content.Context
import android.content.SharedPreferences
class LoginPref {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATEMODE : Int = 0
    constructor(con: Context){
        this.con = con
        pref = con.getSharedPreferences(PREF_NAME, PRIVATEMODE)
        editor = pref.edit()
    }
    companion object{
        val KEY_ID = "id"
        val PREF_NAME = "Login_Preference"
        val IS_LOGIN = "isLogged"
        val KEY_USERNAME = "username"
        val KEY_EMAIL = "email"
        val KEY_ROL = "rol"
        val KEY_ORG_ID = "organizerId"
        val KEY_JUG_ID = "playerId"
    }
    fun createLoginSession(id:Int, username:String, email:String, rol : String, rolId:String){
        editor.putString(KEY_ID, id.toString())
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_ROL, rol)
        if(rol == Rol.organizer)
            editor.putString(KEY_ORG_ID, rolId)
        else editor.putString(KEY_JUG_ID, rolId)
        editor.commit()
    }
    fun getUserDetails(): Map<String, String> {
        var user : Map<String,String> = HashMap<String, String>()
        pref.getString(KEY_ID,null)?.let { (user as HashMap).put(KEY_ID, it) }
        pref.getString(KEY_USERNAME,null)?.let { (user as HashMap).put(KEY_USERNAME, it) }
        pref.getString(KEY_EMAIL, null)?.let { (user as HashMap).put(KEY_EMAIL, it) }
        pref.getString(KEY_ROL, null)?.let { (user as HashMap).put(KEY_ROL, it) }
        pref.getString(KEY_ORG_ID, null)?.let { (user as HashMap).put(KEY_ORG_ID, it) }
        pref.getString(KEY_JUG_ID, null)?.let { (user as HashMap).put(KEY_JUG_ID, it) }

        return user
    }
    fun logoutUser(){
        editor.clear()
        editor.commit()
        //loginViewModel._userLogged.value=null
    }
    fun isLoggedIn():Boolean{
        return pref.getBoolean(IS_LOGIN, false)
    }
}