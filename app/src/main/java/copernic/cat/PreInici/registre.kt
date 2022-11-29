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
                          .document("IdEstadisticas").set(hashMapOf("numero_tiradas" to "0", "criticos" to "0", "numero_de_personajes" to "0", "numero_de_partidas" to "0", "clase_mas_jugada" to "ninguna"))
                        bd.collection("Usuari").document(user.uid).collection("Partidas").document("Plantilla_partida").set(hashMapOf("numero_de_partida" to "0", "id_personaje" to "0"))
                        bd.collection("Usuari").document(user.uid).collection("Personajes").document("Plantilla_personaje")
                            .set(hashMapOf(
                                "nombre" to "vacio",
                                "classe" to "vacia",
                                "Raza" to "vacia",
                                "trasfondo" to "vacio",
                                "nivel" to "0",
                                "alineamiento" to "vacio",
                                "vidaMax" to "0",
                                "vidaActual" to "0",
                                "fuerza" to "0",
                                "constitucion" to "0",
                                "carisma" to "0",
                                "destreza" to "0",
                                "inteligencia" to "0",
                                "sabiduria" to "0",
                                "movimineto" to "0",
                                "inici" to "0",
                                "dados_de_golpe" to "0",
                                "salvaconExito" to "0",
                                "salvacionFallo" to "0",
                                "habilidad1" to "vacio",
                                "habilidad2" to "vacio",
                                "habilidad3" to "vacio",
                                "habilidad4" to "vacio",
                                "habilidad5" to "vacio",
                                "habilidad6" to "vacio",
                                "habilidad7" to "vacio",
                                "magia1" to "vacio",
                                "magia2" to "vacio",
                                "magia3" to "vacio",
                                "magia4" to "vacio",
                                "magia5" to "vacio",
                                "magia6" to "vacio",
                                "magia7" to "vacio",
                                "armas_y_armduras1" to "vacio",
                                "armas_y_armduras2" to "vacio",
                                "armas_y_armduras3" to "vacio",
                                "armas_y_armduras4" to "vacio",
                                "armas_y_armduras5" to "vacio",
                                "armas_y_armduras6" to "vacio",
                                "armas_y_armduras7" to "vacio",
                                "cobre" to "0",
                                "plata" to "0",
                                "oro" to "0",
                                "platino" to "0",
                                "comida_y_objetos1" to "vacio",
                                "comida_y_objetos2" to "vacio",
                                "comida_y_objetos3" to "vacio",
                                "comida_y_objetos4" to "vacio",
                                "comida_y_objetos5" to "vacio",
                                "comida_y_objetos6" to "vacio",
                                "comida_y_objetos7" to "vacio",
                                "acrobacias" to false,
                                "medicina" to false,
                                "atletismo" to false,
                                "natura" to false,
                                "conocimineto_arcano" to false,
                                "percepcion" to false,
                                "engaño" to false,
                                "perspicacia" to false,
                                "historia" to false,
                                "persuasion" to false,
                                "interpretacion" to false,
                                "religion" to false,
                                "intimidacion" to false,
                                "sigilo" to false,
                                "investigacion" to false,
                                "supervivencia" to false,
                                "juego_de_manos" to false,
                                "Trato_con_animales" to false,
                                "idioma1" to "vacio",
                                "idioma2" to "vacio",
                                "idioma3" to "vacio",
                                "idioma4" to "vacio",
                                "idioma5" to "vacio",
                            ))
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