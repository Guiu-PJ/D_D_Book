package copernic.cat.Informacio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import kotlin.random.Random
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import copernic.cat.Inici.MainActivity
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
    private lateinit var auth: FirebaseAuth

    /**
     * En el método onCreateView, se establece el título de la actividad principal y se infla el layout correspondiente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.dados)
        _binding = FragmentDadosBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * En el método onViewCreated, se establecen los listener para los diferentes botones de la vista, los cuales llevan a diferentes fragmentos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTirar.setOnClickListener{
            /**
             * Recojemos los 4 edit text
             *
             * a Es el numero de doados
             * b Es el modificador positivo
             * c Es el numero de caras
             * d Es el modificador negativo
             */
            val a = binding.txtNumeroDeDados.text.toString()
            val b = binding.txtModificadorPositivo.text.toString()
            val c = binding.txtNumeroDeCaras.text.toString()
            val d = binding.txtModificadorNegativo.text.toString()

            /**
             * Comprueba que los campos no esten vacios
             */
            if(a.isNotEmpty() && b.isNotEmpty() && c.isNotEmpty() && d.isNotEmpty()){
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
                /**
                 * Bucle que usa el numero de dados y devuelve un aleatorio entre 1 y el numero de caras
                 */
                while (numdados>0){
                    resultat = Random.nextInt((numcaras+1) - 1) + 1
                    arrayresultats.add(resultat)
                    restotal += resultat
                    numdados--
                    //Si es critico lo guarda en la variable
                    if(resultat == 20){
                        criticos += 1
                    }
                }
                /**
                 * Se suman y restan los modificadores
                 */
                restotal -= modnegativo
                restotal += modpositivo
                binding.txtResultadoFinal.text = getString(R.string.resultado_final2) + (restotal).toString()
                var num = 0
                //Muestra los resultados uno por uno
                for(i in arrayresultats) {
                    string += getString(R.string.dado) + (num+1).toString() + ": " + (arrayresultats[num]).toString() + "  "
                    num = num+1
                }
                binding.txtResultadoDados.text = string
                //Actualizamos la base de datos
                lifecycleScope.launch{
                    withContext(Dispatchers.IO){//llegir dades de la base de dades
                        actualizarbdcriticos(criticos.toString())
                    }
                    withContext(Dispatchers.IO){//llegir dades de la base de dades
                        actualizarbdtiradas()
                    }
                }
            }else{
                //alert dialog por si algun campo no esta lleno
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(getString(R.string.tirada_fallida))
                builder.setMessage(getString(R.string.todos_los_campos_tienen_que_estar_llenos))
                builder.setPositiveButton("OK"){_, _ ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            }


        }
    }

    /**
     * Actualiza la base de datos con los criticos realizados
     *
     * @param criticos numero de criticos en esa tirarda
     */
    fun actualizarbdcriticos(criticos: String){
        val user = auth.currentUser
        var c = 0
        var critico = "0"
         bd.collection("Usuari").document(user!!.uid).collection("Estadisticas").document("IdEstadisticas").get().addOnSuccessListener {
             critico = it.get("criticos") as String
             c = critico.toInt() + criticos.toInt()
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
