package copernic.cat.EditarPersonaje

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import copernic.cat.R
import copernic.cat.databinding.FragmentEditarOEliminarPersonajeBinding
import copernic.cat.databinding.FragmentEditarPersonajeGeneralBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editar_personaje_general.newInstance] factory method to
 * create an instance of this fragment.
 */
class editar_personaje_general : Fragment() {
    private var _binding: FragmentEditarPersonajeGeneralBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditarPersonajeGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        val bundle = arguments
        val args = editar_personaje_generalArgs.fromBundle(bundle!!)
        val nom = args.nomp

        binding.editPersonajeNombre2.setText(nom)

        lifecycleScope.launch {
            withContext(Dispatchers.Unconfined) {
                bd.collection("Usuari").document(user!!.uid)
                    .collection("Personajes")
                    .document(nom.toString()).get()
                    .addOnSuccessListener {
                        binding.editPersonajeClasse.setText(it.get("classe") as String)
                        binding.editPersonajeTrasfondo.setText(it.get("trasfondo") as String)
                        binding.editPersonajeNivel.setText(it.get("nivel") as String)
                        binding.editPersonajeRaza.setText(it.get("raza") as String)
                        binding.editPersonajeAlineamiento.setText(it.get("alineamiento") as String)
                        binding.editPersonajeVidamax.setText(it.get("vidaMax") as String)
                        binding.editPersonajeVidaactual.setText(it.get("vidaActual") as String)
                        binding.editFue.setText(it.get("fuerza") as String)
                        binding.editCon.setText(it.get("constitucion") as String)
                        binding.editCar.setText(it.get("carisma") as String)
                        binding.editDes.setText(it.get("destreza") as String)
                        binding.editInt.setText(it.get("inteligencia") as String)
                        binding.editSab.setText(it.get("sabiduria") as String)
                        binding.editMov.setText(it.get("movimineto") as String)
                        binding.editInici.setText(it.get("inici") as String)
                        binding.editDadosDeGolpe.setText(it.get("dados_de_golpe") as String)
                        binding.editExito.setText(it.get("salvaconExito") as String)
                        binding.editFallo.setText(it.get("salvacionFallo") as String)
                        obrirfoto(nom.toString())
                    }
            }
        }
        binding.btnSiguienteFichaPersonajeGeneral.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Unconfined) {
                    bd.collection("Usuari").document(user!!.uid)
                        .collection("Personajes")
                        .document(nom.toString()).update(
                            "classe", binding.editPersonajeClasse.text.toString(),
                            "trasfondo", binding.editPersonajeTrasfondo.text.toString(),
                            "nivel", binding.editPersonajeNivel.text.toString(),
                            "raza", binding.editPersonajeRaza.text.toString(),
                            "alineamiento", binding.editPersonajeAlineamiento.text.toString(),
                            "vidaMax", binding.editPersonajeVidamax.text.toString(),
                            "vidaActual", binding.editPersonajeVidaactual.text.toString(),
                            "fuerza", binding.editFue.text.toString(),
                            "constitucion", binding.editCon.text.toString(),
                            "carisma", binding.editCar.text.toString(),
                            "destreza", binding.editDes.text.toString(),
                            "inteligencia", binding.editInt.text.toString(),
                            "sabiduria", binding.editSab.text.toString(),
                            "movimineto", binding.editMov.text.toString(),
                            "inici", binding.editInici.text.toString(),
                            "dados_de_golpe", binding.editDadosDeGolpe.text.toString(),
                            "salvaconExito", binding.editExito.text.toString(),
                            "salvacionFallo", binding.editFallo.text.toString(),
                        )
                }
            }
            val action = editar_personaje_generalDirections.actionEditarPersonajeGeneralToEditPersonajeEquipamiento(nom)
            view.findNavController().navigate(action)
        }
    }
    private fun obrirfoto(nom : String){
        val user = auth.currentUser
        val storageRef = FirebaseStorage.getInstance().reference.child("image/personajes/" + user!!.uid + "/" + nom)
        val localfile = File.createTempFile("tempImage", "jpeg")
        //var bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgCrearPersonaje.setImageBitmap(bitmap)
        }
    }

}