package copernic.cat.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
import copernic.cat.databinding.FragmentAdminIniciBinding
import copernic.cat.databinding.FragmentIniciBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [admin_inici.newInstance] factory method to
 * create an instance of this fragment.
 */
class admin_inici : Fragment() {
    private var _binding: FragmentAdminIniciBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminIniciBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        binding.btnNovedades.setOnClickListener {
            findNavController().navigate(R.id.action_admin_inici_to_novedades_admin)
        }
        binding.btnCompendios.setOnClickListener {
            findNavController().navigate(R.id.action_admin_inici_to_anadir_compendios)
        }
        binding.btnReglas.setOnClickListener {
            findNavController().navigate(R.id.action_admin_inici_to_anadir_reglas)
        }
        binding.btnUsuarios.setOnClickListener {
            findNavController().navigate(R.id.action_admin_inici_to_usuarios_admin)
        }

    }
}