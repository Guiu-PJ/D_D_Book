package copernic.cat.RecycleViewCompendios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.databinding.ItemLiatcompendiosBinding

class AdapterListaCompendios(private val ListaCompendios:List<ClassCompendios>) : RecyclerView.Adapter<AdapterListaCompendios.ListaCompendiosViewHolder>() {
    inner class ListaCompendiosViewHolder(val binding: ItemLiatcompendiosBinding): RecyclerView.ViewHolder(binding.root)
    private var binding: ItemLiatcompendiosBinding? = null

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
        }
    }

    override fun getItemCount(): Int {
        return ListaCompendios.size
    }
}