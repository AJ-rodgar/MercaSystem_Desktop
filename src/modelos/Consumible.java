package modelos;

import java.util.ArrayList;
import java.util.List;

public class Consumible {
	
    private int idConsumible;
    private Producto producto;
    private double peso;
    private String recipiente;
    private UnidadMedida unidadMedida;
    private double tamanyo;
    private double precioCompra;
    private double precioVenta;
    private boolean descatalogado;
    
    public Consumible() {
	idConsumible = 0;
	producto = new Producto();
	peso = 0.375;
	setRecipiente("Lata");
	unidadMedida = new UnidadMedida();
	setTamanyo(330);
	setPrecioCompra(0.30);
	setPrecioVenta(0.58);
	setDescatalogado(false);
    }
    
    public Consumible(int idConsumible, Producto producto, double peso, 
	    	      String recipiente, UnidadMedida unidadMedida, double tamanyo, double precioCompra, 
	    	      double precioVenta, boolean descatalogado) {
	this.idConsumible = idConsumible;
	this.producto = producto;
	this.peso = peso;
    	setRecipiente(recipiente);
    	this.unidadMedida = unidadMedida;
    	setTamanyo(tamanyo);
    	setPrecioCompra(precioCompra);
    	setPrecioVenta(precioVenta);
    	setDescatalogado(descatalogado);
    }
    
    public int getIdConsumible() {
    	return idConsumible;
    }
    
    public Producto getProducto() {
    	return producto;
    }
    
    public double getPeso() {
	return peso;
    }

    public String getRecipiente() {
    	return recipiente;
    }
    
    public void setRecipiente(String recipiente) {
    	this.recipiente = recipiente;
    }
    
    public UnidadMedida getUnidadMedida() {
	return unidadMedida;
    }

    public double getTamanyo() {
    	return tamanyo;
    }
    
    public void setTamanyo(double tamanyo) {
    	this.tamanyo = tamanyo;
    }
    
    public double getPrecioCompra() {
    	return precioCompra;
    }
    
    public void setPrecioCompra(double precioCompra) {
    	this.precioCompra = precioCompra;
    }
    
    public double getPrecioVenta() {
    	return precioVenta;
    }
    
    public void setPrecioVenta(double precioVenta) {
    	this.precioVenta = precioVenta;
    }
    
    public boolean isDescatalogado() {
    	return descatalogado;
    }
    
    public void setDescatalogado(boolean descatalogado) {
    	this.descatalogado = descatalogado;
    }
    
    @Override
    public String toString() {
	return producto.getNombreProducto() + " " + tamanyo + " " + unidadMedida.getUnidad();
    }
}
