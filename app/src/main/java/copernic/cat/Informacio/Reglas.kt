package copernic.cat.Informacio

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.RecycleViewReglasUsuario.AdapterListaReglasUsuario
import copernic.cat.RecycleViewReglasUsuario.ClassReglasUsuario
import copernic.cat.RecycleViewReglasUsuario.ListaReglasUsuarios
import copernic.cat.RecyclerViewReglas.AdapterListaReglas
import copernic.cat.RecyclerViewReglas.ClassReglas
import copernic.cat.RecyclerViewReglas.ListaReglas
import copernic.cat.databinding.FragmentReglasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class reglas : Fragment() {
    private var _binding: FragmentReglasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.reglas)
        _binding = FragmentReglasBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recycler view que muestra toda la lista de reglas
        recycleServicios()
    }

    /**
     * Recycler view que muestra todas las reglas
     */
    private fun recycleServicios() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Reglas").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = ClassReglasUsuario(
                            nombre = document["titulo"].toString()
                        )
                        if (ListaReglasUsuarios.ListaReglasUsuariosList.isEmpty()) {
                            ListaReglasUsuarios.ListaReglasUsuariosList.add(wallItem)
                        } else {
                            var cont = 0
                            for (i in ListaReglasUsuarios.ListaReglasUsuariosList) {
                                if (wallItem.nombre == i.nombre) {
                                    cont++
                                }
                            }
                            if(cont<1){
                                ListaReglasUsuarios.ListaReglasUsuariosList.add(wallItem)
                            }
                        }
                    }
                    binding.RecyclerReglasUsuario.layoutManager = LinearLayoutManager(context)
                    binding.RecyclerReglasUsuario.adapter = AdapterListaReglasUsuario(ListaReglasUsuarios.ListaReglasUsuariosList.toList())
                }
            }
        }
    }
}
