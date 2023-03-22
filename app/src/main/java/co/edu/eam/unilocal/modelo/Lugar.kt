package co.edu.eam.unilocal.modelo

import java.util.*
import kotlin.collections.ArrayList

class Lugar(var id:Int,
            var nombre:String,
            var descripcion:String,
            var idCreador:Int,
            var estado:EstadoLugar,
            var idCategoria:Int,
            var direccion:String,
            var latitud:Float, var longitud:Float,
            var idCiudad:Int
) {

    var imagenes:ArrayList<String> = ArrayList()
    var telefonos:ArrayList<String> = ArrayList()
    var fecha: Date = Date()
    var horarios:ArrayList<Horario> = ArrayList()

    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, latitud=$latitud, longitud=$longitud, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }

}