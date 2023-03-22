package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.modelo.Persona

object Personas {

    fun login(correo:String, password:String): Persona?{
        var respuesta:Persona?

        respuesta = Usuarios.listar().firstOrNull{ u -> u.password == password && u.correo == correo }

        if(respuesta == null){
            respuesta = Moderadores.listar().firstOrNull{ u -> u.password == password && u.correo == correo }

            if(respuesta == null) {
                respuesta = Administradores.listar().firstOrNull{ u -> u.password == password && u.correo == correo }
            }
        }

        return respuesta
    }

}