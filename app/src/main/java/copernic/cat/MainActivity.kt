package copernic.cat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.findNavController


private var usuario= false


class MainActivity : AppCompatActivity() {
    //private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.perfil -> {
                //startActivity(Intent(this, recuperar_contrasenya::class.java))
                //binding.navHostFragmentContentMain.findNavController().navigate(R.id.action_inici_to_perfil)
                //findNavController().navigate(R.id.action_inici_to_perfil)
            }
        }
        return super.onOptionsItemSelected(item)
    }

     fun intentcompartir(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.perfil -> {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,"hola")
                    type= "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent,null)
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}