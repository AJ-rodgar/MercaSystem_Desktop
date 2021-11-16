package modelos;

public class UnidadMedida {

    private int idUnidadMedida;
    private String unidad;
    private TipoMedida tipoMedida;
    
    public UnidadMedida() {
	idUnidadMedida = 0;
	unidad = "ml";
	tipoMedida = TipoMedida.Volumen;
    }
    
    public UnidadMedida(int idUnidadMedida, String unidad, TipoMedida tipoMedida) {
	this.idUnidadMedida = idUnidadMedida;
	this.unidad = unidad;
	this.tipoMedida = tipoMedida;
    }

    public int getIdUnidadMedida() {
	return idUnidadMedida;
    }

    public String getUnidad() {
	return unidad;
    }

    public TipoMedida getTipoMedida() {
	return tipoMedida;
    }
}
