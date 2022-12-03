package copernic.cat.Inici

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
import copernic.cat.Reglas.accion
import copernic.cat.databinding.FragmentIniciBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [accion.newInstance] factory method to
 * create an instance of this fragment.
 */

class inici : Fragment() {
    private var _binding: FragmentIniciBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private  lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIniciBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        binding.btnNovedades.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_novedades)
        }

        binding.btnCompendios.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_compendios)
        }

        binding.btnReglas.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_reglas)
        }

        binding.btnDados.setOnClickListener{
            findNavController().navigate(R.id.action_inici_to_dados)
        }

        binding.btnAdmin.setOnClickListener{
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined){//llegir dades de la base de dades
                    bd.collection("Usuari").document(user!!.uid).get().addOnSuccessListener {
                        if(it.get("Admin") as Boolean){
                            findNavController().navigate(R.id.action_inici_to_admin_inici)
                        }else {
                            Toast.makeText(context, "No ets admin", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}