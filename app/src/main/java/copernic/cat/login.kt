package copernic.cat

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

class login : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var loginGoRegistreButton: Button
    private lateinit var recuperarContraseñaButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null)
            supportActionBar!!.hide()

        auth = Firebase.auth

        loginEmail = findViewById(R.id.correuLogin)
        loginPassword = findViewById(R.id.contrasenyaLogin)
        loginButton = findViewById(R.id.botoLogin)
        loginGoRegistreButton = findViewById(R.id.botoActivityRegistre)
        recuperarContraseñaButton = findViewById(R.id.botoActivityRecuperarContrasena)


        loginGoRegistreButton.setOnClickListener {
            startActivity(Intent(this, registre::class.java))
            finish()
        }

        recuperarContraseñaButton.setOnClickListener{
            startActivity(Intent(this, recuperar_contrasenya::class.java))
            finish()
        }




        loginButton.setOnClickListener{
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

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
                    finish()
                }else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Login failed")
                    builder.setMessage("L'email o la contrasenya no son correctes")
                    builder.setPositiveButton("OK"){_, _ ->
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
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