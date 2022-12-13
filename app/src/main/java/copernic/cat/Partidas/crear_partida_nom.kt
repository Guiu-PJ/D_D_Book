package copernic.cat.Partidas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.EditarPersonaje.editar_personaje_habilidadesArgs
import copernic.cat.R
import copernic.cat.Reglas.movimiento
import copernic.cat.Reglas.nivel
import copernic.cat.databinding.FragmentCrearPartidaNomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class crear_partida_nom : Fragment() {
    private var _binding: FragmentCrearPartidaNomBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearPartidaNomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = crear_partida_nomArgs.fromBundle(bundle!!)
        val nom = args.nomp

        binding.txtNombrePersonaje.text = nom

        binding.btnCrearPartida.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Usuari").document(user!!.uid)
                        .collection("Partidas").document(binding.editNombrePartida.text.toString()).set(
                            hashMapOf(
                                "numero_de_partida" to binding.editNombrePartida.text.toString(),
                                "id_personaje" to nom.toString(),
                            )
                        )
                            bd.collection("Usuari").document(user.uid)
                                .collection("Personajes").document(nom.toString()).get()
                                .addOnSuccessListener {
                                    val clase = it.get("classe") as String
                                    val trasfondo = it.get("trasfondo") as String
                                    val nivel = it.get("nivel") as String
                                    val raza = it.get("raza") as String
                                    val alineamineto = it.get("alineamiento") as String
                                    val vidaMax = it.get("vidaMax") as String
                                    val vidaActual = it.get("vidaActual") as String
                                    val fuerza = it.get("fuerza") as String
                                    val constitucion = it.get("constitucion") as String
                                    val carisma = it.get("carisma") as String
                                    val destreza = it.get("destreza") as String
                                    val inteligencia = it.get("inteligencia") as String
                                    val sabiduria = it.get("sabiduria") as String
                                    val movimiento = it.get("movimineto") as String
                                    val inici = it.get("inici") as String
                                    val dadosGolpe = it.get("dados_de_golpe") as String
                                    val salvacionExito = it.get("salvaconExito") as String
                                    val salvacionFallo = it.get("salvacionFallo") as String
                                    val ruta = it.get("rutafoto") as String
                                    val animales = it.get("trato_con_animales") as Boolean
                                    val acrobacias = it.get("acrobacias") as Boolean
                                    val arma1 = it.get("armas_y_armaduras1") as String
                                    val arma2 = it.get("armas_y_armaduras2") as String
                                    val arma3 = it.get("armas_y_armaduras3") as String
                                    val arma4 = it.get("armas_y_armaduras4") as String
                                    val arma5 = it.get("armas_y_armaduras5") as String
                                    val arma6 = it.get("armas_y_armaduras6") as String
                                    val arma7 = it.get("armas_y_armaduras7") as String
                                    val atletismo = it.get("atletismo") as Boolean
                                    val cobre = it.get("cobre") as String
                                    val comida1 = it.get("comida_y_objetos1") as String
                                    val comida2 = it.get("comida_y_objetos2") as String
                                    val comida3 = it.get("comida_y_objetos3") as String
                                    val comida4 = it.get("comida_y_objetos4") as String
                                    val comida5 = it.get("comida_y_objetos5") as String
                                    val comida6 = it.get("comida_y_objetos6") as String
                                    val comida7 = it.get("comida_y_objetos7") as String
                                    val arcano = it.get("conocimiento_arcano") as Boolean
                                    val engano = it.get("engaño") as Boolean
                                    val habilidad1 = it.get("habilidad1") as String
                                    val habilidad2 = it.get("habilidad2") as String
                                    val habilidad3 = it.get("habilidad3") as String
                                    val habilidad4 = it.get("habilidad4") as String
                                    val habilidad5 = it.get("habilidad5") as String
                                    val habilidad6 = it.get("habilidad6") as String
                                    val habilidad7 = it.get("habilidad7") as String
                                    val historia = it.get("historia") as Boolean
                                    val idioma1 = it.get("idioma1") as String
                                    val idioma2 = it.get("idioma2") as String
                                    val idioma3 = it.get("idioma3") as String
                                    val idioma4 = it.get("idioma4") as String
                                    val idioma5 = it.get("idioma5") as String
                                    val interpretacion = it.get("interpretacion") as Boolean
                                    val intimidacion = it.get("intimidacion") as Boolean
                                    val investigacion = it.get("investigacion") as Boolean
                                    val juegoManos = it.get("juego_de_manos") as Boolean
                                    val magia1 = it.get("magia1") as String
                                    val magia2 = it.get("magia2") as String
                                    val magia3 = it.get("magia3") as String
                                    val magia4 = it.get("magia4") as String
                                    val magia5 = it.get("magia5") as String
                                    val magia6 = it.get("magia6") as String
                                    val magia7 = it.get("magia7") as String
                                    val medicina = it.get("medicina") as Boolean
                                    val natura = it.get("natura") as Boolean
                                    val oro = it.get("oro") as String
                                    val percepcion = it.get("percepcion") as Boolean
                                    val perspicacia = it.get("perspicacia") as Boolean
                                    val persuasion = it.get("persuasion") as Boolean
                                    val plata = it.get("plata") as String
                                    val platino = it.get("platino") as String
                                    val religion = it.get("religion") as Boolean
                                    val sigilo = it.get("sigilo") as Boolean
                                    val supervivencia = it.get("supervivencia") as Boolean

                                bd.collection("Usuari").document(user.uid)
                                    .collection("PerPartidas")
                                    .document(binding.editNombrePartida.text.toString()).set(
                                        hashMapOf(
                                            "classe" to clase,
                                            "raza" to raza,
                                            "trasfondo" to trasfondo,
                                            "nivel" to nivel,
                                            "alineamiento" to alineamineto,
                                            "vidaMax" to vidaMax,
                                            "vidaActual" to vidaActual,
                                            "fuerza" to fuerza,
                                            "constitucion" to constitucion,
                                            "carisma" to carisma,
                                            "destreza" to destreza,
                                            "inteligencia" to inteligencia,
                                            "sabiduria" to sabiduria,
                                            "movimineto" to movimiento,
                                            "inici" to inici,
                                            "dados_de_golpe" to dadosGolpe,
                                            "salvaconExito" to salvacionExito,
                                            "salvacionFallo" to salvacionFallo,
                                            "rutafoto" to ruta,
                                            "trato_con_animales" to animales,
                                            "acrobacias" to acrobacias,
                                            "armas_y_armaduras1" to arma1,
                                            "armas_y_armaduras2" to arma2,
                                            "armas_y_armaduras3" to arma3,
                                            "armas_y_armaduras4" to arma4,
                                            "armas_y_armaduras5" to arma5,
                                            "armas_y_armaduras6" to arma6,
                                            "armas_y_armaduras7" to arma7,
                                            "atletismo" to atletismo,
                                            "cobre" to cobre,
                                            "comida_y_objetos1" to comida1,
                                            "comida_y_objetos2" to comida2,
                                            "comida_y_objetos3" to comida3,
                                            "comida_y_objetos4" to comida4,
                                            "comida_y_objetos5" to comida5,
                                            "comida_y_objetos6" to comida6,
                                            "comida_y_objetos7" to comida7,
                                            "conocimiento_arcano" to arcano,
                                            "engaño" to engano,
                                            "habilidad1" to habilidad1,
                                            "habilidad2" to habilidad2,
                                            "habilidad3" to habilidad3,
                                            "habilidad4" to habilidad4,
                                            "habilidad5" to habilidad5,
                                            "habilidad6" to habilidad6,
                                            "habilidad7" to habilidad7,
                                            "historia" to historia,
                                            "idioma1" to idioma1,
                                            "idioma2" to idioma2,
                                            "idioma3" to idioma3,
                                            "idioma4" to idioma4,
                                            "idioma5" to idioma5,
                                            "interpretacion" to interpretacion,
                                            "intimidacion" to intimidacion,
                                            "investigacion" to investigacion,
                                            "juego_de_manos" to juegoManos,
                                            "magia1" to magia1,
                                            "magia2" to magia2,
                                            "magia3" to magia3,
                                            "magia4" to magia4,
                                            "magia5" to magia5,
                                            "magia6" to magia6,
                                            "magia7" to magia7,
                                            "medicina" to medicina,
                                            "natura" to natura,
                                            "oro" to oro,
                                            "percepcion" to percepcion,
                                            "perspicacia" to perspicacia,
                                            "persuasion" to persuasion,
                                            "plata" to plata,
                                            "platino" to platino,
                                            "religion" to religion,
                                            "sigilo" to sigilo,
                                            "superviviencia" to supervivencia,
                                        )
                                    )
                            }



                }
            }
            findNavController().navigate(R.id.action_crear_partida_nom_to_inici)
            Snackbar.make(view, "Partida creada correctamente", BaseTransientBottomBar.LENGTH_SHORT
            ).show()
        }
    }
}