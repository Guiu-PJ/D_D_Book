package copernic.cat.Colecciones

import android.net.Uri
import com.google.firebase.firestore.Exclude

data class Usuarios(val email:String, var contraseña:String,
                    var nombreUsuario:String, var adminAcces:Boolean,
                    var imagenUsuario:Uri,
                     var estadisticas:ArrayList<Estadisticas>,
                     var partidas:ArrayList<Partidas>)
