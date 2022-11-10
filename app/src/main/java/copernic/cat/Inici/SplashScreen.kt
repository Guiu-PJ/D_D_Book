package copernic.cat.Inici


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import copernic.cat.PreInici.login
import copernic.cat.R
import copernic.cat.databinding.ActivityMainBinding
import copernic.cat.databinding.ActivitySplashScreenBinding
import java.util.*
import kotlin.concurrent.schedule


class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
        var timer = Timer()
        override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState)
            if (supportActionBar != null)
                supportActionBar!!.hide()
            binding = ActivitySplashScreenBinding.inflate(layoutInflater)
            setContentView(binding.root)


            timer.schedule(1000){
                var intent = Intent(this@SplashScreen, login::class.java)
                startActivity(intent)
                finish()
            }
        }
}



