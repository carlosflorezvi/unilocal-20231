package co.edu.eam.unilocal.modelo

import android.content.ContentValues
import co.edu.eam.unilocal.sqlite.UsuarioContrato

class Usuario() {

    var nombre:String = ""
    var nickname:String = ""
    var key:String = ""
    var rol:Rol = Rol.CLIENTE

    constructor(nombre: String, nickname:String, rol:Rol):this (){
        this.nombre = nombre
        this.nickname = nickname
        this.rol = rol
    }

    fun toContentValues():ContentValues{

        val values = ContentValues()
        values.put(UsuarioContrato.NOMBRE, nombre )
        values.put(UsuarioContrato.NICKNAME, nickname )
        //values.put(UsuarioContrato.CORREO, correo )
        //values.put(UsuarioContrato.PASSWORD, password )

        return values
    }

}