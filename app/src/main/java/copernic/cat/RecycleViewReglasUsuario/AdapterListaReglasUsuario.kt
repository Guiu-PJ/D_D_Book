package copernic.cat.RecycleViewReglasUsuario

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.Admin.admin_modificarIeliminar_reglasDirections
import copernic.cat.Informacio.reglasDirections
import copernic.cat.databinding.ItemReglasUsuariosBinding

class AdapterListaReglasUsuario (private val ListaReglasUsuario:List<ClassReglasUsuario>) : RecyclerView.Adapter<AdapterListaReglasUsuario.ListaReglasUsuarioViewHolder>() {
    inner class ListaReglasUsuarioViewHolder(val binding: ItemReglasUsuariosBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListaReglasUsuario.ListaReglasUsuarioViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding = ItemReglasUsuariosBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaReglasUsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterListaReglasUsuario.ListaReglasUsuarioViewHolder, position: Int) {
        with(holder) {
            with(ListaReglasUsuario[position]) {
                binding.txtReglasNombreUsuarios.text = this.nombre
            }
            binding.ConstraintReglasUsuarios.setOnClickListener{ view ->
                //binding.txtReglas.text = R.id.txt_regla_se
                navigationToRditarReglas(view, ListaReglasUsuario[position].nombre)
            }
        }
    }

    private fun navigationToRditarReglas(view: View, datos: String) {
        val action = reglasDirections.actionReglasToReglasDes(datos)
        view.findNavController().navigate(action)
    }

    override fun getItemCount(): Int {
        return ListaReglasUsuario.size
    }
    var onItemClick: ((ListaReglasUsuarios) -> Unit)? = null
    var contacts: List<ListaReglasUsuarios> = emptyList()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contacts[adapterPosition])
            }
        }
    }
}