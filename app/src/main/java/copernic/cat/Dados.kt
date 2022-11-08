package copernic.cat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

class Dados : Fragment(R.layout.fragment_dados) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnatras = requireView().findViewById<ImageButton>(R.id.btn_felcha_dados)


        btnatras.setOnClickListener {
            findNavController().navigate(R.id.action_dados_to_inici)

        }
    }
}