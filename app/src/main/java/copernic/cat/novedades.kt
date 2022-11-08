package copernic.cat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController


class novedades : Fragment(R.layout.fragment_novedades) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnatras = requireView().findViewById<ImageButton>(R.id.btn_flecha_novedades)

        btnatras.setOnClickListener {
            findNavController().navigate(R.id.action_novedades_to_inici)
        }

    }
}