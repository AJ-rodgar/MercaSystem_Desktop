package modelos;

public class Rol {
    
    private int idRol;
    private String nombre;
    
    public Rol() {
	idRol = 0;
	nombre = "Administrador";
    }
    
    public Rol(int idRol, String nombre) {
	this.idRol = idRol;
	this.nombre = nombre;
    }

    public int getRolId() {
	return idRol;
    }

    public String getNombre() {
	return nombre;
    }
    
    @Override
    public String toString() {
	return nombre;
    }

}
