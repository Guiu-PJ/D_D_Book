package copernic.cat.Informacio

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import copernic.cat.R
import copernic.cat.databinding.FragmentReglasBinding


class reglas : Fragment() {
    private var _binding: FragmentReglasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReglasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFlechaReglas.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_inici)
        }
        binding.btnAccion.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_accion)
        }
        binding.btnAccionbonus.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_accion_bonus)
        }
        binding.btnAlcance.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_alcance)
        }
        binding.btnCriticos.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_criticos)
        }
        binding.btnDotes.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_dotes)
        }
        binding.btnEquipamiento.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_equipamiento)
        }
        binding.btnEstadosalt.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_estados_alterados)
        }
        binding.btnMovimiento.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_movimiento)
        }
        binding.btnMulticlase.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_multiclase)
        }
        binding.btnNivel.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_nivel)
        }
        binding.btnReaccion.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_reaccion)
        }
        binding.btnTrasfondos.setOnClickListener {
            findNavController().navigate(R.id.action_reglas_to_trasfondos)
        }

    }
}
