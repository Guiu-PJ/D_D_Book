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
import com.google.android.material.textfield.TextInputEditText


class recuperar_contrasenya : AppCompatActivity() {

    private lateinit var botoLogin: Button
    private lateinit var botoRecuperarContraseña: Button
    private lateinit var txt_correu: TextInputEditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenya)

        if (supportActionBar != null)
            supportActionBar!!.hide()

        auth = Firebase.auth


        botoLogin = findViewById(R.id.botoActivityLogin)
        botoRecuperarContraseña = findViewById(R.id.btn_recuperar_contrasenya)
        txt_correu = findViewById(R.id.txt_email_contrasenya)

        botoLogin.setOnClickListener{
            startActivity(Intent(this, login::class.java))
            finish()
        }

        botoRecuperarContraseña.setOnClickListener{
            resetPassword();
        }

    }




    private fun resetPassword(){
        auth= FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(txt_correu.text.toString()).addOnCompleteListener(){task ->
            if(task.isSuccessful){
                startActivity(Intent(this, login::class.java))
                finish()
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Recuperadr contraseña a fallado")
                builder.setMessage("L'email no es correcte")
                builder.setPositiveButton("OK"){_, _ ->
                    Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            }

        }
    }

}