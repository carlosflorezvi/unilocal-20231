package co.edu.eam.unilocal.modelo

import java.util.*

class Comentario() {

    constructor(texto:String, idUsuario:String, calificacion:Int):this(){
        this.texto = texto
        this.idUsuario = idUsuario
        this.calificacion = calificacion
    }

    var key:String = ""
    var texto:String = ""
    var idUsuario:String = ""
    var calificacion:Int = 0
    var fecha:Date = Date()


}