package copernic.cat.RecycleViewPersonajesCrearPartidas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import copernic.cat.Partidas.crear_partidaDirections
import copernic.cat.Perfil.perfilDirections
import copernic.cat.databinding.ItemPersonajescrearpartidaBinding

class AdapterPersonajesCrearPartida(private val ListaPersonajes:List<ClassPersonajesCrearPartida>) : RecyclerView.Adapter<AdapterPersonajesCrearPartida.ListaPersonajesViewHolder>(){
    inner class ListaPersonajesViewHolder(val binding: ItemPersonajescrearpartidaBinding): RecyclerView.ViewHolder(binding.root)
    private var binding: ItemPersonajescrearpartidaBinding? = null
    private lateinit var mListener: onItemCLickListener

    interface onItemCLickListener{
        fun onItemCLick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemCLickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPersonajesCrearPartida.ListaPersonajesViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
        //return CircularsViewHolder(layoutInflater.inflate(R.layout.fragment_circulars_usuaris, parent, false))
        val binding= ItemPersonajescrearpartidaBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ListaPersonajesViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AdapterPersonajesCrearPartida.ListaPersonajesViewHolder, position: Int) {
        with(holder) {
            with(ListaPersonajes[position]) {
                binding.txtPersonajesCrearPartida.text = this.nombre
                binding.imgPersonajesCrearPartida.setImageResource(this.img)
            }
            binding.ConstraintPersonajesCrearPartida.setOnClickListener{ view ->
                navigationTo(view, ListaPersonajes[position].nombre)
            }
        }
    }
    private fun navigationTo(view: View, datos: String) {
        val action = crear_partidaDirections.actionCrearPartidaToCrearPartidaNom(datos)
        view.findNavController().navigate(action)
    }

    override fun getItemCount(): Int {
        return ListaPersonajes.size
    }
}