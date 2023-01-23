package copernic.cat.Perfil

import android.R.string
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
import com.google.firebase.storage.FirebaseStorage
import copernic.cat.Ficha_Personaje.ficha_personaje_general
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecycleViewCompendios.ClassCompendios
import copernic.cat.RecycleViewCompendios.ListaCompendios
import copernic.cat.RecycleViewPersonajesPerfil.AdapterListaPersonajes
import copernic.cat.RecycleViewPersonajesPerfil.ClassListaPersonajes
import copernic.cat.RecycleViewPersonajesPerfil.ListaPersonajes
import copernic.cat.Reglas.accion
import copernic.cat.databinding.FragmentPerfilBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.A
import java.io.File

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
    private var auth: FirebaseAuth = Firebase.auth
    val user = auth.currentUser

    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.perfil)
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        binding.btnEditarPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_perfil_to_editar_perfil)
        }
        binding.btnNuevoPersonaje.setOnClickListener {
            findNavController().navigate(R.id.action_perfil_to_ficha_personaje_general)
        }
        lifecycleScope.launch{
            withContext(Dispatchers.IO){//llegir dades de la base de dades
                llegirnovedades()
            }
        }
        ListaPersonajes.ListaPersonajeslist.clear()
        //recycler view que muestra los personajes
        recycleServicios()
    }

    /**
     * Lee la base de datos y muestra los campos
     */
    fun llegirnovedades(){
        val user = auth.currentUser
        bd.collection("Usuari").document(user!!.uid).get().addOnSuccessListener {
            binding.txtCorreoPerfil2.text = it.get("Email") as String
            binding.txtNombrePerfil2.text = it.get("usuario") as String
            if(it.get("pfp") as Boolean){
                obrirfoto2()
            }
        }
    }

    /**
     * Recycler view que muestra los personajes
     */
    private fun recycleServicios() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Usuari").document(user!!.uid).collection("Personajes").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        /*val storageRef = FirebaseStorage.getInstance().reference.child("image/pfp/" + user!!.uid)
                        val localfile = File.createTempFile("tempImage", "jpeg")
                        storageRef.getFile(localfile).addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                            binding.imgPerfilPerfil2.setImageBitmap(bitmap)
                        }*/
                        //obrirfoto(document["nombre"].toString())
                        val wallItem = ClassListaPersonajes(
                            nombre = document["nombre"].toString(),
                            img = R.drawable.cachodemadera
                        )
                        if (ListaPersonajes.ListaPersonajeslist.isEmpty()) {
                            ListaPersonajes.ListaPersonajeslist.add(wallItem)
                        } else {
                            var cont = 0
                            for (i in ListaPersonajes.ListaPersonajeslist) {
                                if (wallItem.nombre == i.nombre) {
                                    cont++

                                }

                            }
                            if(cont<1){
                                ListaPersonajes.ListaPersonajeslist.add(wallItem)
                            }
                        }
                    }
                    binding.recyclerPersonajes.layoutManager = LinearLayoutManager(context)
                    binding.recyclerPersonajes.adapter = AdapterListaPersonajes(ListaPersonajes.ListaPersonajeslist.toList())
                }
            }
        }
    }

    private fun obrirfoto(nom : String): Bitmap {
        val user = auth.currentUser
        val storageRef = FirebaseStorage.getInstance().reference.child("image/personajes/" + user!!.uid + "/" + nom)
        val localfile = File.createTempFile("tempImage", "jpeg")
        //var bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
        storageRef.getFile(localfile).addOnSuccessListener{
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgPerfilPerfil2.setImageBitmap(bitmap)
            val a = ""
            return@addOnSuccessListener
        }
        //no va
        val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
        return bitmap
    }

    private fun obrirfoto2(){
        val storageRef = FirebaseStorage.getInstance().reference.child("image/pfp/" + user!!.uid)
        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgPerfilPerfil2.setImageBitmap(bitmap)
        }
    }
}