package copernic.cat.EditarPersonaje

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log.v
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
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
import copernic.cat.databinding.FragmentEditarPersonajeHabilidadesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editar_personaje_habilidades.newInstance] factory method to
 * create an instance of this fragment.
 */
class editar_personaje_habilidades : Fragment() {
    private var _binding: FragmentEditarPersonajeHabilidadesBinding? = null
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
        (requireActivity() as MainActivity).title = getString(R.string.editar_habilidades)
        _binding = FragmentEditarPersonajeHabilidadesBinding.inflate(inflater, container, false)
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
         * Recojemos el nombre dle personaje
         */
        val bundle = arguments
        val args = editar_personaje_habilidadesArgs.fromBundle(bundle!!)
        val nom = args.nomp

        /**
         * Corrutina que rellena los campos con la base de datos
         */
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                bd.collection("Usuari").document(user!!.uid)
                    .collection("Personajes")
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

        /**
         * Corrutina que actualiza la base de datos
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

            /**
             * Vuelve la inicio y crea al personaje
             */
            findNavController().navigate(R.id.action_editar_personaje_habilidades_to_inici)
            notification(nom.toString())
            Snackbar.make(view, getString(R.string.personaje_editado_correctamente), BaseTransientBottomBar.LENGTH_SHORT
            ).show()

        }
    }

    /**
     * Envia una notificación al usuario
     *
     * @param nom nombre del personaje
     */
    private fun notification(nom:String) {
       val notification = NotificationCompat.Builder(requireContext(),"1").also{ noti ->
           noti.setContentTitle(getString(R.string.personaje_editado))
           noti.setContentText(getString(R.string.se_a_editado_correctamente_a) + nom)
           noti.setSmallIcon(R.drawable.logo)
       }.build()
        val notificationManageer = NotificationManagerCompat.from(requireContext())
        notificationManageer.notify(1,notification)
    }
}