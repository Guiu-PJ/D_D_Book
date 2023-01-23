package copernic.cat.Admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import copernic.cat.Inici.MainActivity
import copernic.cat.R
import copernic.cat.Models.compendios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import copernic.cat.Utils
import copernic.cat.databinding.FragmentAnadirCompendiosBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [anadir_reglas.newInstance] factory method to
 * create an instance of this fragment.
 */
class anadir_compendios : Fragment() {
    private var _binding: FragmentAnadirCompendiosBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private var photoSelectedUri: Uri? = null
    private var storage = FirebaseStorage.getInstance()
    private var storageRef = storage.getReference().child("image/compendios").child("foto1.jpeg")
    var nom = ""
    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (requireActivity() as MainActivity).title = getString(R.string.anadir_compendio)
        _binding = FragmentAnadirCompendiosBinding.inflate(inflater, container, false)
        return binding.root

    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgAAdirCompendios.setImageResource(R.drawable.cachodemadera)
        super.onViewCreated(view, savedInstanceState)

        binding.btnAAdirFotoCompendio.setOnClickListener {
            val a = binding.txtAAdirNombreCompendio.text.toString()
            Toast.makeText(context, a, Toast.LENGTH_SHORT).show()
            if(a.isNotEmpty()) {
                afegirImatge2()
            }else{
                Snackbar.make(view, getString(R.string.primero_nombre_compendio), BaseTransientBottomBar.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnAAdirCompendio.setOnClickListener {
            val compendios = llegirDades()
            //comprueba si el nombre del compendio ya existe en la base de datos
            bd.collection("Compendios").document(binding.txtAAdirNombreCompendio.text.toString()).get().addOnSuccessListener { it ->
                if(it.exists()){
                    //Si existe avisa con un snackbar
                    Snackbar.make(view, getString(R.string.este_compendio_ya_existe), BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
                }else{
                    //Sino lo añade
                    if (compendios.nombre.isNotEmpty() && compendios.enlace.isNotEmpty()) {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                anadirCompendio(compendios)
                            }
                        }
                        findNavController().navigate(R.id.action_anadir_compendios_to_admin_inici)
                        Utils.notification(getString(R.string.compendio_anadido),getString(R.string.el_compendio) + binding.txtAAdirNombreCompendio.text.toString() + getString(
                                                    R.string.se_a_anadido), requireContext())
                        Snackbar.make(view, getString(R.string.compendio_anadido_correctamente), BaseTransientBottomBar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * Lee la base de datos
     */
    fun llegirDades(): compendios {
        val nombre = binding.txtAAdirNombreCompendio.text.toString()
        val enlace = binding.txtAAdirEnlaceCompendio.text.toString()
        val ruta = "image/compendios/" + binding.txtAAdirNombreCompendio.text.toString()

        return compendios(nombre, enlace, ruta)
    }

    /**
     * Añade el objeto compendios a la base de datos
     *
     * @param compendios objeto
     */
    suspend fun anadirCompendio(compendios: compendios){
        bd.collection("Compendios")
            .document(binding.txtAAdirNombreCompendio.text.toString()).set(
                compendios
            ).await()
    }

    //resultLauncher és l'atribut on guardarem el resultat de la nostra activitat, en el nostre cas obrir la galeria i mitjançant el qual
    //cridarem a l'Intent per obrir-la.
    private val guardarImgCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                photoSelectedUri = result.data?.data //Assignem l'URI de la imatge
            }
        }

    /**
     * Funcion que sube la imagen a la base de datos y la muestra
     */
    fun afegirImatge2(){
        //Obrim la galeria per seleccionar la imatge  //Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        guardarImgCamera.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        storageRef = storage.reference.child("image/compendios").child(binding.txtAAdirNombreCompendio.text.toString())
        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let{uri->
                storageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(context, getString(R.string.imagen_subida_con_exito), Toast.LENGTH_LONG).show()
                    binding.imgAAdirCompendios.setImageURI(uri)
                }
        }
    }
}
