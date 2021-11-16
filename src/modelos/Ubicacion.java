package modelos;

public class Ubicacion {

    private final double pesoMaximo = 25;
	
    private int idUbicacion;
    private Localizacion localizacion;
    private int pasillo;
    private int estanteria;
    private int balda;
    private Consumible consumible;
    private int cantidadConsumible;
    
    public Ubicacion(Localizacion localizacion) {
	idUbicacion = 0;
	this.localizacion = localizacion;
	setPasillo(1);
	setEstanteria(1);
	setBalda(1);
	setConsumible(new Consumible());
	cantidadConsumible = 12;
    }
    
    public Ubicacion(int idUbicacion, Localizacion localizacion, int pasillo, int estanteria, int balda, Consumible consumible, int cantidadConsumible) {
	this.idUbicacion = idUbicacion;
    	this.localizacion = localizacion;
    	setPasillo(pasillo);
    	setEstanteria(estanteria);
    	setBalda(balda);
    	setConsumible(consumible);
    	this.cantidadConsumible = cantidadConsumible;
    }
    
    public int getIdUbicacion() {
    	return idUbicacion;
    }
    
    public Localizacion getLocalizacion() {
    	return localizacion;
    }
    
    public int getPasillo() {
    	return pasillo;
    }
    
    public void setPasillo(int pasillo) {
    	this.pasillo = pasillo;
    }
    
    public int getEstanteria() {
    	return estanteria;
    }
    
    public void setEstanteria(int estanteria) {
    	this.estanteria = estanteria;
    }
    
    public int getBalda() {
    	return balda;
    }
    
    public void setBalda(int balda) {
    	this.balda = balda;
    }
    
    public double getPesoMaximo() {
    	return pesoMaximo;
    }
    
    public Consumible getConsumible() {
    	return consumible;
    }
    
    public void setConsumible(Consumible consumible) {
    	this.consumible = consumible;
    }
    
    public boolean isOcupado() {
    	return consumible != null;
    }
    
    public int getCantidadMaximaConsumible() {
    	return (int) Math.floor(pesoMaximo/consumible.getPeso());
    }
    
    public int getCantidadConsumible() {
    	return cantidadConsumible;
    }
    
    public void addConsumible(int cantidadConsumible) {
	if (getCantidadConsumible() + cantidadConsumible > getCantidadMaximaConsumible()) {
	    this.cantidadConsumible = getCantidadMaximaConsumible();
	} else {
	    this.cantidadConsumible += cantidadConsumible;
	}
    }
    
    public void removeConsumible(int cantidadConsumible) {
	if (getCantidadConsumible() - cantidadConsumible < 0) {
	    this.cantidadConsumible = 0;
	} else {
	    this.cantidadConsumible -= cantidadConsumible;
	}
    }
}
