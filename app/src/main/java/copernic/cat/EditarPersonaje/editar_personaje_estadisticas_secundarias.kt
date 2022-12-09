package copernic.cat.EditarPersonaje

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
import copernic.cat.R
import copernic.cat.databinding.FragmentEditPersonajeEquipamientoBinding
import copernic.cat.databinding.FragmentEditarPersonajeEstadisticasSecundariasBinding
import copernic.cat.databinding.FragmentEditarPersonajeGeneralBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class editar_personaje_estadisticas_secundarias : Fragment() {
    private var _binding: FragmentEditarPersonajeEstadisticasSecundariasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
            _binding = FragmentEditarPersonajeEstadisticasSecundariasBinding.inflate(inflater, container, false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = editar_personaje_estadisticas_secundariasArgs.fromBundle(bundle!!)
        val nom = args.nomp

        lifecycleScope.launch {
            withContext(Dispatchers.Unconfined) {
                bd.collection("Usuari").document(user!!.uid)
                    .collection("Personajes")
                    .document(nom.toString()).get()
                    .addOnSuccessListener {
                        binding.editEsstadisticasSecIdioma.setText(it.get("idioma1") as String)
                        binding.editEsstadisticasSecIdioma2.setText(it.get("idioma2") as String)
                        binding.editEsstadisticasSecIdioma3.setText(it.get("idioma3") as String)
                        binding.editEsstadisticasSecIdioma4.setText(it.get("idioma4") as String)
                        binding.editEsstadisticasSecIdioma5.setText(it.get("idioma5") as String)
                        binding.checkBoxAcrobacias.isChecked = it.get("acrobacias") as Boolean
                        binding.checkBoxMedicina.isChecked = it.get("medicina") as Boolean
                        binding.checkBoxAtletismo.isChecked = it.get("atletismo") as Boolean
                        binding.checkBoxNatura.isChecked = it.get("natura") as Boolean
                        binding.checkBoxArcano.isChecked = it.get("conocimiento_arcano") as Boolean
                        binding.checkBoxPercepcion.isChecked = it.get("percepcion") as Boolean
                        binding.checkBoxEngaO.isChecked = it.get("engaño") as Boolean
                        binding.checkBoxPerspicacia.isChecked = it.get("perspicacia") as Boolean
                        binding.checkBoxHistoria.isChecked = it.get("historia") as Boolean
                        binding.checkBoxPersuasion.isChecked = it.get("persuasion") as Boolean
                        binding.checkBoxInterpretacion.isChecked = it.get("interpretacion") as Boolean
                        binding.checkBoxReligion.isChecked = it.get("religion") as Boolean
                        binding.checkBoxIntimidacion.isChecked = it.get("intimidacion") as Boolean
                        binding.checkBoxSigilo.isChecked = it.get("sigilo") as Boolean
                        binding.checkBoxInvestigacion.isChecked = it.get("investigacion") as Boolean
                        binding.checkBoxSupervivencia.isChecked = it.get("supervivencia") as Boolean
                        binding.checkBoxJuegoDeManos.isChecked = it.get("juego_de_manos") as Boolean
                        binding.checkBoxTrataminetoAnimales.isChecked = it.get("Trato_con_animales") as Boolean

                    }
            }
        }

        binding.btnSiguienteFichaPersonajeEstadisticasSecundarias.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Usuari").document(user!!.uid)
                        .collection("Personajes")
                        .document(nom.toString()).update(
                            "idioma1", binding.editEsstadisticasSecIdioma.text.toString(),
                            "idioma2", binding.editEsstadisticasSecIdioma2.text.toString(),
                            "idioma3", binding.editEsstadisticasSecIdioma3.text.toString(),
                            "idioma4", binding.editEsstadisticasSecIdioma4.text.toString(),
                            "idioma5", binding.editEsstadisticasSecIdioma5.text.toString(),
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
                        )
                }
            }
            val action = editar_personaje_estadisticas_secundariasDirections.actionEditarPersonajeEstadisticasSecundariasToEditarPersonajeHabilidades(nom)
            view.findNavController().navigate(action)
        }
    }
}