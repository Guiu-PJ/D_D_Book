package copernic.cat.Colecciones

import com.google.firebase.firestore.Exclude

data class Dados(val idDados:Int,
                 var contadorTiradas:Int,
                 var contadorCriticos:Int,
                 var imagenesDados:String)