package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.modelo.Ciudad

object Ciudades {

    private val lista:ArrayList<Ciudad> = ArrayList()

    init {
        lista.add( Ciudad(1, "Armenia") )
        lista.add( Ciudad(2, "Salento") )
        lista.add( Ciudad(3, "Pereira") )
        lista.add( Ciudad(4, "Calarcá") )
        lista.add( Ciudad(5, "Manizales") )
    }

    fun listar():ArrayList<Ciudad>{
        return lista
    }

    fun obtener(id:Int): Ciudad?{
        return lista.firstOrNull { c -> c.id == id }
    }

}