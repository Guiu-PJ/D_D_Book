package copernic.cat.EditarPersonaje

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.Ficha_Personaje.ficha_personaje_equipamientoArgs
import copernic.cat.Perfil.perfilDirections
import copernic.cat.R
import copernic.cat.databinding.FragmentEditarOEliminarPersonajeBinding
import copernic.cat.databinding.FragmentFichaPersonajeEquipamientoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editarOEliminarPersonaje.newInstance] factory method to
 * create an instance of this fragment.
 */
class editarOEliminarPersonaje : Fragment() {
    private var _binding: FragmentEditarOEliminarPersonajeBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditarOEliminarPersonajeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = editarOEliminarPersonajeArgs.fromBundle(bundle!!)
        val nom = args.nomp

        binding.txtNomPersonatge.text = nom

        binding.btnEditarPersonatge.setOnClickListener {
            val action = editarOEliminarPersonajeDirections.actionEditarOEliminarPersonajeToEditarPersonajeGeneral(nom)
            view.findNavController().navigate(action)
        }

        binding.btnEliminarPersonatge.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Usuari").document(user!!.uid)
                        .collection("Personajes")
                        .document(nom.toString()).delete().addOnSuccessListener {
                        findNavController().navigate(R.id.action_editarOEliminarPersonaje_to_inici)
                    }
                }
            }
        }
    }

}