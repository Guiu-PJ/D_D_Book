package copernic.cat.Colecciones

import com.google.firebase.firestore.Exclude

data class Partidas(val email:String,
                    var nPartida:Int,
                    var idFichaPersonaje:Int,
                    var estadisticasPersonaje:String)