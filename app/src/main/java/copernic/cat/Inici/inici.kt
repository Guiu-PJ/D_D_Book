package copernic.cat.Inici

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import copernic.cat.R
import copernic.cat.databinding.ActivityMainBinding
import copernic.cat.databinding.ActivityRecuperarContrasenyaBinding
import copernic.cat.databinding.FragmentIniciBinding
import copernic.cat.databinding.FragmentNovedadesBinding

class inici : Fragment() {
    private var _binding: FragmentIniciBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIniciBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNovedades.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_novedades)
        }

        binding.btnReglas.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_reglas)
        }

        binding.btnDados.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_dados)
        }
        binding.btnCerrarSesion.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            //startActivity(Intent(this, login::class.java))
            activity?.finish()
        }
    }

}