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
import copernic.cat.EditarPersonaje.edit_personaje_equipamientoDirections
import copernic.cat.EditarPersonaje.editar_personaje_generalArgs
import copernic.cat.R
import copernic.cat.databinding.FragmentModificarPartidaEquipamientoBinding
import copernic.cat.databinding.FragmentModificarPartidaGeneralBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [modificar_partida_equipamiento.newInstance] factory method to
 * create an instance of this fragment.
 */
class modificar_partida_equipamiento : Fragment() {
    private var _binding: FragmentModificarPartidaEquipamientoBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentModificarPartidaEquipamientoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = modificar_partida_generalArgs.fromBundle(bundle!!)
        val nom = args.nomp


        lifecycleScope.launch {
            withContext(Dispatchers.Unconfined) {
                bd.collection("Usuari").document(user!!.uid)
                    .collection("PerPartidas")
                    .document(nom.toString()).get()
                    .addOnSuccessListener {
                        binding.editEquipamientoArmasArmaduras.setText(it.get("armas_y_armaduras1") as String)
                        binding.editEquipamientoArmasArmaduras2.setText(it.get("armas_y_armaduras2") as String)
                        binding.editEquipamientoArmasArmasArmaduras3.setText(it.get("armas_y_armaduras3") as String)
                        binding.editEquipamientoArmasArmaduras4.setText(it.get("armas_y_armaduras4") as String)
                        binding.editEquipamientoArmasArmaduras5.setText(it.get("armas_y_armaduras5") as String)
                        binding.editEquipamientoArmasArmaduras6.setText(it.get("armas_y_armaduras6") as String)
                        binding.editEquipamientoArmasArmaduras7.setText(it.get("armas_y_armaduras7") as String)
                        binding.editEquipamientoCobre.setText(it.get("cobre") as String)
                        binding.editEquipamientoPlata.setText(it.get("plata") as String)
                        binding.editEquipamientoOro.setText(it.get("oro") as String)
                        binding.editEquipamientoPlatino.setText(it.get("platino") as String)
                        binding.editEquipamientoComidaObjetos.setText(it.get("comida_y_objetos1") as String)
                        binding.editEquipamientoComidaObjetos2.setText(it.get("comida_y_objetos2") as String)
                        binding.editEquipamientoComidaObjetos3.setText(it.get("comida_y_objetos3") as String)
                        binding.editEquipamientoComidaObjetos4.setText(it.get("comida_y_objetos4") as String)
                        binding.editEquipamientoComidaObjetos5.setText(it.get("comida_y_objetos5") as String)
                        binding.editEquipamientoComidaObjetos6.setText(it.get("comida_y_objetos6") as String)
                        binding.editEquipamientoComidaObjetos7.setText(it.get("comida_y_objetos7") as String)
                    }
            }
        }



        binding.btnSiguienteFichaPersonajeEquipamineto.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Usuari").document(user!!.uid)
                        .collection("PerPartidas")
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
            val action = modificar_partida_equipamientoDirections.actionModificarPartidaEquipamientoToModificarPartidaEstadisticasSecundarias(nom)
            view.findNavController().navigate(action)
        }
    }
}