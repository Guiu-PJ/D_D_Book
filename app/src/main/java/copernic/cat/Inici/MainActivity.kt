package copernic.cat.Inici

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import copernic.cat.PreInici.login
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

    /*private fun remplaceFragment(perfil : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,perfil)
        fragmentTransaction.commit()
    }*/
}