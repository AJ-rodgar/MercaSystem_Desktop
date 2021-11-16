package utiles;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelos.Empleado;

@SuppressWarnings("serial")
public class EmpleadoTableModel extends AbstractTableModel{

    String[] columnNames = {"Rol","Nombre","Estado"};
    
    List<Empleado> empleados;
    
    public EmpleadoTableModel(List<Empleado> empleados) {
	this.empleados = empleados;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    
    @Override
    public int getRowCount() {
	return empleados.size() + 1;
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
                    return empleados.get(rowIndex).getRol();
                case 1:
                    return empleados.get(rowIndex).getNombre() + " " + empleados.get(rowIndex).getApellido1() + " " + empleados.get(rowIndex).getApellido2();
                case 2:
                    return empleados.get(rowIndex).getEstadoEmpleado();
                default:
                    return " ";
	    }
	}
    }
    
    public Empleado getEmpleadoAt(int rowIndex) {
	if (rowIndex == 0) {
	    return null;
	}
	return empleados.get(rowIndex-1);
    }

}
