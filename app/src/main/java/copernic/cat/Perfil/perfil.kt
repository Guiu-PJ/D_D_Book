package copernic.cat.Perfil

import android.R.string
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import copernic.cat.Ficha_Personaje.ficha_personaje_general
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecycleViewCompendios.ListaCompendios
import copernic.cat.RecycleViewPersonajesPerfil.AdapterListaPersonajes
import copernic.cat.RecycleViewPersonajesPerfil.ListaPersonajes
import copernic.cat.Reglas.accion
import copernic.cat.databinding.FragmentPerfilBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [accion.newInstance] factory method to
 * create an instance of this fragment.
 */

class perfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private  lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        binding.btnEditarPerfil.setOnClickListener {
            (activity as MainActivity?)?.cambiarPantalla(editar_perfil())
            //findNavController().navigate(R.id.action_perfil_to_editar_perfil)
        }
        lifecycleScope.launch{
            withContext(Dispatchers.Unconfined){//llegir dades de la base de dades
                llegirnovedades()
            }
        }
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.recyclerPersonajes.layoutManager = LinearLayoutManager(context)
        binding.recyclerPersonajes.adapter = AdapterListaPersonajes(ListaPersonajes.ListaPersonajeslist)

    }

    fun llegirnovedades(){
        //Al usar el menu deja de detectar el usuario de firebase y por lo tanto no puedes recojer su id
        val user = auth.currentUser
        bd.collection("Usuari").document(user!!.uid).get().addOnSuccessListener {
            binding.txtCorreoPerfil2.text = it.get("Email") as String
            val str = binding.txtCorreoPerfil2.text.toString()
            val nom = str.split("@")
            binding.txtNombrePerfil2.text = nom[0].toString()
        }
        //bd.collection("Novedades").document("Eliminado").get().addOnSuccessListener {
            //binding.txtInfoEliminado.text = it.get("eliminado") as String?
        //}
        //bd.collection("Novedades").document("Modificado").get().addOnSuccessListener {
            //binding.txtInfoModificado.text = it.get("modificado") as String?
        //}
    }

}