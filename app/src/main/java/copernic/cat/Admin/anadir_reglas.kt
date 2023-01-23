package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Informacio.reglas
import copernic.cat.Inici.MainActivity
import copernic.cat.Models.Reglas
import copernic.cat.R
import copernic.cat.Utils
import copernic.cat.databinding.FragmentAnadirReglasBinding
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
 * Use the [anadir_reglas.newInstance] factory method to
 * create an instance of this fragment.
 */
class anadir_reglas : Fragment() {
    private var _binding: FragmentAnadirReglasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.anadir_regla)
        _binding = FragmentAnadirReglasBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAAdirRegla.setOnClickListener {
            val reglas = llegirDades()
            //Comprueba que no exista ya la regla que añadimos
            bd.collection("Reglas")
                .document(binding.txtTituloReglas.text.toString()).get()
                .addOnSuccessListener {
                    //Si existe avisa con un snackbar
                    if (it.exists()) {
                        Snackbar.make(view, getString(R.string.esta_regla_ya_existe), BaseTransientBottomBar.LENGTH_SHORT
                        ).show()}
                    else {
                        //Sino la añade
                        if (reglas.titulo.isNotEmpty() && reglas.descripcion.isNotEmpty()) {
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    anadirRegla(reglas)
                                }
                            }
                            findNavController().navigate(R.id.action_anadir_reglas_to_admin_inici)

                            Utils.notification(getString(R.string.regla_anadida),
                                getString(R.string.la_regla) + binding.txtTituloReglas.text.toString() +
                                        getString(R.string.se_a_anadido), requireContext())

                            Snackbar.make(view, getString(R.string.regla_anadida_correctamente),
                                BaseTransientBottomBar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        binding.btnCancelarRegla.setOnClickListener {
            findNavController().navigate(R.id.action_anadir_reglas_to_admin_inici)
        }
    }

    /**
     * Lee la base de datos
     */
    fun llegirDades(): Reglas {
        val titulo = binding.txtTituloReglas.text.toString()
        val descripcion = binding.txtDescripcionReglas.text.toString()

        return Reglas(titulo, descripcion)
    }

    /**
     * Sube el objeto reglas a la base de datos
     *
     * @param reglas objeto
     */
    private suspend fun anadirRegla(reglas: Reglas){
        bd.collection("Reglas")
            .document(binding.txtTituloReglas.text.toString()).set(
                reglas
            ).await()
    }
}