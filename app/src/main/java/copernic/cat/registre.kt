package copernic.cat

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
import com.google.firebase.ktx.Firebase


class registre : AppCompatActivity() {

    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerRepeatPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var registerGoLoginButton: Button

    private  lateinit var auth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registre)
        if (supportActionBar != null)
            supportActionBar!!.hide()

        auth = Firebase.auth

        registerEmail = findViewById(R.id.correuRegistre)
        registerPassword = findViewById(R.id.contrasenyaRegistre)
        registerRepeatPassword = findViewById(R.id.repetirContrasenya)
        registerButton = findViewById(R.id.botoRegistre)
        registerGoLoginButton = findViewById(R.id.botoActivityLogin)



        registerButton.setOnClickListener{

            val email = registerEmail.text.toString()
            val password = registerPassword.text.toString()
            val repeatPassword = registerRepeatPassword.text.toString()

            if (password.equals(repeatPassword) && checkEmpty(email, password, repeatPassword)){
                register(email, password)
            }

        }


        registerGoLoginButton.setOnClickListener {
            startActivity(Intent(this, login::class.java))
            finish()
        }


    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, login::class.java))
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

    private fun checkEmpty(email: String, password: String, repeatPassword: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()
    }
}