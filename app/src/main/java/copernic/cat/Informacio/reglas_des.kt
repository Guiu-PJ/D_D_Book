package copernic.cat.Informacio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Admin.admin_modificar_reglaArgs
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.databinding.FragmentAdminModificarReglaBinding
import copernic.cat.databinding.FragmentReglasBinding
import copernic.cat.databinding.FragmentReglasDesBinding
import copernic.cat.databinding.FragmentUsuariosAdminBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [reglas_des.newInstance] factory method to
 * create an instance of this fragment.
 */
class reglas_des : Fragment() {
    private var _binding: FragmentReglasDesBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.reglas)
        _binding = FragmentReglasDesBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** En este método se reciben los argumentos de otra pantalla,
        se obtiene la regla a modificar y se muestra en los campos de texto */
        val bundle = arguments
        val args = reglas_desArgs.fromBundle(bundle!!)
        val nom = args.nom

        binding.txtNombreReglaDes.text = nom

        /**
         * Lee la base de datos usando el nombre de la regla y lo muestra
         */
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
            bd.collection("Reglas").document(nom.toString()).get().addOnSuccessListener {
                binding.txtDescripcionReglaDes.text = it.get("descripcion") as CharSequence
            }


            }
        }
    }

}