package com.example.loginexcerciseapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val sharedPreferences = "loginsharedpref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val sharedPreferences = getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("email", null)
        if (useremail != null) {
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
        val db = openOrCreateDatabase("my_db", Context.MODE_PRIVATE, null)

        val lemail = findViewById<EditText>(R.id.email)
        val lpassword = findViewById<EditText>(R.id.password)
        lbtn.setOnClickListener {
            val email = lemail.text.toString()
            val password = lpassword.text.toString()
            if (isvalid(email, password)) {
                val cursor = db.rawQuery(
                    "SELECT * FROM user WHERE email='$email' AND password='$password'",
                    null
                )
                if (cursor.moveToFirst()) {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("email", email)
                    editor.apply()
                    val intent = Intent(this, SuccessActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Email or Password Is Wrong", Toast.LENGTH_LONG).show()
                }

            }

        }


    }

    private fun ShowMessage(title: String, msg: String) {
        val b = AlertDialog.Builder(this)
        b.setTitle(title)
        b.setMessage(msg)
        b.setPositiveButton("Ok") { _, _ ->

        }
        b.show()

    }

    private fun isvalid(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            ShowMessage("Error", "Please Enter Email")
            return false
        } else if (password.isEmpty()) {
            ShowMessage("Error", "Please Enter Password")
            return false
        }
        return true
    }
}