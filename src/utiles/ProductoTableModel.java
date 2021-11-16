package utiles;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelos.Producto;

@SuppressWarnings("serial")
public class ProductoTableModel extends AbstractTableModel{
    
    String[] columnNames = {"Categoria","Proveedor","Nombre"};
    
    List<Producto> productos;
    
    public ProductoTableModel(List<Producto> productos) {
	this.productos = productos;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    
    @Override
    public int getRowCount() {
	return productos.size() + 1;
    }

    @Override
    public int getColumnCount() {
	return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	if (rowIndex == 0) {
	    return columnNames[columnIndex];
	} else {
	    rowIndex--;
	    switch(columnIndex) {
    	    	case 0:
    	    	    return productos.get(rowIndex).getCategoria().getNombreCategoria();
                case 1:
                    return productos.get(rowIndex).getProveedor().getNombreProveedor();
                case 2:
                    return productos.get(rowIndex).getNombreProducto();
                default:
                    return " ";
	    }
	}
    }
    
    public Producto getProductoAt(int rowIndex) {
	if (rowIndex == 0) {
	    return null;
	}
	return productos.get(rowIndex-1);
    }
}
