package copernic.cat.Partidas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
import copernic.cat.databinding.FragmentEditarPersonajeHabilidadesBinding
import copernic.cat.databinding.FragmentModificarPartidaHabilidadesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [modificar_partida_habilidades.newInstance] factory method to
 * create an instance of this fragment.
 */
class modificar_partida_habilidades : Fragment() {
    private var _binding: FragmentModificarPartidaHabilidadesBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentModificarPartidaHabilidadesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = modificar_partida_habilidadesArgs.fromBundle(bundle!!)
        val nom = args.nomp

        lifecycleScope.launch {
            withContext(Dispatchers.Unconfined) {
                bd.collection("Usuari").document(user!!.uid)
                    .collection("PerPartidas")
                    .document(nom.toString()).get()
                    .addOnSuccessListener {
                        binding.editHabilidades.setText(it.get("habilidad1") as String)
                        binding.editHabilidades2.setText(it.get("habilidad2") as String)
                        binding.editHabilidades3.setText(it.get("habilidad3") as String)
                        binding.editHabilidades4.setText(it.get("habilidad4") as String)
                        binding.editHabilidades5.setText(it.get("habilidad5") as String)
                        binding.editHabilidades6.setText(it.get("habilidad6") as String)
                        binding.editHabilidades7.setText(it.get("habilidad7") as String)
                        binding.editMagia.setText(it.get("magia1") as String)
                        binding.editMagia2.setText(it.get("magia2") as String)
                        binding.editMagia3.setText(it.get("magia3") as String)
                        binding.editMagia4.setText(it.get("magia4") as String)
                        binding.editMagia5.setText(it.get("magia5") as String)
                        binding.editMagia6.setText(it.get("magia6") as String)
                        binding.editMagia7.setText(it.get("magia7") as String)
                    }
            }
        }
        binding.btnSiguienteFichaPersonajeHabilidades.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Usuari").document(user!!.uid)
                        .collection("PerPartidas")
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
            findNavController().navigate(R.id.action_modificar_partida_habilidades_to_inici)
            notification(nom.toString())
            Snackbar.make(view, "Partida editada correctamente", BaseTransientBottomBar.LENGTH_SHORT
            ).show()
        }
    }
    private fun notification(nom:String) {
        val notification = NotificationCompat.Builder(requireContext(),"1").also{ noti ->
            noti.setContentTitle("Partida editada")
            noti.setContentText("Se a editado correctamente la partida: " + nom)
            noti.setSmallIcon(R.drawable.logo)
        }.build()

        val notificationManageer = NotificationManagerCompat.from(requireContext())
        notificationManageer.notify(1,notification)
    }
}