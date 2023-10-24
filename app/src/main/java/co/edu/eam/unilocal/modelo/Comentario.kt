package co.edu.eam.unilocal.modelo

import java.util.*

class Comentario(var texto:String,
                 var idUsuario:Int,
                 var idLugar:Int,
                 var calificacion:Int) {

    constructor(id:Int, texto:String, idUsuario:Int, idLugar: Int, calificacion: Int):this(texto, idUsuario, idLugar, calificacion){
        this.id = id
    }

    var id:Int = 0
    var fecha:Date = Date()

    override fun toString(): String {
        return "Comentario(id=$id, texto='$texto', idUsuario=$idUsuario, idLugar=$idLugar, calificacion=$calificacion, fecha=$fecha)"
    }

}