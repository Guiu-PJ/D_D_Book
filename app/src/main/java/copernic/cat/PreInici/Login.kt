package copernic.cat.PreInici

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AlertDialog
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.databinding.ActivityLoginBinding
import copernic.cat.databinding.ActivityRecuperarContrasenyaBinding

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null)
            supportActionBar!!.hide()

        auth = Firebase.auth



        binding.botoActivityRegistre.setOnClickListener {
            startActivity(Intent(this, registre::class.java))
            //finish()
        }

        binding.botoActivityRecuperarContrasena.setOnClickListener{
            startActivity(Intent(this, recuperar_contrasenya::class.java))
            //finish()
        }




        binding.botoLogin.setOnClickListener{
            val email = binding.correuLogin.text.toString()
            val password = binding.contrasenyaLogin.text.toString()

            if(checkEmpty(email, password)){
                login(email, password)
            }else{
                if(email.isNotEmpty()&&password.isEmpty()){
                    Toast.makeText(applicationContext,"La contraseña no puede estar vacia",Toast.LENGTH_LONG).show()
                }else if(email.isEmpty()&&password.isNotEmpty()){
                    Toast.makeText(applicationContext,"El usuario no puede estar vacio",Toast.LENGTH_LONG).show()
                }else if (email.isEmpty()&&password.isEmpty()){
                    Toast.makeText(applicationContext,"El usuario y la contraseña no pueden estar vacios",Toast.LENGTH_LONG).show()
                }
            }
        }

    }



    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    //finish()
                }else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Login failed")
                    builder.setMessage("L'email o la contrasenya no son correctes")
                    builder.setPositiveButton("OK"){_, _ ->
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(true)
                    alertDialog.show()

                }
            }
    }

    private fun checkEmpty(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}