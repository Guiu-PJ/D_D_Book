package copernic.cat.PreInici

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AlertDialog
import copernic.cat.databinding.ActivityRecuperarContrasenyaBinding


class recuperar_contrasenya : AppCompatActivity() {
    private lateinit var binding: ActivityRecuperarContrasenyaBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContrasenyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null)
            supportActionBar!!.hide()

        auth = Firebase.auth

        binding.botoActivityLogin.setOnClickListener{
            startActivity(Intent(this, login::class.java))
            finish()
        }

        binding.btnRecuperarContrasenya.setOnClickListener{
            resetPasword()
        }

    }

    private fun resetPasword(){
        auth= FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(binding.txtEmailContrasenya.text.toString()).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                startActivity(Intent(this, login::class.java))
                //finish()
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Recuperadr contraseña a fallado")
                builder.setMessage("L'email no es correcte")
                builder.setPositiveButton("OK"){_, _ ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            }

        }
    }

}