package modelos;

public enum EstadoReposicion {
	Pendiente, 
	En_Proceso {
	    public String toString() {
		return "En proceso";
	    }
	}, 
	Completado
}
