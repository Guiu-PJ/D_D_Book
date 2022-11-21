package copernic.cat.Admin

import android.app.Activity
import android.content.DialogInterface
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
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import copernic.cat.R
import copernic.cat.classes.compendios
import copernic.cat.classes.reglas
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
    private var photoSelectedUri: Uri?=null

    //resultLauncher és l'atribut on guardarem el resultat de la nostra activitat, en el nostre cas obrir la galeria i mitjançant el qual
    //cridarem a l'Intent per obrir-la.
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            photoSelectedUri = it.data?.data //Assignem l'URI de la imatge
        }
    }
    private var storage = FirebaseStorage.getInstance()
    private var storageRef = storage.getReference().child("image/imatges").child("foto1.jpeg")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnadirCompendiosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgAAdirCompendios.setImageResource(R.drawable.cachodemadera)
        super.onViewCreated(view, savedInstanceState)
        binding.btnAAdirFotoCompendio.setOnClickListener {
            afegirImatge()
        }
        binding.btnAAdirCompendio.setOnClickListener {
            val compendios = llegirDades()

            if (compendios.titulo.isNotEmpty() && compendios.enlace.isNotEmpty()) {
                bd.collection("Compendios").document("ID Compendio").set(hashMapOf("Titulo" to binding.txtAAdirNombreCompendio.text.toString(), "Enlace" to binding.txtAAdirEnlaceCompendio.text.toString()))

                }
        }
    }
    private val guardarImgCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            photoSelectedUri = result.data?.data //Assignem l'URI de la imatge
        }
    }

    private fun afegirImatge(){
        //Obrim la galeria per seleccionar la imatge
        guardarImgCamera.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))

        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let{uri-> //Hem seleccionat una imatge...
            //Afegim (pujem) la imatge que hem seleccionat mitjançant el mètode putFile de la classe FirebasStorage, passant-li com a
            //paràmetre l'URI de la imatge.
            storageRef.putFile(uri)
                .addOnSuccessListener {

                }
        }
    }

    fun llegirDades(): compendios {
        val titulo =binding.txtAAdirNombreCompendio.text.toString()
        val enlace =binding.txtAAdirEnlaceCompendio.text.toString()

        return compendios(titulo, enlace)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if (requestCode == 100 && resultCode == RESULT_OK){

        //}

    }

    private fun selectimg(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }
}
//bd.collection("Reglas").document("ID Reglas").set(hashMapOf("Nombre" to binding.txtTituloReglas.text.toString(), "Descripcion" to binding.txtDescripcionReglas.text.toString())).addOnSuccessListener {






