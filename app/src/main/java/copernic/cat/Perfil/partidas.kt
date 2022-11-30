package copernic.cat.Perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecycleViewCompendios.ClassCompendios
import copernic.cat.RecycleViewCompendios.ListaCompendios
import copernic.cat.RecycleViewPersonajesPerfil.AdapterListaPersonajes
import copernic.cat.RecycleViewPersonajesPerfil.ListaPersonajes
import copernic.cat.RecyclerViewPerfil.AdapterListaPerfil
import copernic.cat.RecyclerViewPerfil.ClassPerfil
import copernic.cat.RecyclerViewPerfil.ListaPerfil
import copernic.cat.databinding.FragmentEstadisticasBinding
import copernic.cat.databinding.FragmentPartidasBinding
import copernic.cat.databinding.FragmentPerfilBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class partidas : Fragment() {

    private var _binding: FragmentPartidasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private  lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartidasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        if (ListaPerfil.ListaPerfil.isEmpty()) {
            recycleServicios()
        } else {
            binding.reciclerviewPartidas.layoutManager = LinearLayoutManager(context)
            binding.reciclerviewPartidas.adapter = AdapterListaPerfil(ListaPerfil.ListaPerfil.toList())
        }
    }

    private fun recycleServicios() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                auth = Firebase.auth
                val user = auth.currentUser
                bd.collection("Usuari").document(user!!.uid).collection("Partidas").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = ClassPerfil(
                            nombre = document["id_partida"].toString(),
                            personaje = document["id_personaje"].toString()
                        )
                        if (ListaPerfil.ListaPerfil.isEmpty()) {
                            ListaPerfil.ListaPerfil.add(wallItem)
                        } else {
                            var cont = 0
                            for (i in ListaPerfil.ListaPerfil) {
                                if (wallItem.nombre == i.nombre) {
                                    cont++
                                }
                            }
                            if(cont<1){
                                ListaPerfil.ListaPerfil.add(wallItem)
                            }
                        }
                    }
                    binding.reciclerviewPartidas.layoutManager = LinearLayoutManager(context)
                    binding.reciclerviewPartidas.adapter = AdapterListaPerfil(ListaPerfil.ListaPerfil.toList())
                }
            }
        }
    }
}