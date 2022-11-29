package copernic.cat.Inici

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import copernic.cat.Perfil.editar_perfil
import copernic.cat.Perfil.estadisticas
import copernic.cat.Perfil.partidas
import copernic.cat.Perfil.perfil
import copernic.cat.PreInici.login
import copernic.cat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupActionBarWithNavController(NavController(this))
        binding.navView.setNavigationItemSelectedListener {
            //val fragmentManager: fragmentManager = supportFragmentManager

            when(it.itemId) {
                copernic.cat.R.id.inici -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                copernic.cat.R.id.estadisticas -> {
                    val newFragment = estadisticas()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(copernic.cat.R.id.container_main, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                copernic.cat.R.id.perfil -> {
                    cambiarPantalla(perfil())
                    true
                }
                copernic.cat.R.id.editar_perfil -> {
                    val newFragment = editar_perfil()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(copernic.cat.R.id.container_main, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                copernic.cat.R.id.partidas -> {
                    val newFragment = partidas()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(copernic.cat.R.id.container_main, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                copernic.cat.R.id.cerrarsesion -> {
                    FirebaseAuth.getInstance().signOut()
                    //startActivity(Intent(this, login::class.java))
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
    }

    fun cambiarPantalla(fragment: Fragment){
        val newFragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(copernic.cat.R.id.container_main, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}