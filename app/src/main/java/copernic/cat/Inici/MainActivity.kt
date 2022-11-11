package copernic.cat.Inici

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import copernic.cat.PreInici.login
import copernic.cat.R
import copernic.cat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var appBarConfiguration = AppBarConfiguration(
            setOf(R.id.inici, R.id.novedades),
            binding.drawerLayout
        )
        setupActionBarWithNavController(NavController(this), appBarConfiguration)
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.inici -> {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.novedades -> {
                    //findNavController().navigate(R.id.action_inici_to_dados)
                    true
                }
                R.id.cerrarsesion -> {
                    FirebaseAuth.getInstance().signOut()
                    //startActivity(Intent(this, login::class.java))
                    var intent = Intent(this, login::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> super.onOptionsItemSelected(it)

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.perfil -> {Toast.makeText(this,"hola", Toast.LENGTH_SHORT).show()}
            R.id.cerrarsesion -> {
                FirebaseAuth.getInstance().signOut()
                //startActivity(Intent(this, login::class.java))
                var intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()}
        }

        return super.onOptionsItemSelected(item)
    }


}