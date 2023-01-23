package copernic.cat.PreInici

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.classes.usuaris
import copernic.cat.databinding.ActivityLoginBinding

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var bd = FirebaseFirestore.getInstance()
    //private lateinit var Usuari: usuaris

    @SuppressLint("MissingInflatedId")
    /**
     * En el método onCreate, se infla el layout correspondiente
     * y se establecen los listener para los botones de inicio de sesión, registro y recuperar contraseña.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
        * Ocultamos la acction bar
        */
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
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.iniciar_sesion_fallido))
                    builder.setMessage(getString(R.string.la_contrasena_no_puede_estar_vacia))
                    builder.setPositiveButton("OK"){_, _ ->
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(true)
                    alertDialog.show()
                }else if(email.isEmpty()&&password.isNotEmpty()){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.iniciar_sesion_fallido))
                    builder.setMessage(getString(R.string.el_usuario_no_puede_estar_vacio))
                    builder.setPositiveButton("OK"){_, _ ->
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(true)
                    alertDialog.show()
                }else if (email.isEmpty()&&password.isEmpty()){
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle(getString(R.string.iniciar_sesion_fallido))
                        builder.setMessage(getString(R.string.el_usuario_y_contrasena_no_pueden_estar_vacios))
                        builder.setPositiveButton("OK"){_, _ ->
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(true)
                        alertDialog.show()
                }
            }
        }

    }


    /**
     * Comprueba el login del usuario
     *
     * @param email correo del usuario
     * @param password contraseña del usuario
     */
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))

                    //finish()
                }else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.iniciar_sesion_fallido))
                    builder.setMessage(getString(R.string.email_o_contrasena_incorrectos))
                    builder.setPositiveButton("OK"){_, _ ->
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(true)
                    alertDialog.show()

                }
            }
    }

    /**
     * El método onStart se encarga de comprobar
     * si el usuario ya ha iniciado sesión, y si es así, lo redirige a la actividad principal.
     */
    override fun onStart() {
        super.onStart() //Cridem al la funció onStart() perquè ens mostri per pantalla l'activity
        auth = Firebase.auth
        //currentUser és un atribut de la classe FirebaseAuth que guarda l'usuari autenticat. Si aquest no està autenticat, el seu valor serà null.
        val currentUser = auth.currentUser
        createNotificationChannel()
        if(currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    /**
     * Crea el canal por donde se envian las notificaciones
     */
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "descripcion"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("1", name, importance).apply {
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Comprueba que los campos no estan vacios
     *
     * @param email correo del usuario
     * @param password contraseña del usuario
     */
    fun checkEmpty(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun llegirDades(): usuaris {
        val email =binding.correuLogin.text.toString()

        return usuaris(email, false)
    }
}