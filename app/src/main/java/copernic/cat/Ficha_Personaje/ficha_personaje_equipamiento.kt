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
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.Inici.MainActivity
import copernic.cat.Models.PersonajeEquipamiento
import copernic.cat.Models.PersonajeGeneral
import copernic.cat.R
import copernic.cat.databinding.FragmentFichaPersonajeEquipamientoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
    private var user: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (requireActivity() as MainActivity).title = getString(R.string.equipamiento)
        _binding = FragmentFichaPersonajeEquipamientoBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        user = auth.currentUser

        /**
         * Recojemos el nombre dle personaje
         */
        val bundle = arguments
        val args = ficha_personaje_equipamientoArgs.fromBundle(bundle!!)
        val nom = args.nomPers

        /**
         * Boton para pasar a la siguiente pantalla
         */
        binding.btnSiguienteFichaPersonajeEquipamineto.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    //anadirPersonajeEquipamiento(nom.toString())
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
            /**
             * Enviamos le nombre del personaje
             */
            val nomp = nom
            val directions = ficha_personaje_equipamientoDirections.actionFichaPersonajeEquipamientoToFichaPersonajeEstadisticasSecundarias(nomp)
            findNavController().navigate(directions)
        }
    }


    /**
     * Llenamos le objeto perosnaje equipamiento
     *
     * @return PersonajeEquipamiento
     */
    fun llegirDades(): PersonajeEquipamiento {
        val armas_y_armaduras1 = binding.editEquipamientoArmasArmaduras.text.toString()
        val armas_y_armaduras2 = binding.editEquipamientoArmasArmaduras2.text.toString()
        val armas_y_armaduras3 = binding.editEquipamientoArmasArmasArmaduras3.text.toString()
        val armas_y_armaduras4 = binding.editEquipamientoArmasArmaduras4.text.toString()
        val armas_y_armaduras5 = binding.editEquipamientoArmasArmaduras5.text.toString()
        val armas_y_armaduras6 = binding.editEquipamientoArmasArmaduras6.text.toString()
        val armas_y_armaduras7 = binding.editEquipamientoArmasArmaduras7.text.toString()
        val cobre = binding.editEquipamientoCobre.text.toString()
        val plata = binding.editEquipamientoPlata.text.toString()
        val oro = binding.editEquipamientoOro.text.toString()
        val platino = binding.editEquipamientoPlatino.text.toString()
        val comida_y_objetos1 = binding.editEquipamientoComidaObjetos.text.toString()
        val comida_y_objetos2 = binding.editEquipamientoComidaObjetos2.text.toString()
        val comida_y_objetos3 = binding.editEquipamientoComidaObjetos3.text.toString()
        val comida_y_objetos4 = binding.editEquipamientoComidaObjetos4.text.toString()
        val comida_y_objetos5 = binding.editEquipamientoComidaObjetos5.text.toString()
        val comida_y_objetos6 = binding.editEquipamientoComidaObjetos6.text.toString()
        val comida_y_objetos7 = binding.editEquipamientoComidaObjetos7.text.toString()


        return PersonajeEquipamiento(
            armas_y_armaduras1,
            armas_y_armaduras2,
            armas_y_armaduras3,
            armas_y_armaduras4,
            armas_y_armaduras5,
            armas_y_armaduras6,
            armas_y_armaduras7,
            cobre,
            plata,
            oro,
            platino,
            comida_y_objetos1,
            comida_y_objetos2,
            comida_y_objetos3,
            comida_y_objetos4,
            comida_y_objetos5,
            comida_y_objetos6,
            comida_y_objetos7,
        )
    }

    private suspend fun anadirPersonajeEquipamiento(nom: String) {
        val personajeEquipamiento = llegirDades()
        bd.collection("Usuari").document(user!!.uid)
            .collection("Personajes")
            .document(nom).update(
                "hola", "a",
                personajeEquipamiento
            )
    }
}