package copernic.cat.RecyclerViewPerfil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.Partidas.crear_partidaDirections
import copernic.cat.Perfil.partidasDirections
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecyclerViewPerfil.ListaPerfil
import copernic.cat.databinding.ItemLiatcompendiosBinding
import copernic.cat.databinding.ItemListaperfilBinding

class AdapterListaPerfil(private val ListaPerfil:List<ClassPerfil>) : RecyclerView.Adapter<AdapterListaPerfil.ListaPerfilViewHolder>() {
    inner class ListaPerfilViewHolder(val binding: ItemListaperfilBinding): RecyclerView.ViewHolder(binding.root)
    private var binding: ItemListaperfilBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListaPerfil.ListaPerfilViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding = ItemListaperfilBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaPerfilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterListaPerfil.ListaPerfilViewHolder, position: Int) {
        with(holder) {
            with(ListaPerfil[position]) {
                binding.txtListaperfil.text = this.nombre
                binding.txtListapersonaje.text = this.personaje
            }
            binding.ConstrainListaPerfil.setOnClickListener{ view ->
                navigationTo(view, ListaPerfil[position].nombre)
            }
        }
    }
    private fun navigationTo(view: View, datos: String) {
        val action = partidasDirections.actionPartidasToModificarOEliminarPartida(datos)
        view.findNavController().navigate(action)
    }

    override fun getItemCount(): Int {
        return ListaPerfil.size
    }
}
