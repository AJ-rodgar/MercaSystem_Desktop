package utiles;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelos.Pedido;

@SuppressWarnings("serial")
public class PedidosTableModel extends AbstractTableModel{

    String[] columnNames = {"Estado","Proveedor","Fecha Pedido","Fecha Entrega"};
    
    List<Pedido> pedidos;
    
    public PedidosTableModel(List<Pedido> pedidos) {
	this.pedidos = pedidos;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    
    @Override
    public int getRowCount() {
	return pedidos.size() + 1;
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
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    switch(columnIndex) {
    	    	case 0:
                    return pedidos.get(rowIndex).getEstadoPedido();
                case 1:
                    return pedidos.get(rowIndex).getProveedor().getNombreProveedor();
                case 2:
                    return sdf.format(pedidos.get(rowIndex).getFechaPedido());
                case 3:
                    if (pedidos.get(rowIndex).getFechaEntrega() == null) {
                	return " ";
                    } else {
                	return sdf.format(pedidos.get(rowIndex).getFechaEntrega());
                    }
                default:
                    return " ";
	    }
	}
    }
    
    public Pedido getPedidoAt(int rowIndex) {
	if (rowIndex == 0) {
	    return null;
	}
	return pedidos.get(rowIndex-1);
    }
}
