package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.modelo.Moderador

object Moderadores {

    private val lista:ArrayList<Moderador> = ArrayList()

    init {
        lista.add( Moderador(1, "Moderador1", "mode1@email.com", "1234"))
        lista.add( Moderador(2, "Moderador2", "mode2@email.com", "1234"))
    }

    fun listar():ArrayList<Moderador>{
        return lista
    }

    fun obtener(id:Int): Moderador?{
        return lista.firstOrNull { a -> a.id == id }
    }

}