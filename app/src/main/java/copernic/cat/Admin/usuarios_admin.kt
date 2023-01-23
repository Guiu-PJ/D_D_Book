package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecycleViewCompendios.ClassCompendios
import copernic.cat.RecycleViewCompendios.ListaCompendios
import copernic.cat.RecyclerViewAdminUsuarios.AdapterListaAdminUsuarios
import copernic.cat.RecyclerViewAdminUsuarios.ClassAdminUsuarios
import copernic.cat.RecyclerViewAdminUsuarios.ListaAdminUsuarios
import copernic.cat.databinding.FragmentCompendiosBinding
import copernic.cat.databinding.FragmentUsuariosAdminBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private var bd = FirebaseFirestore.getInstance()

/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }*/
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (requireActivity() as MainActivity).title = getString(R.string.usuarioss)
        _binding = FragmentUsuariosAdminBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListaAdminUsuarios.ListaAdminUsuarioslista.clear()
        recycleServicios()
    }

    /**
     * Recycler view que muestra todos los usuarios
     */
    private fun recycleServicios() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Usuari").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = ClassAdminUsuarios(
                            correo = document["Email"].toString(),
                            image = R.drawable.calamardo
                        )
                        if (ListaAdminUsuarios.ListaAdminUsuarioslista.isEmpty()) {
                            ListaAdminUsuarios.ListaAdminUsuarioslista.add(wallItem)
                        } else {
                            var cont = 0
                            for (i in ListaAdminUsuarios.ListaAdminUsuarioslista) {
                                if (wallItem.correo == i.correo) {
                                    cont++

                                }

                            }
                            if(cont<1){
                                ListaAdminUsuarios.ListaAdminUsuarioslista.add(wallItem)
                            }
                        }
                    }
                    binding.recuclerViewAdminUsuarios.layoutManager = LinearLayoutManager(context)
                    binding.recuclerViewAdminUsuarios.adapter = AdapterListaAdminUsuarios(ListaAdminUsuarios.ListaAdminUsuarioslista.toList())
                }
            }
        }
    }
}