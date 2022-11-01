package copernic.cat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class recuperar_contrasenya : AppCompatActivity() {

    private lateinit var botoLogin: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenya)
        if (supportActionBar != null)
            supportActionBar!!.hide()

        botoLogin = findViewById(R.id.botoActivityLogin)

        botoLogin.setOnClickListener{
            startActivity(Intent(this, login::class.java))
            finish()
        }

    }





}