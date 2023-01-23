package copernic.cat.Ficha_Personaje

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import copernic.cat.Inici.MainActivity
import copernic.cat.Models.PersonajeGeneral
import copernic.cat.Models.Reglas
import copernic.cat.Models.compendios
import copernic.cat.R
import copernic.cat.databinding.FragmentFichaPersonajeGeneralBinding
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
 * Use the [ficha_personaje_general.newInstance] factory method to
 * create an instance of this fragment.
 */
class ficha_personaje_general : Fragment() {
    private var _binding: FragmentFichaPersonajeGeneralBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private var photoSelectedUri: Uri? = null
    private var user: FirebaseUser? = null
    private var storage = FirebaseStorage.getInstance()
    private var storageRef = storage.getReference().child("image/compendios").child("foto1.jpeg")

    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.crear_personaje)
        _binding = FragmentFichaPersonajeGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        user = auth.currentUser
        binding.imgCrearPersonaje.setOnClickListener {
            if(binding.editPersonajeNombre.text.toString().isEmpty()){
                Snackbar.make(view, getString(R.string.primero_pon_un_nombre_al_personaje), BaseTransientBottomBar.LENGTH_SHORT
                ).show()
            }else{
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        afegirImatge2(view)
                    }
                }
            }
        }

        /**
         * Pasamos a la siguiente pantalla
         */
        binding.btnSiguienteFichaPersonajeGeneral.setOnClickListener {
            if (binding.editPersonajeNombre.text.toString().isNotEmpty()) {
                bd.collection("Usuari").document(user!!.uid)
                    .collection("Personajes")
                    .document(binding.editPersonajeNombre.text.toString()).get()
                    .addOnSuccessListener { it ->
                        if (it.exists()) {
                            Snackbar.make(view, getString(R.string.este_personaje_ya_existe), BaseTransientBottomBar.LENGTH_SHORT
                            ).show()
                        } else {
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    anadirPersonajeGeneral()
                                }
                            }
                            /*bd.collection("Usuari").document(user!!.uid)
                                .collection("Personajes")
                                .document(binding.editPersonajeNombre.text.toString()).set(
                                    hashMapOf(
                                        "nombre" to binding.editPersonajeNombre.text.toString(),
                                        "classe" to binding.editPersonajeClasse.text.toString(),
                                        "raza" to binding.editPersonajeRaza.text.toString(),
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
                                        "rutafoto" to "image/personajes/" + user!!.uid + "/" + binding.editPersonajeNombre.text.toString(),
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
                                        "conocimiento_arcano" to false,
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
                                        "trato_con_animales" to false,
                                        "idioma1" to "",
                                        "idioma2" to "",
                                        "idioma3" to "",
                                        "idioma4" to "",
                                        "idioma5" to "",
                                    )
                                )*/

                            /**
                             * Enviamos el nombre del personaje
                             */
                            val nomp = binding.editPersonajeNombre.text.toString()
                            val directions =
                                ficha_personaje_generalDirections.actionFichaPersonajeGeneralToFichaPersonajeEquipamiento(nomp)

                            findNavController().navigate(directions)
                            bd.collection("Usuari").document(user!!.uid).collection("Estadisticas")
                                .document("IdEstadisticas").get().addOnSuccessListener {
                                val per = it.get("numero_de_personajes") as String?
                                val num = per!!.toInt() + 1
                                bd.collection("Usuari").document(user!!.uid)
                                    .collection("Estadisticas").document("IdEstadisticas")
                                    .update("numero_de_personajes", (num).toString())
                            }


                        }
                    }
            }


        }
    }

    private val guardarImgCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                photoSelectedUri = result.data?.data //Assignem l'URI de la imatge
            }
        }

    /**
     * Añadimos la imagen a la base de datos y la mostramos
     */
    suspend fun afegirImatge2(view: View) {
        //Obrim la galeria per seleccionar la imatge  //Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        guardarImgCamera.launch(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
        storageRef = storage.reference.child("image/personajes/" + user!!.uid)
            .child(binding.editPersonajeNombre.text.toString())
        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let { uri ->
            storageRef.putFile(uri)
                .addOnCompleteListener{
                    Snackbar.make(
                        view, getString(R.string.imagen_subida_correctamente), BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
                    binding.imgCrearPersonaje.setImageURI(uri)
                }.await()
        }
    }

    /**
     * Llenamos le objeto PersonajeGeneral
     *
     * @return PersonajeGeneral
     */
    private fun llegirDades(): PersonajeGeneral {
        val nombre = binding.editPersonajeNombre.text.toString()
        val classe = binding.editPersonajeClasse.text.toString()
        val raza = binding.editPersonajeRaza.text.toString()
        val trasfondo = binding.editPersonajeTrasfondo.text.toString()
        val nivel = binding.editPersonajeNivel.text.toString()
        val alineamiento = binding.editPersonajeAlineamiento.text.toString()
        val vidaMax = binding.editPersonajeVidamax.text.toString()
        val vidaActual = binding.editPersonajeVidaactual.text.toString()
        val fuerza = binding.editFue.text.toString()
        val constitucion = binding.editCon.text.toString()
        val carisma = binding.editCar.text.toString()
        val destreza = binding.editDes.text.toString()
        val inteligencia = binding.editInt.text.toString()
        val sabiduria = binding.editSab.text.toString()
        val movimiento = binding.editMov.text.toString()
        val inici = binding.editInici.text.toString()
        val dados_de_golpe = binding.editDadosDeGolpe.text.toString()
        val salvacionExito = binding.editExito.text.toString()
        val salvacionFallo = binding.editFallo.text.toString()
        val rutafoto = "image/personajes/" + user!!.uid + "/" + binding.editPersonajeNombre.text.toString()



        return PersonajeGeneral(
            nombre,
            classe,
            raza,
            trasfondo,
            nivel,
            alineamiento,
            vidaMax,
            vidaActual,
            fuerza,
            constitucion,
            carisma,
            destreza,
            inteligencia,
            sabiduria,
            movimiento,
            inici,
            dados_de_golpe,
            salvacionExito,
            salvacionFallo,
            rutafoto
        )
    }

    /**
     * Añadimos le personaje a la base de datos
     */
    suspend fun anadirPersonajeGeneral() {
        val personajeGeneral = llegirDades()
        bd.collection("Usuari").document(user!!.uid)
            .collection("Personajes")
            .document(binding.editPersonajeNombre.text.toString()).set(
                //personajeGeneral
            hashMapOf(
                "nombre" to binding.editPersonajeNombre.text.toString(),
                "classe" to binding.editPersonajeClasse.text.toString(),
                "raza" to binding.editPersonajeRaza.text.toString(),
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
                "rutafoto" to "image/personajes/" + user!!.uid + "/" + binding.editPersonajeNombre.text.toString(),
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
                "conocimiento_arcano" to false,
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
                "trato_con_animales" to false,
                "idioma1" to "",
                "idioma2" to "",
                "idioma3" to "",
                "idioma4" to "",
                "idioma5" to "",
            )

            ).await()
    }
}