package copernic.cat.Partidas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.RecycleViewPersonajesCrearPartidas.ClassPersonajesCrearPartida
import copernic.cat.RecycleViewPersonajesCrearPartidas.AdapterPersonajesCrearPartida
import copernic.cat.RecycleViewPersonajesCrearPartidas.ListaPersonajes
import copernic.cat.databinding.FragmentCrearPartidaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class crear_partida : Fragment() {
    private var _binding: FragmentCrearPartidaBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.crear_partida)
        _binding = FragmentCrearPartidaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        /**
         * Recycler view que muestra todos los personajes para escojer uno para la partida
         */
        recycleServicios()

    }

    /**
     * Recycler view que muestra todos los personajes para escojer uno para la partida
     */
    private fun recycleServicios() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                auth = Firebase.auth
                val user = auth.currentUser
                bd.collection("Usuari").document(user!!.uid).collection("Personajes").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        //obrirfoto(document["nombre"].toString())
                        val wallItem = ClassPersonajesCrearPartida(
                            nombre = document["nombre"].toString(),
                            img = R.drawable.tuerca
                        )
                        if (ListaPersonajes.ListaPersonajeslist.isEmpty()) {
                            ListaPersonajes.ListaPersonajeslist.add(wallItem)
                        } else {
                            var cont = 0
                            for (i in ListaPersonajes.ListaPersonajeslist) {
                                if (wallItem.nombre == i.nombre) {
                                    cont++
                                }
                            }
                            if(cont<1){
                                ListaPersonajes.ListaPersonajeslist.add(wallItem)
                            }
                        }
                    }
                    binding.recyclerPersonajesCrearPartida.layoutManager = LinearLayoutManager(context)
                    binding.recyclerPersonajesCrearPartida.adapter = AdapterPersonajesCrearPartida(ListaPersonajes.ListaPersonajeslist.toList())
                }
            }
        }
    }

}