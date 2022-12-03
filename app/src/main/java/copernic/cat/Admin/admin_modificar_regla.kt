package copernic.cat.Admin

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Ficha_Personaje.ficha_personaje_estadisticas_secundariasArgs
import copernic.cat.R
import copernic.cat.databinding.FragmentAdminModificarIeliminarReglasBinding
import copernic.cat.databinding.FragmentAdminModificarReglaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




class admin_modificar_regla : Fragment() {
    private var _binding: FragmentAdminModificarReglaBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminModificarReglaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val args = admin_modificar_reglaArgs.fromBundle(bundle!!)
        val nom = args.regla
        Toast.makeText(context, nom, Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            withContext(Dispatchers.Unconfined) {
                bd.collection("Reglas").document(nom.toString()).get().addOnSuccessListener {
                    binding.editTituloRegla.setText(it.get("titulo") as CharSequence)
                    binding.editDescripcionRegla.setText(it.get("Descripcion") as CharSequence)
                }
            }
        }

                binding.btnEditarReglaGuardar.setOnClickListener {
                    lifecycleScope.launch {
                        withContext(Dispatchers.Unconfined) {
                    bd.collection("Reglas").document(nom.toString()).delete().addOnSuccessListener {
                        lifecycleScope.launch {
                            withContext(Dispatchers.Unconfined) {
                                bd.collection("Reglas").document(binding.editTituloRegla.text.toString())
                                    .set(hashMapOf(
                                        "Descripcion" to binding.editDescripcionRegla.text.toString(),
                                        "titulo" to binding.editTituloRegla.text.toString()
                                    )
                                    )
                            }
                        }
                        findNavController().navigate(R.id.action_admin_modificar_regla_to_inici)
                    }
                }
            }
        }

        binding.btnEditarReglaEliminar.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Reglas").document(nom.toString()).delete().addOnSuccessListener {
                        findNavController().navigate(R.id.action_admin_modificar_regla_to_inici)
                    }
                }
            }
        }

    }
}


