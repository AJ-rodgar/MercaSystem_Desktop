package modelos;

public enum EstadoEmpleado {
	Activo, 
	TemporalmenteFuera{
	    public String toString() {
		return "Temporalmente Fuera";
	    }
	}, 
	Inactivo, 
	Despedido
}
