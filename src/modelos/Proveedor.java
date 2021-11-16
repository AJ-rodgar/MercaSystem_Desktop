package modelos;

public class Proveedor {

    private int idProveedor;
    private String nombreProveedor;
    private String email;
    private String telefono;
    private DireccionPostal direccionPostal;
    
    public Proveedor() {
	idProveedor = 0;
	setNombreProveedor("The Coca Cola Company");
	setEmail("cocacolaconil@CocaColaCompany.com");
	setTelefono("956443365");
	setDireccionPostal(new DireccionPostal());
    }
    
    public Proveedor(int idProveedor, String nombreProveedor, String email, String telefono, DireccionPostal direccion) {
    	this.idProveedor = idProveedor;
    	setNombreProveedor(nombreProveedor);
    	setEmail(email);
    	setTelefono(telefono);
    	setDireccionPostal(direccion);
    }
    
    public int getIdProveedor() {
    	return idProveedor;
    }
    
    public String getNombreProveedor() {
    	return nombreProveedor;
    }
    
    public void setNombreProveedor(String nombreProveedor) {
    	this.nombreProveedor = nombreProveedor;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getTelefono() {
    	return telefono;
    }
    
    public void setTelefono(String telefono) {
    	this.telefono = telefono;
    }
    
    public DireccionPostal getDireccionPostal() {
    	return direccionPostal;
    }
    
    public void setDireccionPostal(DireccionPostal direccionPostal) {
    	this.direccionPostal = direccionPostal;
    }
    
    @Override
    public String toString() {
	return nombreProveedor;
    }
}
