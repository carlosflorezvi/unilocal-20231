package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.modelo.Administrador

object Administradores {

    private val lista:ArrayList<Administrador> = ArrayList()

    init {

        lista.add( Administrador(1, "Admin1", "admin1@email.com", "3413"))
        lista.add( Administrador(2, "Admin2", "admin2@email.com", "5655"))
    }

    fun obtener(id:Int): Administrador?{
        return lista.firstOrNull { a -> a.id == id }
    }

    fun login(correo:String, password:String): Administrador {
        val respuesta = lista.first { a -> a.password == password && a.correo == correo }
        return respuesta
    }

}