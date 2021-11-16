package modelos;

public class Producto {
	
    private int idProducto;
    private CategoriaProducto categoria;
    private Proveedor proveedor;
    private String nombreProducto;
	
    public Producto() {
	idProducto = 0;
	setCategoria(new CategoriaProducto());
	setProveedor(new Proveedor());
	setNombreProducto("Coca Cola Zero");
    }
	
    public Producto(int idProducto, CategoriaProducto categoria, Proveedor proveedor, String nombreProducto) {
	this.idProducto = idProducto;
	setCategoria(categoria);
	setProveedor(proveedor);
	setNombreProducto(nombreProducto);
    }

    public int getIdProducto() {
	return idProducto;
    }

    public CategoriaProducto getCategoria() {
	return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
	this.categoria = categoria;
    }

    public Proveedor getProveedor() {
	return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
	this.proveedor = proveedor;
    }

    public String getNombreProducto() {
	return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
	this.nombreProducto = nombreProducto;
    }
    
    @Override
    public String toString() {
	return nombreProducto;
    }

}
