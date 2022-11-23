package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.Colecciones.Reglas
import copernic.cat.R
import copernic.cat.classes.reglas
import copernic.cat.classes.usuaris
import copernic.cat.databinding.FragmentAnadirReglasBinding
import copernic.cat.databinding.FragmentIniciBinding

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
    ): View? {
        _binding = FragmentAnadirReglasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAAdirRegla.setOnClickListener {
            val reglas = llegirDades()

            if(reglas.tituloReglas.isNotEmpty() && reglas.descripcionReglas.isNotEmpty()){
                bd.collection("Reglas").document(binding.txtTituloReglas.text.toString()).set(hashMapOf("Titulo" to binding.txtTituloReglas.text.toString(), "Descripcion" to binding.txtDescripcionReglas.text.toString())).addOnSuccessListener {
                    //val builder = AlertDialog.Builder(this)
                    //builder.setTitle("Correcto")
                    //builder.setMessage("Se a añadido la regla correctamente")
                    //builder.setPositiveButton("OK"){_, _ ->
                    //}
                }.addOnFailureListener{
                    //val builder = AlertDialog.Builder(this)
                    //builder.setTitle("Error")
                    //builder.setMessage("No se a podido añadir la regla")
                    //builder.setPositiveButton("OK"){_, _ ->
                    //}
                }
            }
            findNavController().navigate(R.id.action_anadir_reglas_to_inici)
        }
        binding.btnCancelarRegla.setOnClickListener {
            findNavController().navigate(R.id.action_anadir_reglas_to_inici)
        }
    }


    fun llegirDades(): Reglas {
        val titulo =binding.txtTituloReglas.text.toString()
        val descripcion =binding.txtDescripcionReglas.text.toString()

        return Reglas(titulo, descripcion)
    }
}