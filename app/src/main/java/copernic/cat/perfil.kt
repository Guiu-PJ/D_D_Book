package copernic.cat

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class perfil : Fragment() {


    private lateinit var  adapter : MyAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var newsArrayList : ArrayList<News>
    private lateinit var botoLogout: Button

    lateinit var imageId : Array<Int>
    lateinit var textId : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Infalate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_personajes)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(newsArrayList)
        recyclerView.adapter = adapter

        botoLogout = view.findViewById(R.id.img_cerrar_sesion)

        botoLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
        }
    }

    private fun dataInitialize(){
        newsArrayList = arrayListOf<News>()
        imageId = arrayOf(
            R.drawable.calamardo,
            R.drawable.jorge

        )

        textId = arrayOf(
            getString(R.string.ricardo_calamar),
            getString(R.string.jorge_cerdo)

        )

        for(i in imageId.indices){

            val news = News(imageId[i], textId[i])
            newsArrayList.add(news)
        }

    }



}