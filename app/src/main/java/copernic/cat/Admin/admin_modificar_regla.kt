package copernic.cat.Admin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.Utils
import copernic.cat.databinding.FragmentAdminModificarReglaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




class admin_modificar_regla : Fragment() {
    private var _binding: FragmentAdminModificarReglaBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.modificar_regla)
        _binding = FragmentAdminModificarReglaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** En esta variable se reciben los argumentos de otra pantalla,
        se obtiene la regla a modificar y se muestra en los campos de texto */
        val bundle = arguments
        val args = admin_modificar_reglaArgs.fromBundle(bundle!!)
        val nom = args.regla

        // Se utiliza una corrutina para obtener la regla de la base de datos y mostrarla en los campos de texto
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                bd.collection("Reglas").document(nom.toString()).get().addOnSuccessListener {
                    binding.editTituloRegla.setText(it.get("titulo") as CharSequence)
                    binding.editDescripcionRegla.setText(it.get("descripcion") as CharSequence)
                }
            }
        }

        binding.btnEditarReglaGuardar.setOnClickListener {
            bd.collection("Reglas").document(nom.toString()).delete().addOnSuccessListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        modificarRegla(nom.toString(), view)
                    }
                }
                findNavController().navigate(R.id.action_admin_modificar_regla_to_inici)
            }
        }

        binding.btnEditarReglaEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.eliminar_regla))
            builder.setMessage(getString(R.string.seguro_que_quieres_eliminar_la_regla) + nom.toString())
            builder.setNegativeButton(getString(R.string.cancelar)){
                _, _ ->
            }
            builder.setPositiveButton("OK"){_, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        eliminarRegla(nom.toString(), view)
                    }
                }
                findNavController().navigate(R.id.action_admin_modificar_regla_to_inici)
            }
            val alertDialog: AlertDialog? = builder.create()
            alertDialog!!.setCancelable(true)
            alertDialog.show()
        }
    }


    /**
     * Funcion eliminar regla
     *
     * @param nom  nombre de la regla
     * @param view  view
     *
     */
    suspend fun eliminarRegla(nom: String, view: View){
        bd.collection("Reglas").document(nom).delete().addOnSuccessListener {
            Utils.notification(getString(R.string.regla_eliminada), getString(R.string.la_regla)+ binding.editTituloRegla.text.toString() + getString(R.string.se_a_eliminado_correctamente), requireContext())
            Snackbar.make(view, getString(R.string.regla_eliminada_correctamente), BaseTransientBottomBar.LENGTH_SHORT
            ).show()
        }.await()
    }

    /**
     * Funcion modificar regla
     *
     * @param nom  nombre de la regla
     * @param view  view
     *
     */
    suspend fun modificarRegla(nom : String, view: View){
        bd.collection("Reglas").document(nom)
            .set(
                hashMapOf(
                    "descripcion" to binding.editDescripcionRegla.text.toString(),
                    "titulo" to binding.editTituloRegla.text.toString()
                )
            ).await()
        Utils.notification(getString(R.string.regla_modificada), getString(R.string.la_regla)+ binding.editTituloRegla.text.toString() + getString(R.string.se_a_modificado_correctamente), requireContext())
        Snackbar.make(view, getString(R.string.regla_modificada_correctamente), BaseTransientBottomBar.LENGTH_SHORT).show()
    }
}


