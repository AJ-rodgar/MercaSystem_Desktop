package modelos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private int idCompra;
    private Cliente cliente;
    private List<Linea> listaCompra;
    private Date fechaCompra;
    private TipoCompra tipoCompra;
    
    public Compra() {
	idCompra = 0;
	cliente = new Cliente();
	listaCompra = new ArrayList<Linea>();
	fechaCompra = Date.valueOf(LocalDate.now());
	tipoCompra = TipoCompra.Fisica;
    }
    
    public Compra(int idCompra, Cliente cliente, List<Linea> listaCompra, Date fechaCompra, TipoCompra tipoCompra) {
    	this.idCompra = idCompra;
    	this.cliente = cliente;
    	this.listaCompra = listaCompra;
    	this.fechaCompra = fechaCompra;
    	this.tipoCompra = tipoCompra;
    }

    public int getIdCompra() {
    	return idCompra;
    }
    
    public Cliente getCliente() {
    	return cliente;
    }
    
    public double getPrecioTotal() {
	double res = 0;
	for (Linea l : listaCompra) {
	    res += l.getPrecioTotal();
	}
    	return res;
    }
    
    public Date getFechaCompra() {
    	return fechaCompra;
    }

    public List<Linea> getListaCompra() {
	return listaCompra;
    }
    
    public void addLinea(Consumible consumible, int cantidad) {
	double precio = new BigDecimal(consumible.getPrecioCompra() * cantidad).setScale(2, RoundingMode.HALF_UP).doubleValue();;
	
	for (Linea l : listaCompra) {
	    if (l.getConsumible().equals(consumible)) {
		listaCompra.remove(l);
	    }
	}
	
	listaCompra.add(new Linea(consumible, cantidad, precio));
    }
    
    public void removeLinea(Linea linea) {
	if (listaCompra.contains(linea)) {
	    listaCompra.remove(linea);
	}
    }
    
    public TipoCompra getTipoCompra() {
	return tipoCompra;
    }
}
