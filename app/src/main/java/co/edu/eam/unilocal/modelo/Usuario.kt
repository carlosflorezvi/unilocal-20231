package co.edu.eam.unilocal.modelo

class Usuario(id: Int, nombre: String, var nickname:String, correo: String, password: String): Persona(id, nombre, correo, password){

    var favoritos:ArrayList<Int> = ArrayList()

    fun esFavorito(codigo:Int):Boolean{
        return favoritos.contains(codigo)
    }

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }
}