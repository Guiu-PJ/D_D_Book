package copernic.cat.Perfil

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
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.databinding.FragmentEstadisticasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Perfil_Estadisticas.newInstance] factory method to
 * create an instance of this fragment.
 */
class estadisticas : Fragment() {
    private var _binding: FragmentEstadisticasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private  lateinit var auth: FirebaseAuth
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (requireActivity() as MainActivity).title = getString(R.string.estadisticas)
        _binding = FragmentEstadisticasBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        lifecycleScope.launch{
            withContext(Dispatchers.Unconfined){//llegir dades de la base de dades
                llegirnovedades()
            }
        }

    }

    /**
     * Lee la base de datos y muestra los campos
     */
    fun llegirnovedades(){
        val user = auth.currentUser
        bd.collection("Usuari").document(user!!.uid).collection("Estadisticas").document("IdEstadisticas").get().addOnSuccessListener {
            binding.editCriticos.text = it.get("criticos") as String
            binding.editTiradas.text = it.get("numero_tiradas") as String
            binding.editPartidas.text = it.get("numero_de_partidas") as String
            binding.editPersonaje.text = it.get("numero_de_personajes") as String
        }

    }
}