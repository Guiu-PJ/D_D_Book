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
                        val user = auth.currentUser

                        bd.collection("Usuari").document(user!!.uid).set(hashMapOf("Email" to binding.correuRegistre.text.toString(), "Admin" to false))
                        bd.collection("Usuari").document(user.uid).collection("Estadisticas")//Col.lecció
                          .document("IdEstadisticas").set(hashMapOf("numero_tiradas" to "0", "criticos" to "0", "numero_de_personajes" to "0", "numero_de_partidas" to "0", "clase_mas_jugada" to "ninguna", "personaje_mas_jugado" to "ninguno"))
                        bd.collection("Usuari").document(user.uid).collection("Partidas").document("Plantilla_partida").set(hashMapOf("numero_de_partida" to "0", "id_personaje" to "0"))
                        //bd.collection("Usuari").document(user.uid).collection("Personajes").document("Plantilla personaje").set(hashMapOf())
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