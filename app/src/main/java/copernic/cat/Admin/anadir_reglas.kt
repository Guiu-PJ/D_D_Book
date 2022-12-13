package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Colecciones.Reglas
import copernic.cat.R
import copernic.cat.classes.reglas
import copernic.cat.classes.usuaris
import copernic.cat.databinding.FragmentAnadirReglasBinding
import copernic.cat.databinding.FragmentIniciBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [anadir_reglas.newInstance] factory method to
 * create an instance of this fragment.
 */
class anadir_reglas : Fragment() {
    private var _binding: FragmentAnadirReglasBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnadirReglasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAAdirRegla.setOnClickListener {
            val reglas = llegirDades()

            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Reglas")
                        .document(binding.txtTituloReglas.text.toString()).get()
                        .addOnSuccessListener {
                            if (it.exists()) {
                                Toast.makeText(context, "Aquesta regla ja existeix", Toast.LENGTH_SHORT).show()
                            } else {
                                if (reglas.tituloReglas.isNotEmpty() && reglas.descripcionReglas.isNotEmpty()) {
                                    bd.collection("Reglas")
                                        .document(binding.txtTituloReglas.text.toString()).set(
                                            hashMapOf(
                                                "titulo" to binding.txtTituloReglas.text.toString(),
                                                "Descripcion" to binding.txtDescripcionReglas.text.toString()
                                            )
                                        ).addOnSuccessListener {
                                            Snackbar.make(view, "Regla añadida correctamente", BaseTransientBottomBar.LENGTH_SHORT
                                            ).show()
                                        }.addOnFailureListener {
                                            Snackbar.make(view, "Regla no añadida", BaseTransientBottomBar.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                                findNavController().navigate(R.id.action_anadir_reglas_to_admin_inici)
                            }
                        }
                    }
                }
            }
        binding.btnCancelarRegla.setOnClickListener {
            findNavController().navigate(R.id.action_anadir_reglas_to_admin_inici)
        }
    }


    private fun llegirDades(): Reglas {
        val titulo = binding.txtTituloReglas.text.toString()
        val descripcion = binding.txtDescripcionReglas.text.toString()

        return Reglas(titulo, descripcion)
    }
}