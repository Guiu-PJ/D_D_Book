package copernic.cat.Informacio

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.R
import copernic.cat.Reglas.accion
import copernic.cat.databinding.FragmentNovedadesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [accion.newInstance] factory method to
 * create an instance of this fragment.
 */
class novedades : Fragment() {
    private var _binding:FragmentNovedadesBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovedadesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFlechaNovedades.setOnClickListener {
            findNavController().navigate(R.id.action_novedades_to_inici)
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO){//llegir dades de la base de dades
                llegirnovedades()
            }
        }

    }
 fun llegirnovedades(){
     bd.collection("Novedades").document("Añadir").get().addOnSuccessListener {
         binding.txtInfoAAdido.text = it.get("añadido") as String?
     }
     bd.collection("Novedades").document("Eliminado").get().addOnSuccessListener {
         binding.txtInfoEliminado.text = it.get("eliminado") as String?
     }
     bd.collection("Novedades").document("Modificado").get().addOnSuccessListener {
         binding.txtInfoModificado.text = it.get("modificado") as String?
     }
 }
}