package copernic.cat.Inici

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import copernic.cat.R
import copernic.cat.Reglas.accion
import copernic.cat.databinding.FragmentIniciBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [accion.newInstance] factory method to
 * create an instance of this fragment.
 */

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

        binding.btnCompendios.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_compendios)
        }

        binding.btnReglas.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_reglas)
        }

        binding.btnDados.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_dados)
        }

        binding.btnAAdirReglas.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_anadir_reglas)
        }
    }

}