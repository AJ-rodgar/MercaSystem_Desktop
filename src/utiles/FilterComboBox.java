package utiles;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings({ "serial", "rawtypes" })
public class FilterComboBox<T> extends JComboBox {
    
    private List<String> lista;

    @SuppressWarnings("unchecked")
    public FilterComboBox(List<T> listaObjetos) {
        super(listaObjetos.toArray());
        this.lista = new ArrayList<>();
        for (Object o : listaObjetos) {
            if (o == null) {
        	lista.add(" ");
            } else {
        	lista.add(o.toString());
            }  
        }
        this.setLightWeightPopupEnabled(false);
        this.setEditable(true);
        final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
        textfield.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        comboFilter(textfield.getText());
                    }
                });
            }
        });

    }

    @SuppressWarnings("unchecked")
    public void comboFilter(String enteredText) {
	List<String> listaFiltrada = new ArrayList<String>();
	
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).toLowerCase().contains(enteredText.toLowerCase())) {
                listaFiltrada.add(lista.get(i));
            }
        }
        
        if (listaFiltrada.size() > 0) {
            this.setModel(new DefaultComboBoxModel(listaFiltrada.toArray()));
            this.setSelectedItem(enteredText);
            this.showPopup();
        } else {
            this.hidePopup();
        }
    }

    @SuppressWarnings("unchecked")
    public void setItems(List<T> listaObjetos) {
	lista.clear();
	removeAll();
	for (Object o : listaObjetos) {
            if (o == null) {
        	lista.add(" ");
            } else {
        	lista.add(o.toString());
            }
            addItem(o);
        }
    }
    
}
