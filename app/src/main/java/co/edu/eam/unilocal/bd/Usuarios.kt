package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.modelo.Usuario

object Usuarios {

    private val lista:ArrayList<Usuario> = ArrayList()

    init {

        lista.add( Usuario(1, "Carlos", "carlos", "carlos@email.com", "1203") )
        lista.add( Usuario(2, "Pepito", "pepe", "pepe@email.com", "3451") )
        lista.add( Usuario(3, "Laura", "laura", "laura@email.com", "6543") )
        lista.add( Usuario(4, "Marcos", "marcos", "marcos@email.com", "8635") )
        lista.add( Usuario(5, "Maria", "maria", "maria@email.com", "5437") )

    }

    fun listar():ArrayList<Usuario>{
        return lista
    }

    fun agregar(usuario: Usuario){
        lista.add(usuario)
    }

    fun obtener(id:Int): Usuario?{
        return lista.firstOrNull { u -> u.id == id }
    }

    fun login(correo:String, password:String):Usuario{
        val respuesta = lista.first { u -> u.password == password && u.correo == correo }
        return respuesta
    }


}