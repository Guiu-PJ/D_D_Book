package copernic.cat.Inici

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import copernic.cat.R
import copernic.cat.databinding.ActivityMainBinding
import copernic.cat.databinding.ActivityRecuperarContrasenyaBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /*private fun remplaceFragment(perfil : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,perfil)
        fragmentTransaction.commit()
    }*/
}