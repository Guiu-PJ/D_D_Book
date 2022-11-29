package copernic.cat.Informacio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecycleViewCompendios.ClassCompendios
import copernic.cat.RecycleViewCompendios.ListaCompendios
import copernic.cat.RecycleViewPersonajesPerfil.AdapterListaPersonajes
import copernic.cat.RecycleViewPersonajesPerfil.ClassListaPersonajes
import copernic.cat.RecycleViewPersonajesPerfil.ListaPersonajes
import copernic.cat.databinding.FragmentCompendiosBinding
import copernic.cat.databinding.FragmentDadosBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [compendios.newInstance] factory method to
 * create an instance of this fragment.
 */
class compendios : Fragment() {
    private var _binding: FragmentCompendiosBinding? = null
    private val binding get() = _binding!!
    private lateinit var userList: ArrayList<ClassCompendios>
    private lateinit var adapterCompendios: AdapterListaCompendios
    private var bd = FirebaseFirestore.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCompendiosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        //initRecyclerView2()
    }

    private fun initRecyclerView(){
        binding.recyclerCompendios.layoutManager = LinearLayoutManager(context)
        binding.recyclerCompendios.adapter = AdapterListaCompendios(ListaCompendios.ListaCompendioslist)
    }

    private fun initRecyclerView2(){
        userList = ArrayList()
        adapterCompendios = AdapterListaCompendios(userList)
        bd.collection("Compendios").get().addOnSuccessListener{ documents ->
            for(document in documents){
                val wallItem = document.toObject(ClassCompendios::class.java)
                wallItem.nombre = document["Nombre"].toString()
                binding.recyclerCompendios.adapter = adapterCompendios
                binding.recyclerCompendios.layoutManager = LinearLayoutManager(context)
                userList.add(wallItem)
            }

        }

        //binding.recyclerCompendios.layoutManager = LinearLayoutManager(context)
        //binding.recyclerCompendios.adapter = AdapterListaCompendios(ListaCompendios.ListaCompendioslist)

    }


}