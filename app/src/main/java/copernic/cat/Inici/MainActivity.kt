package copernic.cat.Inici

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import copernic.cat.Informacio.novedades
import copernic.cat.Perfil.estadisticas
import copernic.cat.Perfil.partidas
import copernic.cat.Perfil.perfil
import copernic.cat.PreInici.login
import copernic.cat.R
import copernic.cat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.inici, R.id.novedades),
            binding.drawerLayout
        )
        setupActionBarWithNavController(NavController(this), appBarConfiguration)

        binding.navView.setNavigationItemSelectedListener {
            //val fragmentManager: fragmentManager = supportFragmentManager

            when(it.itemId) {
                R.id.inici -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.estadisticas -> {
                    val newFragment = estadisticas()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container_main, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                R.id.perfil -> {
                    val newFragment = perfil()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container_main, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                R.id.partidas -> {
                    val newFragment = partidas()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container_main, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                R.id.cerrarsesion -> {
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
}