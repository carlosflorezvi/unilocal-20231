package co.edu.eam.unilocal.modelo

class Ciudad() {

    constructor(id:Int, nombre:String):this(){
        this.id = id
        this.nombre = nombre
    }

    var id:Int = 0
    var key:String = ""
    var nombre:String = ""

    override fun toString(): String {
        return "Ciudad(id=$id, nombre='$nombre')"
    }
}