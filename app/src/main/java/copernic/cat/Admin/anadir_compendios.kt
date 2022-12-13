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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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
    private var photoSelectedUri: Uri? = null
    private var storage = FirebaseStorage.getInstance()
    private var storageRef = storage.getReference().child("image/compendios").child("foto1.jpeg")
    var nom = ""

    //resultLauncher és l'atribut on guardarem el resultat de la nostra activitat, en el nostre cas obrir la galeria i mitjançant el qual
    //cridarem a l'Intent per obrir-la.
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                photoSelectedUri = it.data?.data //Assignem l'URI de la imatge
            }
        }

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
            afegirImatge2()
        }
        binding.btnAAdirCompendio.setOnClickListener {
            val compendios = llegirDades()

            bd.collection("Compendios").document(binding.txtAAdirNombreCompendio.text.toString()).get().addOnSuccessListener { it ->
                if(it.exists()){
                    Toast.makeText(context, "Aquest compendio ja existeix", Toast.LENGTH_SHORT).show()
                }else{
                    if (compendios.nombre.isNotEmpty() && compendios.enlace.isNotEmpty()) {
                        bd.collection("Compendios")
                            .document(binding.txtAAdirNombreCompendio.text.toString()).set(
                                hashMapOf(
                                    "Nombre" to binding.txtAAdirNombreCompendio.text.toString(),
                                    "Enlace" to binding.txtAAdirEnlaceCompendio.text.toString(),
                                    "ruta" to "image/compendios/" + binding.txtAAdirNombreCompendio.text.toString()
                                )
                            )
                        findNavController().navigate(R.id.action_anadir_compendios_to_admin_inici)
                        notification(binding.txtAAdirNombreCompendio.text.toString())
                        Snackbar.make(view, "Compendio añadido correctamente", BaseTransientBottomBar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun notification(nom:String) {
        val notification = NotificationCompat.Builder(requireContext(),"1").also{ noti ->
            noti.setContentTitle("Compendio añadido")
            noti.setContentText("Compendio: " + nom + " correctamente añadido")
            noti.setSmallIcon(R.drawable.logo)
        }.build()
        val notificationManageer = NotificationManagerCompat.from(requireContext())
        notificationManageer.notify(1,notification)
    }

    private val guardarImgCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                photoSelectedUri = result.data?.data //Assignem l'URI de la imatge
            }
        }

    private fun llegirDades(): compendios {
        val nombre = binding.txtAAdirNombreCompendio.text.toString()
        val enlace = binding.txtAAdirEnlaceCompendio.text.toString()

        return compendios(nombre, enlace)
    }

    private fun afegirImatge2(){
        //Obrim la galeria per seleccionar la imatge  //Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        guardarImgCamera.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        storageRef = storage.reference.child("image/compendios").child(binding.txtAAdirNombreCompendio.text.toString())
        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let{uri->
                storageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(context, "La imatge s'ha pujat amb èxit", Toast.LENGTH_LONG).show()
                    binding.imgAAdirCompendios.setImageURI(uri)
                }
        }
    }
}
