package copernic.cat.Inici

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
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
                R.id.novedades -> {

                    //replaceFragment(fragment, R.id.container)
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
                val intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()}
        }

        return super.onOptionsItemSelected(item)
    }
private fun fragmentTransition(fragment: Fragment){
    //supportFragmentManager.beginTransaction().remplace(R.id.novedades)
}
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }
    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{replace(frameId, fragment)}
    }

}