package copernic.cat.Informacio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import kotlin.random.Random
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import copernic.cat.R
import copernic.cat.Reglas.accion
import copernic.cat.databinding.FragmentDadosBinding

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
            val arrayresultats = ArrayList<Int>()
            var restotal = 0
            var numdados = Integer.parseInt(binding.txtNumeroDeDados.text.toString())
            val modpositivo = Integer.parseInt(binding.txtModificadorPositivo.text.toString())
            val numcaras = Integer.parseInt(binding.txtNumeroDeCaras.text.toString())
            val modnegativo = Integer.parseInt(binding.txtModificadorNegativo.text.toString())
            var resultat: Int?
            var string = ""
            //Toast.makeText(context, (numdados+mod).toString(), Toast.LENGTH_SHORT).show()
            while (numdados>0){
                resultat = Random.nextInt((numcaras+1) - 1) + 1
                arrayresultats.add(resultat)
                restotal += resultat
                numdados--
        }
            restotal -= modnegativo
            restotal += modpositivo
            binding.txtResultadoFinal.text = "Resultado final: " + (restotal).toString()
            for(i in arrayresultats) {
                string += "Dado" + i.toString() + ": " + (arrayresultats[i]).toString() + "  "
            }
            binding.txtResultadoDados.text = string
            //Toast.makeText(context, (numdados).toString()+(numcaras+modpositivo).toString(), Toast.LENGTH_SHORT).show()
        }
    }

}