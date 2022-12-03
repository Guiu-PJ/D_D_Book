package copernic.cat.Reglas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.R
import copernic.cat.databinding.FragmentAccionBinding
import copernic.cat.databinding.FragmentAlcanceBinding
import copernic.cat.databinding.FragmentIniciBinding
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
class alcance : Fragment() {
    private var _binding: FragmentAlcanceBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlcanceBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFlechaAlcance.setOnClickListener{
            findNavController().navigate(R.id.action_alcance_to_reglas)
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO){//llegir dades de la base de dades
                llegirnovedades()
            }

        }
    }
    fun llegirnovedades() {
        bd.collection("Reglas").document("alcance").get().addOnSuccessListener {
            binding.txtAlcance.text = it.get("Descripcion") as String?
        }
    }
}