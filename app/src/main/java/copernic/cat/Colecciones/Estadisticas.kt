package copernic.cat.Colecciones

import com.google.firebase.firestore.Exclude
import copernic.cat.Reglas.criticos

data class Estadisticas(val email:String,
                        var nTiradas:Int,
                        var nCriticos:Int,
                        var nPersonajes:Int,
                        var nPartidas:Int,
                        var clasePref:String,
                        var personajePref:String)