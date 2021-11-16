package modelos;

import java.sql.Date;

public abstract class Persona {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private Date fechaNacimiento;
    private DireccionPostal direccion;
    private String telefono;
    private String email;
    private String nombreUsuario;
    private String contrasenya;
	
    public Persona() {
	setNombre("Merlí");
	setApellido1("Bergeron");
	setApellido2("García");
	setFechaNacimiento(Date.valueOf("1980-10-10"));
	setDireccionPostal(new DireccionPostal());
	setTelefono("000000000");
	setEmail("merlibergeron@gmail.com");
	setNombreUsuario("Peripatetico");
	setContrasenya("Plotino");
    }
    
    public Persona(String nombre, String apellido1, String apellido2, Date fechaNacimiento, DireccionPostal direccionPostal,
    			   String telefono, String email, String usuario, String contrasenya) {
    	setNombre(nombre);
    	setApellido1(apellido1);
    	setApellido2(apellido2);
    	setFechaNacimiento(fechaNacimiento);
    	setDireccionPostal(direccionPostal);
    	setTelefono(telefono);
    	setEmail(email);
    	setNombreUsuario(usuario);
    	setContrasenya(contrasenya);
    }
    
    public String getNombre() {
	return nombre;
    }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public String getApellido1() {
    	return apellido1;
    }
    
    public void setApellido1(String apellido1) {
    	this.apellido1 = apellido1;
    }
    
    public String getApellido2() {
    	return apellido2;
    }
    
    public void setApellido2(String apellido2) {
    	this.apellido2 = apellido2;
    }
    
    public Date getFechaNacimiento() {
    	return fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
    	this.fechaNacimiento = fechaNacimiento;
    }
    
    public DireccionPostal getDireccionPostal() {
    	return direccion;
    }
    
    public void setDireccionPostal(DireccionPostal direccion) {
    	this.direccion = direccion;
    }
    
    public String getTelefono() {
    	return telefono;
    }
    
    public void setTelefono(String telefono) {
    	this.telefono = telefono;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getNombreUsuario() {
    	return nombreUsuario;
    }
    
    public void setNombreUsuario(String usuario) {
    	this.nombreUsuario = usuario;
    }
    
    public String getContrasenya() {
    	return contrasenya;
    }
    
    public void setContrasenya(String contrasenya) {
    	this.contrasenya = contrasenya;
    }
	
}
