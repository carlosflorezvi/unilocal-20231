package co.edu.eam.unilocal.modelo

class Usuario(id: Int, nombre: String, var nickname:String, correo: String, password: String): Persona(id, nombre, correo, password){

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }
}