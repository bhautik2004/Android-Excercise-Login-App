package com.example.loginexcerciseapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : AppCompatActivity() {
    val sharedPreferences = "loginsharedpref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val sharedPreferences = getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)

        val successmessage = findViewById<TextView>(R.id.successmsg)

        val msg = "SuccessFully Logged in,\nYour Email = $email"
        successmessage.setText(msg)
        logoutbtn.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}