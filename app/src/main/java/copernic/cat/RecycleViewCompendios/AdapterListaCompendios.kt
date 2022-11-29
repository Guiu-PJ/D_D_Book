package copernic.cat.RecycleViewCompendios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.databinding.ItemLiatcompendiosBinding

class AdapterListaCompendios(private val ListaCompendios:List<ClassCompendios>) : RecyclerView.Adapter<AdapterListaCompendios.ListaCompendiosViewHolder>() {
    inner class ListaCompendiosViewHolder(val binding: ItemLiatcompendiosBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListaCompendios.ListaCompendiosViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding= ItemLiatcompendiosBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaCompendiosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterListaCompendios.ListaCompendiosViewHolder, position: Int) {
        with(holder) {
            with(ListaCompendios[position]) {
                binding.txtListacompendios.text = this.nombre
                binding.imgListacompendios.setImageResource(this.image)
            }
            binding.constrantcompendios.setOnClickListener{
                binding.txtListacompendios.text = "hola"
            }
        }

    }

    override fun getItemCount(): Int {
        return ListaCompendios.size
    }
    var onItemClick: ((ListaCompendios) -> Unit)? = null
    var contacts: List<ListaCompendios> = emptyList()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contacts[adapterPosition])
            }
        }
    }
}