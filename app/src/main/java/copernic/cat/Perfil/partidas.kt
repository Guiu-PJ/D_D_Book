package copernic.cat.Perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import copernic.cat.R
import copernic.cat.RecycleViewPersonajesPerfil.AdapterListaPersonajes
import copernic.cat.RecycleViewPersonajesPerfil.ListaPersonajes
import copernic.cat.RecyclerViewPerfil.AdapterListaPerfil
import copernic.cat.RecyclerViewPerfil.ListaPerfil
import copernic.cat.databinding.FragmentEstadisticasBinding
import copernic.cat.databinding.FragmentPartidasBinding
import copernic.cat.databinding.FragmentPerfilBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class partidas : Fragment() {

    private var _binding: FragmentPartidasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartidasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.reciclerviewPartidas.layoutManager = LinearLayoutManager(context)
        binding.reciclerviewPartidas.adapter = AdapterListaPerfil(ListaPerfil.ListaPerfil)

    }


}