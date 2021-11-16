package modelos;

import java.sql.Date;

public class Reposicion {

    private int idReposicion;
    private Empleado solicitante;
    private Empleado reponedor;
    private Date instanteSolicitud;
    private Date instanteCompletado;
    private Consumible consumible;
    private Ubicacion ubicacionTienda;
    private Ubicacion ubicacionesAlmacen;
    private int cantidad;
    private EstadoReposicion estado;
    
    public Reposicion() {
	idReposicion = 0;
	solicitante = new Empleado();
	setReponedor(new Empleado());
	instanteSolicitud = Date.valueOf("2021-09-08 10:00:00");
	setInstanteCompletado(Date.valueOf("2021-09-08 10:25:00"));
	consumible = new Consumible();
	ubicacionTienda = new Ubicacion(Localizacion.Tienda);
	ubicacionesAlmacen = new Ubicacion(Localizacion.Almacen);
	cantidad = 5;
	setEstado(EstadoReposicion.Pendiente);
    }
    
    public Reposicion(int idReposicion, Empleado solicitante, Empleado reponedor, Date instanteSolicitud, 
    		      Date instanteCompletado, Consumible consumible, Ubicacion ubicacionTienda, 
    		      Ubicacion ubicacionAlmacen, int cantidad, EstadoReposicion estado) {
    	
    	this.idReposicion = idReposicion;
    	this.solicitante = solicitante;
    	setReponedor(reponedor);
    	this.instanteSolicitud = instanteSolicitud;
    	setInstanteCompletado(instanteCompletado);
    	this.consumible = consumible;
    	this.ubicacionTienda = ubicacionTienda;
    	this.ubicacionesAlmacen = ubicacionAlmacen;
    	this.cantidad = cantidad;
    	setEstado(estado);
    }
    
    public int getIdReposicion() {
    	return idReposicion;
    }
    
    public Empleado getSolicitante() {
    	return solicitante;
    }
    
    public Empleado getReponedor() {
    	return reponedor;
    }
    
    public void setReponedor(Empleado reponedor) {
    	this.reponedor = reponedor;
    }
    
    public Date getInstanteSolicitud() {
    	return instanteSolicitud;
    }
    
    public Date getInstanteCompletado() {
    	return instanteCompletado;
    }
    
    public void setInstanteCompletado(Date instanteCompletado) {
    	this.instanteCompletado = instanteCompletado;
    }
    
    public Consumible getConsumible() {
    	return consumible;
    }
    
    public Ubicacion getUbicacionTienda() {
    	return ubicacionTienda;
    }
    
    public Ubicacion getUbicacionAlmacen() {
    	return ubicacionesAlmacen;
    }
    
    public int getCantidad() {
	return cantidad;
    }
    
    public EstadoReposicion getEstado() {
    	return estado;
    }
    
    public void setEstado(EstadoReposicion estado) {
    	this.estado = estado;
    }
    
}
