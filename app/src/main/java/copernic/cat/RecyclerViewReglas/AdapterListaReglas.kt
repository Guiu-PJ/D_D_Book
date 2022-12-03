package copernic.cat.RecyclerViewReglas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.Admin.admin_modificarIeliminar_reglasDirections


import copernic.cat.databinding.ItemReglasBinding

class AdapterListaReglas (private val ListaReglas:List<ClassReglas>) : RecyclerView.Adapter<AdapterListaReglas.ListaReglasViewHolder>() {
    inner class ListaReglasViewHolder(val binding: ItemReglasBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListaReglas.ListaReglasViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding = ItemReglasBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaReglasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterListaReglas.ListaReglasViewHolder, position: Int) {
        with(holder) {
            with(ListaReglas[position]) {
                binding.txtReglas.text = this.nombre
            }
            binding.ConstraintReglas.setOnClickListener{ view ->
                //binding.txtReglas.text = R.id.txt_regla_se
                navigationToRditarReglas(view, ListaReglas[position].nombre)
            }
        }
    }

    private fun navigationToRditarReglas(view: View, datos: String) {
        val action = admin_modificarIeliminar_reglasDirections.actionAdminModificarIeliminarReglasToAdminModificarRegla(datos)
        view.findNavController().navigate(action)
    }

    override fun getItemCount(): Int {
        return ListaReglas.size
    }
    var onItemClick: ((ListaReglas) -> Unit)? = null
    var contacts: List<ListaReglas> = emptyList()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contacts[adapterPosition])
            }
        }
    }
}
