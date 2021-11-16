package modelos;

import java.sql.Date;
import java.time.LocalDate;

public class Informe {

    private int idInforme;
    private Empleado analista;
    private Date fechaInforme;
    private String detalle;
    
    public Informe() {
	idInforme = 0;
	analista = new Empleado();
	fechaInforme = Date.valueOf(LocalDate.now());
	setDetalle("");
    }
    
    public Informe(int idInforme, Empleado analista, Date fechaInforme, String detalle) {
	this.idInforme = idInforme;
	this.analista = analista;
	this.fechaInforme = fechaInforme;
	setDetalle(detalle);
    }

    public int getIdInforme() {
	return idInforme;
    }

    public Empleado getAnalista() {
	return analista;
    }

    public Date getFechaInforme() {
	return fechaInforme;
    }

    public String getDetalle() {
	return detalle;
    }

    public void setDetalle(String detalle) {
	this.detalle = detalle;
    }
}
