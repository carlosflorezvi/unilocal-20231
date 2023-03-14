package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.modelo.Categoria

object Categorias {

    private val lista:ArrayList<Categoria> = ArrayList()

    init {
        lista.add( Categoria(1, "Hotel", "\uf594") )
        lista.add( Categoria(2, "Café", "\uf7b6") )
        lista.add( Categoria(3, "Restaurante", "\uf2e7") )
        lista.add( Categoria(4, "Parque", "\uf1bb") )
        lista.add( Categoria(5, "Bar", "\uf0fc") )
    }

    fun listar():ArrayList<Categoria>{
        return lista
    }

    fun obtener(id:Int):Categoria?{
        return lista.firstOrNull { c -> c.id == id }
    }

}