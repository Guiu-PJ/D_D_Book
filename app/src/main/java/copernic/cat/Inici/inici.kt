package copernic.cat.Inici

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
import copernic.cat.Reglas.accion
import copernic.cat.databinding.FragmentIniciBinding
import hotchemi.android.rate.AppRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [accion.newInstance] factory method to
 * create an instance of this fragment.
 */

class inici : Fragment() {
    private var _binding: FragmentIniciBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    val user = auth.currentUser
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.inicio)
        _binding = FragmentIniciBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppRate.with(requireActivity()).setInstallDays(0).setLaunchTimes(2).setRemindInterval(1).monitor()
        //Es mostra el diàleg si es compleix alguna de les condicions
        AppRate.showRateDialogIfMeetsConditions(requireActivity())

        binding.btnNovedades.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_novedades)
        }

        binding.btnReglas.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_reglas)
        }

        binding.btnDados.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_dados)
        }

        binding.btnAdmin.setOnClickListener{
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    admin(view)
                }
            }
        }

        /* binding.btnCompendios.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_compendios)
        }*/

    }

    /**
     * Comprueba si el usuario es admin o no
     *
     * @param view view
     */
    suspend fun admin(view : View){
        bd.collection("Usuari").document(user!!.uid).get().addOnSuccessListener {
            if(it.get("Admin") as Boolean){
                findNavController().navigate(R.id.action_inici_to_admin_inici)
            }else {
                Snackbar.make(view, getString(R.string.no_eres_admin), BaseTransientBottomBar.LENGTH_SHORT).show()
            }
        }.await()
    }
}