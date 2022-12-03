package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.ListaCompendios
import copernic.cat.RecyclerViewReglas.AdapterListaReglas
import copernic.cat.RecyclerViewReglas.ClassReglas
import copernic.cat.RecyclerViewReglas.ListaReglas
import copernic.cat.databinding.FragmentAdminModificarIeliminarReglasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [admin_modificarIeliminar_reglas.newInstance] factory method to
 * create an instance of this fragment.
 */
class admin_modificarIeliminar_reglas : Fragment() {
    private var _binding: FragmentAdminModificarIeliminarReglasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAdminModificarIeliminarReglasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

    }

    private fun initRecyclerView(view: View) {
        if (ListaCompendios.ListaCompendioslist.isEmpty()) {
            recycleServicios()
        } else {
            binding.recyclerReglas.layoutManager = LinearLayoutManager(context)
            binding.recyclerReglas.adapter = AdapterListaReglas(ListaReglas.ListaReglasList.toList())
        }
    }
    private fun recycleServicios() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Reglas").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = ClassReglas(
                            nombre = document["titulo"].toString()
                        )
                        if (ListaReglas.ListaReglasList.isEmpty()) {
                            ListaReglas.ListaReglasList.add(wallItem)
                        } else {
                            var cont = 0
                            for (i in ListaReglas.ListaReglasList) {
                                if (wallItem.nombre == i.nombre) {
                                    cont++
                                }
                            }
                            if(cont<1){
                                ListaReglas.ListaReglasList.add(wallItem)
                            }
                        }
                    }
                    binding.recyclerReglas.layoutManager = LinearLayoutManager(context)
                    binding.recyclerReglas.adapter = AdapterListaReglas(ListaReglas.ListaReglasList.toList())
                }
            }
        }
    }

}