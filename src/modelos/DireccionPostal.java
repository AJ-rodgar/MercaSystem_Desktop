package modelos;

public class DireccionPostal {
    private String provincia;
    private String ciudad;
    private String direccion;
    
    public DireccionPostal() {
	setProvincia("Barcelona");
	setCiudad("Barcelona");
	setDireccion("Av. de Josep Tarradellas, 5");
    }
    
    public DireccionPostal(String provincia, String ciudad, String direccion) {
    	setProvincia(provincia);
    	setCiudad(ciudad);
    	setDireccion(direccion);
    }
    
    public String getProvincia() {
    	return provincia;
    }
    
    public void setProvincia(String provincia) {
    	this.provincia = provincia;
    }
    
    public String getCiudad() {
    	return ciudad;
    }
    
    public void setCiudad(String ciudad) {
    	this.ciudad = ciudad;
    }
    
    public String getDireccion() {
    	return direccion;
    }
    
    public void setDireccion(String direccion) {
    	this.direccion = direccion;
    }
}
