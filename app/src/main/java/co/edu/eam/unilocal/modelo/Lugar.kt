package co.edu.eam.unilocal.modelo

import android.content.ContentValues
import co.edu.eam.unilocal.sqlite.LugarContrato
import co.edu.eam.unilocal.sqlite.UsuarioContrato
import java.util.*
import kotlin.collections.ArrayList

class Lugar() {

    constructor( nombre:String, descripcion:String, idCreador:String, estado:EstadoLugar, idCategoria:Int, direccion:String, posicion: Posicion, idCiudad:Int):this(){
        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.idCategoria = idCategoria
        this.direccion = direccion
        this.posicion = posicion
        this.idCiudad = idCiudad
    }

    constructor( nombre:String, descripcion:String, idCreador:String, estado:EstadoLugar, idCategoria:Int, direccion:String, idCiudad:Int):this(){
        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.idCategoria = idCategoria
        this.direccion = direccion
        this.idCiudad = idCiudad
    }

    constructor(id:Int, nombre: String, descripcion: String, lat:Double, lng:Double, direccion: String, idCategoria: Int, idCreador: String):this(){
        this.nombre = nombre
        this.descripcion = descripcion

        val pos = Posicion(lat, lng)
        this.posicion = pos

        this.direccion = direccion
        this.idCategoria = idCategoria
        this.idCreador = idCreador
    }

    var key:String = ""
    var nombre:String = ""
    var descripcion:String = ""
    var idCreador:String = ""
    var estado:EstadoLugar = EstadoLugar.SIN_REVISAR
    var idCategoria:Int = 0
    var direccion:String = ""
    var posicion: Posicion = Posicion()
    var idCiudad:Int = 0
    var imagenes:ArrayList<String> = ArrayList()
    var telefonos:ArrayList<String> = ArrayList()
    var fecha: Date = Date()
    var horarios:ArrayList<Horario> = ArrayList()

    fun estaAbierto():Boolean{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)
        //val minuto = fecha.get(Calendar.MINUTE)

        var mensaje = false

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) && hora < horario.horaCierre && hora > horario.horaInicio  ){
                mensaje = true
                break
            }
        }

        return mensaje
    }

    fun obtenerHoraCierre():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)

        var mensaje = ""

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) ){
                mensaje = "${horario.horaCierre}:00"
                break
            }
        }

        return mensaje
    }

    fun obtenerHoraApertura():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)

        var mensaje = ""
        var pos:Int

        for(horario in horarios){
            pos = horario.diaSemana.indexOf( DiaSemana.values()[dia-1] )
            mensaje = if( pos!=-1 ){
                if( horario.horaInicio > hora ){
                    "${horario.diaSemana[pos].toString().lowercase()} a las ${horario.horaInicio}:00"
                }else{
                    "${horario.diaSemana[pos+1].toString().lowercase()} a las ${horario.horaInicio}:00"
                }
            }else{
                "${horario.diaSemana[0].toString().lowercase()} a las ${horario.horaInicio}:00"
            }
            break
        }

        return mensaje
    }

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(LugarContrato.NOMBRE, nombre )
        values.put(LugarContrato.DESCRIPCION, descripcion )
        values.put(LugarContrato.LAT, posicion.lat )
        values.put(LugarContrato.LNG, posicion.lng )
        values.put(LugarContrato.DIRECCION, direccion )
        values.put(LugarContrato.CATEGORIA, idCategoria )
        values.put(LugarContrato.ID_CREADOR, idCreador )
        values.put(LugarContrato.KEY_FIREBASE, key )

        return values
    }


}