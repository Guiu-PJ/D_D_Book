package copernic.cat.Informacio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import kotlin.random.Random
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.R
import copernic.cat.Reglas.accion
import copernic.cat.Reglas.criticos
import copernic.cat.databinding.FragmentDadosBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

class Dados : Fragment() {
    private var _binding: FragmentDadosBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private  lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFelchaDados.setOnClickListener {
            findNavController().navigate(R.id.action_dados_to_inici)

        }
        binding.btnTirar.setOnClickListener{
            auth = Firebase.auth
            val arrayresultats = ArrayList<Int>()
            var restotal = 0
            var numdados = Integer.parseInt(binding.txtNumeroDeDados.text.toString())
            val modpositivo = Integer.parseInt(binding.txtModificadorPositivo.text.toString())
            val numcaras = Integer.parseInt(binding.txtNumeroDeCaras.text.toString())
            val modnegativo = Integer.parseInt(binding.txtModificadorNegativo.text.toString())
            var resultat: Int?
            var string = ""
            var criticos = 0
            //Toast.makeText(context, (numdados+mod).toString(), Toast.LENGTH_SHORT).show()
            while (numdados>0){
                resultat = Random.nextInt((numcaras+1) - 1) + 1
                arrayresultats.add(resultat)
                restotal += resultat
                numdados--
                if(resultat == 20){
                    criticos += 1
                }
        }
            restotal -= modnegativo
            restotal += modpositivo
            binding.txtResultadoFinal.text = "Resultado final: " + (restotal).toString()
           var num = 0
            for(i in arrayresultats) {
                string += "Dado" + (num+1).toString() + ": " + (arrayresultats[num]).toString() + "  "
                num = num+1
            }
            binding.txtResultadoDados.text = string
            //Toast.makeText(context, (numdados).toString()+(numcaras+modpositivo).toString(), Toast.LENGTH_SHORT).show()
            lifecycleScope.launch{
                withContext(Dispatchers.Unconfined){//llegir dades de la base de dades
                    actualizarbdcriticos(criticos.toString())
                }
                withContext(Dispatchers.Unconfined){//llegir dades de la base de dades
                    actualizarbdtiradas()
                }
            }
        }
    }

    fun actualizarbdcriticos(criticos: String){
        val user = auth.currentUser
        var c = 0
        var critico = "0"
         bd.collection("Usuari").document(user!!.uid).collection("Estadisticas").document("IdEstadisticas").get().addOnSuccessListener {
             critico = it.get("criticos") as String
             c = critico.toInt() + criticos.toInt()
             Toast.makeText(context, c.toString(), Toast.LENGTH_SHORT).show()
             bd.collection("Usuari").document(user.uid).collection("Estadisticas")//Col.lecció
                 .document("IdEstadisticas").update("criticos", c.toString())
         }

    }
    fun actualizarbdtiradas(){
        val user = auth.currentUser
        bd.collection("Usuari").document(user!!.uid).collection("Estadisticas").document("IdEstadisticas").get().addOnSuccessListener {
            val num = it.get("numero_tiradas") as String
             bd.collection("Usuari").document(user.uid).collection("Estadisticas")//Col.lecció
                .document("IdEstadisticas").update("numero_tiradas", (num.toInt()+1).toString())
        }
    }
}
