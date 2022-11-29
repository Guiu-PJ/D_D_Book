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
import copernic.cat.databinding.FragmentCompendiosBinding
import copernic.cat.databinding.FragmentFichaPersonajeGeneralBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ficha_personaje_general.newInstance] factory method to
 * create an instance of this fragment.
 */
class ficha_personaje_general : Fragment() {
    private var _binding: FragmentFichaPersonajeGeneralBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFichaPersonajeGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser
        binding.btnSiguienteFichaPersonajeGeneral.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    if (binding.editPersonajeNombre.text.toString().isNotEmpty()) {
                            bd.collection("Usuari").document(user!!.uid)
                            .collection("Personajes")
                            .document(binding.editPersonajeNombre.text.toString()).get()
                            .addOnSuccessListener { it ->
                                if (it.exists()) {
                                    Toast.makeText(
                                        context, "Este personaje ya existe", Toast.LENGTH_SHORT).show()
                                } else {
                                    bd.collection("Usuari").document(user.uid)
                                        .collection("Personajes")
                                        .document(binding.editPersonajeNombre.text.toString()).set(
                                            hashMapOf(
                                                "nombre" to binding.editPersonajeNombre.text.toString(),
                                                "classe" to binding.editPersonajeClasse.text.toString(),
                                                "Raza" to binding.editPersonajeRaza.text.toString(),
                                                "trasfondo" to binding.editPersonajeTrasfondo.text.toString(),
                                                "nivel" to binding.editPersonajeNivel.text.toString(),
                                                "alineamiento" to binding.editPersonajeAlineamiento.text.toString(),
                                                "vidaMax" to binding.editPersonajeVidamax.text.toString(),
                                                "vidaActual" to binding.editPersonajeVidaactual.text.toString(),
                                                "fuerza" to binding.editFue.text.toString(),
                                                "constitucion" to binding.editCon.text.toString(),
                                                "carisma" to binding.editCar.text.toString(),
                                                "destreza" to binding.editDes.text.toString(),
                                                "inteligencia" to binding.editInt.text.toString(),
                                                "sabiduria" to binding.editSab.text.toString(),
                                                "movimineto" to binding.editMov.text.toString(),
                                                "inici" to binding.editInici.text.toString(),
                                                "dados_de_golpe" to binding.editDadosDeGolpe.text.toString(),
                                                "salvaconExito" to binding.editExito.text.toString(),
                                                "salvacionFallo" to binding.editFallo.text.toString(),
                                                "habilidad1" to "",
                                                "habilidad2" to "",
                                                "habilidad3" to "",
                                                "habilidad4" to "",
                                                "habilidad5" to "",
                                                "habilidad6" to "",
                                                "habilidad7" to "",
                                                "magia1" to "",
                                                "magia2" to "",
                                                "magia3" to "",
                                                "magia4" to "",
                                                "magia5" to "",
                                                "magia6" to "",
                                                "magia7" to "",
                                                "armas_y_armaduras1" to "",
                                                "armas_y_armaduras2" to "",
                                                "armas_y_armaduras3" to "",
                                                "armas_y_armaduras4" to "",
                                                "armas_y_armaduras5" to "",
                                                "armas_y_armaduras6" to "",
                                                "armas_y_armaduras7" to "",
                                                "cobre" to "",
                                                "plata" to "",
                                                "oro" to "",
                                                "platino" to "",
                                                "comida_y_objetos1" to "",
                                                "comida_y_objetos2" to "",
                                                "comida_y_objetos3" to "",
                                                "comida_y_objetos4" to "",
                                                "comida_y_objetos5" to "",
                                                "comida_y_objetos6" to "",
                                                "comida_y_objetos7" to "",
                                                "acrobacias" to false,
                                                "medicina" to false,
                                                "atletismo" to false,
                                                "natura" to false,
                                                "conocimineto_arcano" to false,
                                                "percepcion" to false,
                                                "engaño" to false,
                                                "perspicacia" to false,
                                                "historia" to false,
                                                "persuasion" to false,
                                                "interpretacion" to false,
                                                "religion" to false,
                                                "intimidacion" to false,
                                                "sigilo" to false,
                                                "investigacion" to false,
                                                "supervivencia" to false,
                                                "juego_de_manos" to false,
                                                "Trato_con_animales" to false,
                                                "idioma1" to "",
                                                "idioma2" to "",
                                                "idioma3" to "",
                                                "idioma4" to "",
                                                "idioma5" to "",
                                            )
                                        )

                                    val nomp = binding.editPersonajeNombre.text.toString()
                                    val directions = ficha_personaje_generalDirections.actionFichaPersonajeGeneralToFichaPersonajeEquipamiento(nomp)

                                    findNavController().navigate(directions)
                                    bd.collection("Usuari").document(user.uid).collection("Estadisticas").document("IdEstadisticas").get().addOnSuccessListener{
                                                val per = it.get("numero_de_personajes") as String?
                                                val num = per!!.toInt()+1
                                        bd.collection("Usuari").document(user.uid).collection("Estadisticas").document("IdEstadisticas").update("numero_de_personajes", (num).toString())
                                    }


                                }
                            }
                    }
                }
            }
        }
    }
}