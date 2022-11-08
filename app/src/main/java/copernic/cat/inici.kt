package copernic.cat

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class inici : Fragment(R.layout.fragment_inici) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnIrNovedades = requireView().findViewById<Button>(R.id.btn_novedades)
        val btnIrCompendios = requireView().findViewById<Button>(R.id.btn_compendios)
        val btnIrReglas = requireView().findViewById<Button>(R.id.btn_reglas)
        val btnIrDados = requireView().findViewById<Button>(R.id.btn_dados)


        btnIrNovedades.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_novedades)
        }

        btnIrReglas.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_reglas)
        }

        btnIrDados.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_dados)
        }

    }

}