package copernic.cat.Ficha_Personaje

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
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
 * Use the [ficha_personaje_equipamiento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ficha_personaje_equipamiento : Fragment() {
    private var _binding: FragmentFichaPersonajeEquipamientoBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFichaPersonajeEquipamientoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = ficha_personaje_equipamientoArgs.fromBundle(bundle!!)
        val nom = args.nomPers

        binding.btnSiguienteFichaPersonajeEquipamineto.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                        bd.collection("Usuari").document(user!!.uid)
                        .collection("Personajes")
                        .document(nom.toString()).update(
                                "armas_y_armaduras1", binding.editEquipamientoArmasArmaduras.text.toString(),
                                "armas_y_armaduras2", binding.editEquipamientoArmasArmaduras2.text.toString(),
                                "armas_y_armaduras3", binding.editEquipamientoArmasArmasArmaduras3.text.toString(),
                                "armas_y_armaduras4", binding.editEquipamientoArmasArmaduras4.text.toString(),
                                "armas_y_armaduras5", binding.editEquipamientoArmasArmaduras5.text.toString(),
                                "armas_y_armaduras6", binding.editEquipamientoArmasArmaduras6.text.toString(),
                                "armas_y_armaduras7", binding.editEquipamientoArmasArmaduras7.text.toString(),
                                "cobre", binding.editEquipamientoCobre.text.toString(),
                                "plata", binding.editEquipamientoPlata.text.toString(),
                                "oro", binding.editEquipamientoOro.text.toString(),
                                "platino", binding.editEquipamientoPlatino.text.toString(),
                                "comida_y_objetos1", binding.editEquipamientoComidaObjetos.text.toString(),
                                "comida_y_objetos2", binding.editEquipamientoComidaObjetos2.text.toString(),
                                "comida_y_objetos3", binding.editEquipamientoComidaObjetos3.text.toString(),
                                "comida_y_objetos4", binding.editEquipamientoComidaObjetos4.text.toString(),
                                "comida_y_objetos5", binding.editEquipamientoComidaObjetos5.text.toString(),
                                "comida_y_objetos6", binding.editEquipamientoComidaObjetos6.text.toString(),
                                "comida_y_objetos7", binding.editEquipamientoComidaObjetos7.text.toString(),
                        )

                }
            }
            val nomp = nom
            val directions = ficha_personaje_equipamientoDirections.actionFichaPersonajeEquipamientoToFichaPersonajeEstadisticasSecundarias(nomp)

            findNavController().navigate(directions)
        }
    }
}