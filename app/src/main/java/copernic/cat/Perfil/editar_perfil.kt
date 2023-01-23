package copernic.cat.Perfil

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.databinding.FragmentEditarPerfilBinding
import copernic.cat.databinding.FragmentPerfilBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editar_perfil.newInstance] factory method to
 * create an instance of this fragment.
 */
class editar_perfil : Fragment() {

    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    val user = auth.currentUser
    private var photoSelectedUri: Uri? = null
    private var storage = FirebaseStorage.getInstance()
    private var storageRef = storage.getReference().child("image/compendios").child("foto1.jpeg")
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.editar_perfil)
        _binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llegirnovedades()
        binding.btnAceptar.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    bd.collection("Usuari").document(user!!.uid)
                        .update(
                            "usuario", binding.txtNombrePerfil.text.toString(),
                        ).await()
                }
            }
            findNavController().navigate(R.id.action_editar_perfil_to_perfil)
        }
        binding.imgEditarFoto.setOnClickListener {
            lifecycleScope.launch{
                withContext(Dispatchers.IO){
                    afegirImatge2(view)
                }
            }
        }
    }

    /**
     * Lee la base de datos y llena los campos
     */
    fun llegirnovedades(){
        bd.collection("Usuari").document(user!!.uid).get().addOnSuccessListener {
            binding.txtCorreoPerfil.setText(it.get("Email") as String)
            binding.txtNombrePerfil.setText(it.get("usuario") as String)
            if(it.get("pfp") as Boolean){
                obrirfoto()
            }
        }
    }

    val guardarImgCamera =
        registerForActivityResult(ActivityResultContracts.
        StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                photoSelectedUri = result.data?.data //Assignem l'URI de la imatge
            }
        }

    /**
     * Añade la imagen a la base de datos y la muestra al usuario
     */
    suspend fun afegirImatge2(view: View) {
        //Obrim la galeria per seleccionar la imatge  //Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        guardarImgCamera.launch(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
        storageRef = storage.reference.child("image/pfp/" + user!!.uid)
        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let { uri ->
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    binding.imgPerfilPerfil.setImageURI(uri)
                    Snackbar.make(view, getString(R.string.imagen_subida_con_exito), BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            bd.collection("Usuari").document(user.uid)
                                .update("pfp", true,).await()
                        }
                    }
                }.await()
        }
    }

    /**
     * Muestra la imagen al usuario
     */
    fun obrirfoto(){
        val storageRef = FirebaseStorage.getInstance().reference.child("image/pfp/" + user!!.uid)
        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgPerfilPerfil.setImageBitmap(bitmap)
        }
    }
}