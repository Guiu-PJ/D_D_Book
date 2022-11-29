package copernic.cat.Ficha_Personaje

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
import copernic.cat.databinding.FragmentFichaPersonajeEstadisticasSecundariasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.h

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ficha_personaje_estadisticas_secundarias.newInstance] factory method to
 * create an instance of this fragment.
 */
class ficha_personaje_estadisticas_secundarias : Fragment() {
    private var _binding: FragmentFichaPersonajeEstadisticasSecundariasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = copernic.cat.databinding.FragmentFichaPersonajeEstadisticasSecundariasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = ficha_personaje_estadisticas_secundariasArgs.fromBundle(bundle!!)
        val nom = args.nomPers

        binding.btnSiguienteFichaPersonajeEstadisticasSecundarias.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                        bd.collection("Usuari").document(user!!.uid)
                        .collection("Personajes")
                        .document(nom.toString()).update(
                            "acrobacias", binding.checkBoxAcrobacias.isChecked,
                                "medicina", binding.checkBoxMedicina.isChecked,
                                "atletismo", binding.checkBoxAtletismo.isChecked,
                                "natura", binding.checkBoxNatura.isChecked,
                                "conocimiento_arcano", binding.checkBoxArcano.isChecked,
                                "percepcion", binding.checkBoxPercepcion.isChecked,
                                "engaño", binding.checkBoxEngaO.isChecked,
                                "perspicacia", binding.checkBoxPerspicacia.isChecked,
                                "historia", binding.checkBoxHistoria.isChecked,
                                "persuasion", binding.checkBoxPersuasion.isChecked,
                                "interpretacion", binding.checkBoxInterpretacion.isChecked,
                                "religion", binding.checkBoxReligion.isChecked,
                                "intimidacion", binding.checkBoxIntimidacion.isChecked,
                                "sigilo", binding.checkBoxSigilo.isChecked,
                                "investigacion", binding.checkBoxInvestigacion.isChecked,
                                "supervivencia", binding.checkBoxSupervivencia.isChecked,
                                "juego_de_manos", binding.checkBoxJuegoDeManos.isChecked,
                                "trato_con_animales", binding.checkBoxTrataminetoAnimales.isChecked,
                                "idioma1", binding.editEsstadisticasSecIdioma.text.toString(),
                                "idioma2", binding.editEsstadisticasSecIdioma2.text.toString(),
                                "idioma3", binding.editEsstadisticasSecIdioma3.text.toString(),
                                "idioma4", binding.editEsstadisticasSecIdioma4.text.toString(),
                                "idioma5", binding.editEsstadisticasSecIdioma5.text.toString()
                        )
                }
            }
            val nomp = nom
            val directions = ficha_personaje_estadisticas_secundariasDirections.actionFichaPersonajeEstadisticasSecundariasToFichaPersonajeHabilidades(nomp)

            findNavController().navigate(directions)
        }
    }
}