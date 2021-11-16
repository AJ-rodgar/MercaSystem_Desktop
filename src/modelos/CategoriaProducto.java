package modelos;

public class CategoriaProducto {

    private int idCategoria;
    private CategoriaProducto categoriaPadre;
    private String nombreCategoria;
    
    public CategoriaProducto() {
	idCategoria = 0;
	setCategoriaPadre(null);
	setNombreCategoria("Bebidas");
    }
    
    public CategoriaProducto(int idCategoria, CategoriaProducto categoriaPadre, String nombreCategoria) {
    	this.idCategoria = idCategoria;
    	this.setCategoriaPadre(categoriaPadre);
    	this.setNombreCategoria(nombreCategoria);
    }
    
    public int getIdCategoria() {
    	return idCategoria;
    }
    
    public CategoriaProducto getCategoriaPadre() {
    	return categoriaPadre;
    }
    
    public void setCategoriaPadre(CategoriaProducto categoriaPadre) {
    	this.categoriaPadre = categoriaPadre;
    }
    
    public String getNombreCategoria() {
    	return nombreCategoria;
    }
    
    public void setNombreCategoria(String nombreCategoria) {
    	this.nombreCategoria = nombreCategoria;
    }
    
    @Override
    public String toString() {
	return nombreCategoria;
    }
}
