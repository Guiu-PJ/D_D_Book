package copernic.cat.Partidas

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.EditarPersonaje.editarOEliminarPersonajeArgs
import copernic.cat.EditarPersonaje.editarOEliminarPersonajeDirections
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.databinding.FragmentCrearPartidaNomBinding
import copernic.cat.databinding.FragmentEditarOEliminarPersonajeBinding
import copernic.cat.databinding.FragmentModificarOEliminarPartidaBinding
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
 * Use the [modificar_o_eliminar_partida.newInstance] factory method to
 * create an instance of this fragment.
 */
class modificar_o_eliminar_partida : Fragment() {
    private var _binding: FragmentModificarOEliminarPartidaBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private var user: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.modificar_o_eliminar_partida)
        _binding = FragmentModificarOEliminarPartidaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        user = auth.currentUser

        /**
         * Recoje el nombre de la partida
         */
        val bundle = arguments
        val args = editarOEliminarPersonajeArgs.fromBundle(bundle!!)
        val nom = args.nomp

        binding.txtNomPartida.text = nom


        /**
         * te lleva a la pantalla editar partida y le pasa el nombre de la partida
         */
        binding.btnEditarPartida.setOnClickListener {
            val action = modificar_o_eliminar_partidaDirections.actionModificarOEliminarPartidaToModificarPartidaGeneral(nom)
            view.findNavController().navigate(action)
        }

        /**
         * Pregunta si estas seguro de eliminar al personaje y lo elimina si pulsas si
         */
        binding.btnEliminarPartida.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.eliminar_partida))
            builder.setMessage(getString(R.string.seguro_que_quieres_eliminar_la_partida) + nom.toString())
            builder.setNegativeButton(getString(R.string.cancelar)){
                    _, _ ->
            }
            builder.setPositiveButton("OK"){_, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        borrarPartida(nom.toString())
                    }
                }
                /**
                 * Actualiza la bd y vuelve al inicio
                 */
                findNavController().navigate(R.id.action_modificar_o_eliminar_partida_to_inici)
                bd.collection("Usuari").document(user!!.uid).collection("Estadisticas")
                    .document("IdEstadisticas").get().addOnSuccessListener {
                        val per = it.get("numero_de_partidas") as String?
                        val num = per!!.toInt() - 1
                        bd.collection("Usuari").document(user!!.uid)
                            .collection("Estadisticas").document("IdEstadisticas")
                            .update("numero_de_partidas", (num).toString())
                    }
            }
            val alertDialog: AlertDialog? = builder.create()
            alertDialog!!.setCancelable(true)
            alertDialog.show()
        }
    }

    /**
     * Borra la partida de la base de datos
     */
    suspend fun borrarPartida(nom: String){
        bd.collection("Usuari").document(user!!.uid).collection("PerPartidas")
            .document(nom).delete().await()
        bd.collection("Usuari").document(user!!.uid).collection("Partidas")
            .document(nom).delete().await()
        //findNavController().navigate(R.id.action_modificar_o_eliminar_partida_to_partidas)
    }
}