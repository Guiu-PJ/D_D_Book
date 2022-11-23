package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Colecciones.Novedades
import copernic.cat.R
import copernic.cat.classes.reglas
import copernic.cat.databinding.FragmentAnadirReglasBinding
import copernic.cat.databinding.FragmentNovedadesAdminBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [novedades_admin.newInstance] factory method to
 * create an instance of this fragment.
 */
class novedades_admin : Fragment() {
    private var _binding: FragmentNovedadesAdminBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNovedadesAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val novedades = llegirDades()

        binding.btnAceptarNovedades.setOnClickListener{
            bd.collection("Novedades").document("Añadir").set(hashMapOf("añadido" to binding.inputTxtAAdido.text.toString()))
            bd.collection("Novedades").document("Eliminado").set(hashMapOf("eliminado" to binding.inputTxtEliminado.text.toString()))
            bd.collection("Novedades").document("Modificado").set(hashMapOf("modificado" to binding.txtInputModificado.text.toString()))
            findNavController().navigate(R.id.action_novedades_admin_to_admin_inici)
        }

    }

    fun llegirDades(): Novedades {
        val anadido = binding.inputTxtAAdido.text.toString()
        val eliminado = binding.inputTxtEliminado.text.toString()
        val modificado = binding.txtInputModificado.text.toString()

        return Novedades(anadido, eliminado, modificado)
    }

}