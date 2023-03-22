package co.edu.eam.unilocal.modelo

class Moderador(id: Int, nombre: String, correo: String, password: String): Persona(id, nombre, correo, password) {

    override fun toString(): String {
        return "Moderador() ${super.toString()}"
    }

}