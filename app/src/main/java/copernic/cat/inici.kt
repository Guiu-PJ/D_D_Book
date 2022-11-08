package copernic.cat

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


class inici : Fragment(R.layout.fragment_inici) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnIrNovedades = requireView().findViewById<Button>(R.id.btn_novedades)
        val btnIrCompendios = requireView().findViewById<Button>(R.id.btn_compendios)
        val btnIrReglas = requireView().findViewById<Button>(R.id.btn_reglas)
        val btnIrDados = requireView().findViewById<Button>(R.id.btn_dados)
        val btncerrarsesion = requireView().findViewById<Button>(R.id.btn_cerrar_sesion)


        btnIrNovedades.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_novedades)
        }

        btnIrReglas.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_reglas)
        }

        btnIrDados.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_dados)
        }

        btncerrarsesion.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            //startActivity(Intent(this, login::class.java))
            activity?.finish()
        }

    }


}