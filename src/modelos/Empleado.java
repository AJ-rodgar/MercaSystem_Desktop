package modelos;

import java.sql.Date;

public class Empleado extends Persona{

    private int idEmpleado;
    private EstadoEmpleado estadoEmpleado;
    private Rol rol;
	
    public Empleado() {
	super();
	idEmpleado = 0;
	setEstadoEmpleado(EstadoEmpleado.Activo);
	setRol(new Rol());
    }
	
    public Empleado(int idEmpleado, String nombre, String apellido1, String apellido2,
	    	    Date fechaNacimiento, DireccionPostal direccion, String telefono, 
	    	    String email, String usuario, String contrasenya, EstadoEmpleado estadoEmpleado, Rol rol) {
	super(nombre, apellido1, apellido2, fechaNacimiento, direccion, telefono, email,
		usuario, contrasenya);
	this.idEmpleado = idEmpleado;
	setEstadoEmpleado(estadoEmpleado);
	setRol(rol);
    }

    public int getIdEmpleado() {
	return idEmpleado;
    }
    
    public EstadoEmpleado getEstadoEmpleado() {
    	return estadoEmpleado;
    }
    
    public void setEstadoEmpleado(EstadoEmpleado estadoEmpleado) {
    	this.estadoEmpleado = estadoEmpleado;
    }
    
    public Rol getRol() {
    	return rol;
    }
    
    public void setRol(Rol rol) {
    	this.rol = rol;
    }
	
}
