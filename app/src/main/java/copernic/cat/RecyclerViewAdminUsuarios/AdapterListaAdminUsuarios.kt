package copernic.cat.RecyclerViewAdminUsuarios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.databinding.ItemAdminUsuariosBinding

class AdapterListaAdminUsuarios(private val ListaAdminUsuarios:List<ClassAdminUsuarios>) : RecyclerView.Adapter<AdapterListaAdminUsuarios.ListaAdminUsuariosViewHolder>() {
    inner class ListaAdminUsuariosViewHolder(val binding: ItemAdminUsuariosBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListaAdminUsuarios.ListaAdminUsuariosViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding= ItemAdminUsuariosBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaAdminUsuariosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterListaAdminUsuarios.ListaAdminUsuariosViewHolder, position: Int) {
        with(holder) {
            with(ListaAdminUsuarios[position]) {
                binding.txtCorreoUsuariosAdmin.text = this.correo
                binding.imgUsuariosAdmin.setImageResource(this.image)
            }
        }
    }

    override fun getItemCount(): Int {
        return ListaAdminUsuarios.size
    }
    var onItemClick: ((ListaAdminUsuarios) -> Unit)? = null
    var contacts: List<ListaAdminUsuarios> = emptyList()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contacts[adapterPosition])
            }
        }
    }
}