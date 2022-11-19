package copernic.cat.PreInici

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
import copernic.cat.classes.usuaris
import copernic.cat.databinding.ActivityRecuperarContrasenyaBinding
import copernic.cat.databinding.ActivityRegistreBinding


class registre : AppCompatActivity() {
    private lateinit var binding: ActivityRegistreBinding
    private  lateinit var auth: FirebaseAuth
    private var bd = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null)
            supportActionBar!!.hide()

        auth = Firebase.auth

        binding.botoRegistre.setOnClickListener{

            val email = binding.correuRegistre.text.toString()
            val password = binding.contrasenyaRegistre.text.toString()
            val repeatPassword = binding.repetirContrasenya.text.toString()

            if (password.equals(repeatPassword) && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()){
                register(email, password)
            }

        }

        binding.botoActivityLogin.setOnClickListener {
            startActivity(Intent(this, login::class.java))
            finish()
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    val usuaris = llegirDades()

                    if(usuaris.Email.isNotEmpty()){
                        bd.collection("Usuari").document("ID Usuario").set(hashMapOf("Email" to binding.correuRegistre.text.toString(), "Admin" to false)).addOnSuccessListener {
                            Toast.makeText(applicationContext,"L'usuari s'ha afegit correctament", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener{
                            Toast.makeText(applicationContext,"L'usuari no s'ha afegit", Toast.LENGTH_LONG).show()
                        }
                    }
                    startActivity(Intent(this, login::class.java))
                    finish()
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


    private fun llegirDades(): usuaris {
        val email =binding.correuRegistre.text.toString()

        return usuaris(email, false)
    }
}