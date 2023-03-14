package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.modelo.Comentario

object Comentarios {

    private val lista:ArrayList<Comentario> = ArrayList()

    init {

        lista.add( Comentario(1, "Excelente servicio y buen ambiente", 1, 2, 5 ) )
        lista.add( Comentario(2, "Muy demorado, no volvería", 4, 1, 2 ) )
        lista.add( Comentario(3, "Buena comida mexicana, precios razonables", 3, 3, 4 ) )
        lista.add( Comentario(4, "El lugar es bonito pero muy lentos", 2, 2, 3 ) )
        lista.add( Comentario(5, "No volvería, los precios son muy altos", 5, 2, 2 ) )
        lista.add( Comentario(6, "Un hotel bien ubicado y con desayuno incluído. Recomendado.", 1, 5, 4 ) )

    }

    fun listar(idLugar:Int):ArrayList<Comentario>{
        return lista.filter { c -> c.idLugar == idLugar }.toCollection(ArrayList())
    }

    fun crear(comentario: Comentario){
        lista.add( comentario )
    }
}