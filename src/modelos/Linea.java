package modelos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Linea {

    private Consumible consumible;
    private int cantidad;
    private double precioTotal;
	
    public Linea(boolean compra) {
	consumible = new Consumible();
	cantidad = 6;
        if (compra) {
            precioTotal = new BigDecimal(consumible.getPrecioCompra() * cantidad).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } else {
            precioTotal = new BigDecimal(consumible.getPrecioVenta() * cantidad).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
    
    }
    
    public Linea(Consumible consumible, int cantidad, double precioTotal) {
	this.consumible = consumible;
	this.cantidad = cantidad;
	this.precioTotal = precioTotal;
    }

    public Consumible getConsumible() {
	return consumible;
    }

    public int getCantidad() {
	return cantidad;
    }
    
    public double getPrecioTotal() {
        return precioTotal;
    }
    
}
