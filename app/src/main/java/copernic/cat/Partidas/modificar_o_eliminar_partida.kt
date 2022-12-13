package copernic.cat.Partidas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.EditarPersonaje.editarOEliminarPersonajeArgs
import copernic.cat.EditarPersonaje.editarOEliminarPersonajeDirections
import copernic.cat.R
import copernic.cat.databinding.FragmentCrearPartidaNomBinding
import copernic.cat.databinding.FragmentEditarOEliminarPersonajeBinding
import copernic.cat.databinding.FragmentModificarOEliminarPartidaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [modificar_o_eliminar_partida.newInstance] factory method to
 * create an instance of this fragment.
 */
class modificar_o_eliminar_partida : Fragment() {
    private var _binding: FragmentModificarOEliminarPartidaBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentModificarOEliminarPartidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = editarOEliminarPersonajeArgs.fromBundle(bundle!!)
        val nom = args.nomp

        binding.txtNomPartida.text = nom

        binding.btnEditarPartida.setOnClickListener {
            val action = modificar_o_eliminar_partidaDirections.actionModificarOEliminarPartidaToModificarPartidaGeneral(nom)
            view.findNavController().navigate(action)
        }

        binding.btnEliminarPartida.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Usuari").document(user!!.uid).collection("PerPartidas")
                        .document(nom.toString()).delete()
                    bd.collection("Usuari").document(user.uid).collection("Partidas")
                        .document(nom.toString()).delete()
                }
            }
        }

    }
}