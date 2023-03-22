package co.edu.eam.unilocal.bd

import android.util.Log
import co.edu.eam.unilocal.modelo.EstadoLugar
import co.edu.eam.unilocal.modelo.Horario
import co.edu.eam.unilocal.modelo.Lugar

object Lugares {

    private val lista:ArrayList<Lugar> = ArrayList()

    init {

        val horario1 = Horario(1, Horarios.obtenerTodos(), 12, 20)
        val horario2 = Horario(2, Horarios.obtenerEntreSemana(), 9, 20)
        val horario3 = Horario(3, Horarios.obtenerFinSemana(), 14, 23)

        val lugar1 = Lugar(1, "Café ABC", "Excelente café para compartir", 1, EstadoLugar.ACEPTADO, 2, "Calle 123",73.3434f, -40.4345f, 1)
        lugar1.horarios.add(horario2)

        val lugar2 = Lugar(2, "Bar City Pub", "Bar en la ciudad de Armenia", 2, EstadoLugar.ACEPTADO, 5, "Calle 12 # 23-1",73.3434f, -40.4345f, 1)
        lugar2.horarios.add(horario1)

        val lugar3 = Lugar(3, "Restaurante Mi Cuate", "Comida Mexicana para todos", 3, EstadoLugar.ACEPTADO, 3, "Calle 452",73.3434f, -40.4345f, 5)
        lugar3.horarios.add(horario1)

        val lugar4 = Lugar(4, "BBC Norte Pub", "Cervezas BBC muy buenas", 1, EstadoLugar.ACEPTADO, 1, "Calle 53",73.3434f, -40.4345f, 3)
        lugar4.horarios.add(horario3)

        val lugar5 = Lugar(5, "Hotel barato", "Hotel para ahorrar mucho dinero", 1, EstadoLugar.ACEPTADO, 1, "Calle 23 # 34-1",73.3434f, -40.4345f, 1)
        lugar5.horarios.add( horario1 )

        val lugar6 = Lugar(6, "Hostal Hippie", "Alojamiento para todos y todas", 2, EstadoLugar.SIN_REVISAR, 1, "Carrera 123",73.3434f, -40.4345f, 2)
        lugar6.horarios.add( horario2 )

        lista.add( lugar1 )
        lista.add( lugar2 )
        lista.add( lugar3 )
        lista.add( lugar4 )
        lista.add( lugar5 )
        lista.add( lugar6 )

    }

    fun listarPorEstado(estado:EstadoLugar):ArrayList<Lugar>{
        return lista.filter { l -> l.estado == estado }.toCollection(ArrayList())
    }

    fun obtener(id:Int): Lugar?{
        return lista.firstOrNull { l -> l.id == id }
    }

    fun buscarNombre(nombre:String): ArrayList<Lugar> {
        return lista.filter { l -> l.nombre.lowercase().contains(nombre.lowercase()) && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun crear(lugar:Lugar){
        lista.add( lugar )
    }

    fun buscarCiudad(codigoCiudad:Int): ArrayList<Lugar> {
        return lista.filter { l -> l.idCiudad == codigoCiudad && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun buscarCategoria(codigoCategoria:Int): ArrayList<Lugar> {
        return lista.filter { l -> l.idCategoria == codigoCategoria && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }


    fun cambiarEstado(codigo:Int, nuevoEstado:EstadoLugar){

        val lugar = lista.firstOrNull{ l -> l.id == codigo}

        if(lugar!=null){
            lugar.estado = nuevoEstado
        }

    }

}