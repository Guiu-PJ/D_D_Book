package copernic.cat.Informacio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import copernic.cat.R


class reglas : Fragment(R.layout.fragment_reglas) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnatras = requireView().findViewById<ImageButton>(R.id.btn_flecha_reglas)

        btnatras.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_inici)
        }

    }
}
