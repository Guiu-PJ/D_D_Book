package copernic.cat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController


class reglas : Fragment(R.layout.fragment_reglas) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnatras = requireView().findViewById<ImageButton>(R.id.btn_flecha_reglas)

        btnatras.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_inici)
        }

    }
}
