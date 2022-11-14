package copernic.cat.RecycleViewPersonajesPerfil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.databinding.ItemListapersonajesBinding

class AdapterListaPersonajes(private val ListaPersonajes:List<ClassListaPersonajes>) : RecyclerView.Adapter<AdapterListaPersonajes.ListaPersonajesViewHolder>(){
    inner class ListaPersonajesViewHolder(val binding: ItemListapersonajesBinding):RecyclerView.ViewHolder(binding.root)
    private var binding: ItemListapersonajesBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaPersonajesViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding= ItemListapersonajesBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaPersonajesViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ListaPersonajesViewHolder, position: Int) {
        with(holder) {
            with(ListaPersonajes[position]) {
                binding.txtListapersonajes.text = this.nombre
                binding.imgListapersonajes.setImageResource(this.image)
            }
        }
    }

    override fun getItemCount(): Int {
        return ListaPersonajes.size
    }

}

