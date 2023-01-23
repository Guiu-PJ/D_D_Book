package copernic.cat.Models

import android.net.Uri

data class Usuarios(val email:String, var contraseña:String,
                    var nombreUsuario:String, var adminAcces:Boolean,
                    var imagenUsuario:Uri,
                     var estadisticas:ArrayList<Estadisticas>,
                     var partidas:ArrayList<Partidas>)
