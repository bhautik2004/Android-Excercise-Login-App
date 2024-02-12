package com.example.loginexcerciseapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val sharedPreferences = "loginsharedpref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("email", null)
        if (useremail != null) {
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
        val db = openOrCreateDatabase("my_db", Context.MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS user (fname VARCHAR, lname VARCHAR, email VARCHAR, password VARCHAR);")
        arbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val fname = findViewById<EditText>(R.id.fname)
        val lname = findViewById<EditText>(R.id.lname)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        rbtn.setOnClickListener {

            val fname = fname.text.toString()
            val lname = lname.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()

            if (isvalid(fname, lname, email, password)) {
                db.execSQL("INSERT INTO user VALUES('$fname','$lname','$email','$password')")
                ShowMessage("Success", "Successfully Register Click For Login..")
                CleartEditext()
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

    private fun isvalid(fname: String, lname: String, email: String, password: String): Boolean {
        if (fname.isEmpty()) {
            ShowMessage("Error", "Please Enter First Name")
            return false
        } else if (lname.isEmpty()) {
            ShowMessage("Error", "Please Enter Last Name")
            return false
        } else if (email.isEmpty()) {
            ShowMessage("Error", "Please Enter Email")
            return false
        } else if (password.isEmpty()) {
            ShowMessage("Error", "Please Enter Password")
            return false
        }
        return true
    }

    fun CleartEditext() {
        fname.setText("")
        lname.setText("")
        email.setText("")
        password.setText("")
    }
}