package copernic.cat.RecycleViewCompendios

import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.R
import kotlinx.coroutines.tasks.await

class ListaCompendios {
    private var bd = FirebaseFirestore.getInstance()
    companion object{
        val ListaCompendioslist = listOf<ClassCompendios>(
            ClassCompendios(
                "Compendio del jugador",
                R.drawable.player_handbook_5e
            ),
            ClassCompendios(
                "Dungeon master",
                R.drawable.dungeon_masters_guide
            ),
            ClassCompendios(
                "El resurgir del dragon",
                R.drawable.el_resurgir_del_dragon
            ),
            ClassCompendios(
                "Compendio de monstruos",
                R.drawable.monster_manual_5
            )
        )
    }

    suspend fun mostrarDepartaments():String{ //Una funció de supensió bloquejà el context on s'executa la corrutina

        var resultat:String = ""

        //La variable resultatConsulta guardarà el resultat de la consulta a Firestore, en el nostre cas, tots els documents (get())
        //de la col.lecció departament, és a dir, tots els departaments amb els seus parells clau-valor.
        //await(), és el mètode que suspèn la corrutina, en el nostre cas la consulta a Firestore. A partir d'aquí, el context on s'executa
        //la corrutina, torna a estar actiu.
        var resultatConsulta = bd.collection("Compendios").get().await()

        if(!resultatConsulta.isEmpty) { //Si el resultat té contingut...

            for (document in resultatConsulta) { //Per cada document que ens ha retornat la consulta..

                //Guardem el codi del departament i el seu contingut concatenant tots els resultats i guardant-los en la variable resultat.
                //Accedim al codi cridant a l'atribut predefinit id d'un document de la classe FirebaseFirestore el qual ens retorna l'id del document
                //que el crida, en el nostre cas el codi.
                //Accedim a tot el contingut del document cridant a l'atribut predefinit data d'un document de la classe FirebaseFirestore el qual ens
                //retorna tot el contingut del document que el crida.
                resultat += "Compendios: ${document.id} -> ${document.data}\n"

            }

        }else{ //Si el resultat no té contingut...
            //Toast.makeText(this,"No hi ha departaments", Toast.LENGTH_LONG).show()
        }

        return resultat
    }
}