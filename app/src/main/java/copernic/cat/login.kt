package copernic.cat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (supportActionBar != null)
            supportActionBar!!.hide()
    }
}