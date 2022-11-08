package copernic.cat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(private val listapersonajes : ArrayList<News>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_personajes, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = listapersonajes[position]
        holder.image.setImageResource(currentItem.image)
        holder.text.text = currentItem.text
    }

    override fun getItemCount(): Int {
        return listapersonajes.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val image : ShapeableImageView = itemView.findViewById(R.id.img_personaje_calamardo)
        val text : TextView = itemView.findViewById(R.id.txt_personaje_calamardo)

    }



}