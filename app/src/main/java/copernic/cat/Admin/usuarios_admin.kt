package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecycleViewCompendios.ListaCompendios
import copernic.cat.RecyclerViewAdminUsuarios.AdapterListaAdminUsuarios
import copernic.cat.RecyclerViewAdminUsuarios.ListaAdminUsuarios
import copernic.cat.databinding.FragmentCompendiosBinding
import copernic.cat.databinding.FragmentUsuariosAdminBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [usuarios_admin.newInstance] factory method to
 * create an instance of this fragment.
 */
class usuarios_admin : Fragment() {
    private var _binding: FragmentUsuariosAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsuariosAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }



    private fun initRecyclerView(){
        binding.recuclerViewAdminUsuarios.layoutManager = LinearLayoutManager(context)
        binding.recuclerViewAdminUsuarios.adapter = AdapterListaAdminUsuarios(ListaAdminUsuarios.ListaAdminUsuarioslista)

    }
}