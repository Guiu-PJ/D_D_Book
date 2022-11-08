package copernic.cat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth


class perfil : Fragment(R.layout.fragment_perfil) {
    private lateinit var adapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArrayList: ArrayList<News>

    lateinit var imageId: Array<Int>
    lateinit var textId: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnatras = requireView().findViewById<ImageButton>(R.id.btn_flecha_novedades)

        btnatras.setOnClickListener {
            findNavController().navigate(R.id.action_novedades_to_inici)
        }

    }


    // override fun onCreateView(

        //inflater: LayoutInflater, container: ViewGroup?,
        //savedInstanceState: Bundle?
        //): View? {

        // Inflate the layout for this fragment
    //return inflater.inflate(R.layout.fragment_perfil, container, false)
    // }

//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//super.onViewCreated(view, savedInstanceState)
        //dataInitialize()
        //val layoutManager = LinearLayoutManager(context)
        //recyclerView = view.findViewById(R.id.recycler_personajes)
        //recyclerView.layoutManager = layoutManager
        //recyclerView.setHasFixedSize(true)
        //adapter = MyAdapter(newsArrayList)
        //recyclerView.adapter = adapter
// }

//private fun dataInitialize(){
     //newsArrayList = arrayListOf<News>()

     //imageId = arrayOf(
     //R.drawable.calamardo,
     //R.drawable.jorge

     //)

     //textId = arrayOf(
     //getString(R.string.ricardo_calamar),
     //getString(R.string.jorge_cerdo)

     //)

     //for(i in imageId.indices){

     //val news = News(imageId[i], textId[i])
     //newsArrayList.add(news)
     //}

     //}



}