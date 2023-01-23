package copernic.cat.EditarPersonaje

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import copernic.cat.Ficha_Personaje.ficha_personaje_equipamientoArgs
import copernic.cat.Inici.MainActivity
import copernic.cat.Perfil.perfilDirections
import copernic.cat.R
import copernic.cat.databinding.FragmentEditarOEliminarPersonajeBinding
import copernic.cat.databinding.FragmentFichaPersonajeEquipamientoBinding
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
 * Use the [editarOEliminarPersonaje.newInstance] factory method to
 * create an instance of this fragment.
 */
class editarOEliminarPersonaje : Fragment() {
    private var _binding: FragmentEditarOEliminarPersonajeBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth= Firebase.auth
    val user = auth.currentUser
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.editar_o_eliminar_personaje)
        _binding = FragmentEditarOEliminarPersonajeBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Recoje el nombre del personaje
         */
        val bundle = arguments
        val args = editarOEliminarPersonajeArgs.fromBundle(bundle!!)
        val nom = args.nomp

        binding.txtNomPersonatge.text = nom

        /**
         * Boton para ir a editar personaje
         */
        binding.btnEditarPersonatge.setOnClickListener {
            val action =
                editarOEliminarPersonajeDirections.actionEditarOEliminarPersonajeToEditarPersonajeGeneral(
                    nom
                )
            view.findNavController().navigate(action)
        }

        /**
         * Eliminas al personaje aunque antes sale un alert dialog preguntando si estas seguro
         */
        binding.btnEliminarPersonatge.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.eliminar_personaje))
            builder.setMessage(getString(R.string.seguro_que_quieres_eliminar_el_personaje) + nom.toString())
            builder.setNegativeButton(getString(R.string.cancelar)) { _, _ ->
            }
            builder.setPositiveButton("OK") { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        bd.collection("Usuari").document(user!!.uid)
                            .collection("Personajes")
                            .document(nom.toString()).delete().addOnSuccessListener {
                                lifecycleScope.launch {
                                    withContext(Dispatchers.IO) {
                                        bd.collection("Usuari").document(user.uid)
                                            .collection("Estadisticas")
                                            .document("IdEstadisticas").get().addOnSuccessListener {
                                                val per = it.get("numero_de_personajes") as String?
                                                val num = per!!.toInt() - 1
                                                bd.collection("Usuari").document(user.uid)
                                                    .collection("Estadisticas")
                                                    .document("IdEstadisticas")
                                                    .update(
                                                        "numero_de_personajes",
                                                        (num).toString()
                                                    )
                                            }
                                    }
                                    Snackbar.make(
                                        view,
                                        getString(R.string.personaje_eliminado_correctamente),
                                        BaseTransientBottomBar.LENGTH_SHORT
                                    ).show()
                                    findNavController().navigate(R.id.action_editarOEliminarPersonaje_to_inici)
                                }
                            }
                    }
                }
            }
            val alertDialog: AlertDialog? = builder.create()
            alertDialog!!.setCancelable(true)
            alertDialog.show()
        }
    }
}