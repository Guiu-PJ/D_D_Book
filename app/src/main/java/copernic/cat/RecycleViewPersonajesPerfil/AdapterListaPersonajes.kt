package copernic.cat.RecycleViewPersonajesPerfil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.Admin.admin_modificarIeliminar_reglasDirections
import copernic.cat.Perfil.perfilDirections
import copernic.cat.RecycleViewCompendios.AdapterListaCompendios
import copernic.cat.RecyclerViewReglas.ListaReglas
import copernic.cat.databinding.ItemListapersonajesBinding

class AdapterListaPersonajes(private val ListaPersonajes:List<ClassListaPersonajes>) : RecyclerView.Adapter<AdapterListaPersonajes.ListaPersonajesViewHolder>(){
    inner class ListaPersonajesViewHolder(val binding: ItemListapersonajesBinding):RecyclerView.ViewHolder(binding.root)
    private var binding: ItemListapersonajesBinding? = null
    private lateinit var mListener: onItemCLickListener

    interface onItemCLickListener{
        fun onItemCLick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemCLickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListaPersonajes.ListaPersonajesViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding= ItemListapersonajesBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaPersonajesViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AdapterListaPersonajes.ListaPersonajesViewHolder, position: Int) {
        with(holder) {
            with(ListaPersonajes[position]) {
                binding.txtListapersonajes.text = this.nombre
                binding.imgListapersonajes.setImageResource(this.img)
            }
            binding.ConstraintPersonajes.setOnClickListener{ view ->
                //binding.txtReglas.text = R.id.txt_regla_se
                navigationToEditPers(view, ListaPersonajes[position].nombre)
            }
        }
    }
    private fun navigationToEditPers(view: View, datos: String) {
        val action = perfilDirections.actionPerfilToEditarOEliminarPersonaje(datos)
        view.findNavController().navigate(action)
    }

    override fun getItemCount(): Int {
        return ListaPersonajes.size
    }

}

