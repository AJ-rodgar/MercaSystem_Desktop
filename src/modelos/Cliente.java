package modelos;

import java.sql.Date;

public class Cliente extends Persona{
    private int idCliente;

    public Cliente() {
	super();
	idCliente = 0;
    }
    
    public Cliente(int idCliente, String nombre, String apellido1, String apellido2, 
	    	   Date fechaNacimiento, DireccionPostal direccion, String telefono,
	    	   String email, String usuario, String contrasenya) {
        super(nombre, apellido1, apellido2, fechaNacimiento, direccion, telefono, email,
    	      usuario, contrasenya);
    	this.idCliente = idCliente;
    }
    
    public int getIdCliente() {
    	return idCliente;
    }
}
