package copernic.cat.PreInici

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AlertDialog
import copernic.cat.R
import copernic.cat.databinding.ActivityRecuperarContrasenyaBinding


class recuperar_contrasenya : AppCompatActivity() {
    private lateinit var binding: ActivityRecuperarContrasenyaBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    /**
     * En el método onCreate, se infla el layout correspondiente
     * y se establece el listener para el boton de recuperar contraseña.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContrasenyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Ocultamos la action bar
         */
        if (supportActionBar != null)
            supportActionBar!!.hide()

        auth = Firebase.auth

        binding.botoActivityLogin.setOnClickListener{
            startActivity(Intent(this, login::class.java))
            finish()
        }

        binding.btnRecuperarContrasenya.setOnClickListener{
            val email = binding.txtEmailContrasenya.text.toString()
            if(email.isNotEmpty()){
                resetPasword()
            }else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.recuperar_contraseña_fallido))
                builder.setMessage(getString(R.string.el_campo_no_puede_estar_vacio))
                builder.setPositiveButton("OK") { _, _ ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            }
        }

    }

    /**
     * Envia el correo para recuperar la contraseña
     */
    fun resetPasword(){
        auth= FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(binding.txtEmailContrasenya.text.toString()).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                startActivity(Intent(this, login::class.java))
                //finish()
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.recuperar_contrasena_fallido))
                builder.setMessage(getString(R.string.correo_incorrecto))
                builder.setPositiveButton("OK"){_, _ ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            }

        }
    }
}