package utiles;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelos.Proveedor;

@SuppressWarnings("serial")
public class ProveedorTableModel extends AbstractTableModel{
    
    String[] columnNames = {"Nombre","Email","Telefono"};
    
    List<Proveedor> proveedores;
    
    public ProveedorTableModel(List<Proveedor> proveedores) {
	this.proveedores = proveedores;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    
    @Override
    public int getRowCount() {
	return proveedores.size() + 1;
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
    	    	    return proveedores.get(rowIndex).getNombreProveedor();
                case 1:
                    return proveedores.get(rowIndex).getEmail();
                case 2:
                    return proveedores.get(rowIndex).getTelefono();
                default:
                    return " ";
	    }
	}
    }
    
    public Proveedor getProveedorAt(int rowIndex) {
	if (rowIndex == 0) {
	    return null;
	}
	return proveedores.get(rowIndex-1);
    }
}
