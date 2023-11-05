package co.edu.eam.unilocal.modelo

class Categoria() {

    constructor(id:Int, nombre:String, icono:String):this(){
        this.id = id
        this.nombre = nombre
        this.icono = icono
    }

    var id:Int = 0
    var key:String = ""
    var nombre:String = ""
    var icono:String = ""

    override fun toString(): String {
        return "Categoria(id=$id, nombre='$nombre', icono='$icono')"
    }
}