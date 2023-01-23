package copernic.cat.Ficha_Personaje

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
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.databinding.FragmentFichaPersonajeHabilidadesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ficha_personaje_habilidades.newInstance] factory method to
 * create an instance of this fragment.
 */
class ficha_personaje_habilidades : Fragment() {
    private var _binding: FragmentFichaPersonajeHabilidadesBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (requireActivity() as MainActivity).title = "Habilidades"
        _binding = FragmentFichaPersonajeHabilidadesBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        /**
         * Recojemos el nombre del personaje
         */
        val bundle = arguments
        val args = ficha_personaje_estadisticas_secundariasArgs.fromBundle(bundle!!)
        val nom = args.nomPers

        /**
         * Boton para pasar a la siguiente pantalla
         */
        binding.btnSiguienteFichaPersonajeHabilidades.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                        bd.collection("Usuari").document(user!!.uid)
                        .collection("Personajes")
                        .document(nom.toString()).update(
                            "habilidad1", binding.editHabilidades.text.toString(),
                            "habilidad2", binding.editHabilidades2.text.toString(),
                            "habilidad3", binding.editHabilidades3.text.toString(),
                            "habilidad4", binding.editHabilidades4.text.toString(),
                            "habilidad5", binding.editHabilidades5.text.toString(),
                            "habilidad6", binding.editHabilidades6.text.toString(),
                            "habilidad7", binding.editHabilidades7.text.toString(),
                            "magia1", binding.editMagia.text.toString(),
                            "magia2", binding.editMagia2.text.toString(),
                            "magia3", binding.editMagia3.text.toString(),
                            "magia4", binding.editMagia4.text.toString(),
                            "magia5", binding.editMagia5.text.toString(),
                            "magia6", binding.editMagia6.text.toString(),
                            "magia7", binding.editMagia7.text.toString(),
                        )
                }
            }
            findNavController().navigate(R.id.action_ficha_personaje_habilidades_to_inici)
            Snackbar.make(view, getString(R.string.personaje_creado_correctamente), BaseTransientBottomBar.LENGTH_SHORT
            ).show()
        }
    }

}