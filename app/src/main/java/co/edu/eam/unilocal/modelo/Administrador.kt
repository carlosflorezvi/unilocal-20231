package co.edu.eam.unilocal.modelo

class Administrador(var id:Int, var nombre:String, var correo:String, var password:String) {

    override fun toString(): String {
        return "Administrador(id=$id, nombre='$nombre', correo='$correo', password='$password')"
    }
}