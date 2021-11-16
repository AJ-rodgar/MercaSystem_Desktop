package vistas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import modelos.Empleado;
import utiles.Estilos;

@SuppressWarnings("serial")
public class PantallaEmpleado extends JDialog {
    private JPanel contentPane;
    private Empleado empleado;
	
    private JSeparator separadorDatos;
    private JSeparator separadorDireccion;
    private JSeparator separadorCuenta;
	
    private JLabel etiquetaDatos;
    private JLabel nombre;
    private JLabel apellido1;
    private JLabel apellido2;
    private JLabel fechaNacimiento;
    private JLabel telefono;
    private JLabel email;
    private JLabel etiquetaDireccion;
    private JLabel provincia;
    private JLabel ciudad;
    private JLabel direccion;
    private JLabel etiquetaCuenta;
    private JLabel usuario;
    private JLabel contrasenya;
    private JLabel etiquetaEstado;
    private JLabel etiquetaRol;
    
    private JTextField campoNombre;
    private JTextField campoApellido1;
    private JTextField campoApellido2;
    private JTextField campoProvincia;
    private JTextField campoCiudad;
    private JTextField campoDireccion;
    private JTextField campoTelefono;
    private JTextField campoEmail;
    private JTextField campoUsuario;
    private JPasswordField campoContrasenya;
	
    private JCheckBox mostrarContrasenya;
	
    private JSpinner campoFecha;
	
    private JButton guardar;
    private JButton deshacer;
	
    private List<JTextField> camposObligatorios;
	
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    PantallaEmpleado frame = new PantallaEmpleado();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }
	
    public PantallaEmpleado() {
	setModal(true);
	setResizable(false);
	setTitle("Empleado");
	setIconImage(Estilos.LOGO);
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 750, 500);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	contentPane.setLayout(null);
	contentPane.setBackground(Estilos.PRINCIPAL_CLARO);
	setContentPane(contentPane);
	inicializaComponentes();
    }
	
    public PantallaEmpleado(Empleado empleado) {
	this.empleado = empleado;
	setModal(true);
	setResizable(false);
	setTitle("Empleado");
	setIconImage(Estilos.LOGO);
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 750, 500);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	contentPane.setLayout(null);
	contentPane.setBackground(Estilos.PRINCIPAL_CLARO);
	setContentPane(contentPane);
	inicializaComponentes(empleado);
    }

    private void inicializaComponentes() {
		
	camposObligatorios = new ArrayList<>();
		
	etiquetaDatos = new JLabel("DATOS PERSONALES");
	etiquetaDatos.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	etiquetaDatos.setForeground(Estilos.PRINCIPAL_OSCURO);
	etiquetaDatos.setBounds(20, 12, 260, 30);
		
	separadorDatos = new JSeparator();
	separadorDatos.setBounds(290, 25, 445, 2);
	separadorDatos.setForeground(Estilos.PRINCIPAL_OSCURO);
		
	nombre = new JLabel("Nombre");
	nombre.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	nombre.setBounds(20, 48, 100, 30);
		
	campoNombre = new JTextField();
	campoNombre.setFont(Estilos.FUENTE_CAMPOS);
	campoNombre.setBounds(125, 48, 200, 30);
	camposObligatorios.add(campoNombre);
		
	apellido1 = new JLabel("1º Apellido");
	apellido1.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	apellido1.setBounds(345, 48, 100, 30);
		
	campoApellido1 = new JTextField();
	campoApellido1.setFont(Estilos.FUENTE_CAMPOS);
	campoApellido1.setBounds(450, 48, 250, 30);
	camposObligatorios.add(campoApellido1);
		
	apellido2 = new JLabel("2º Apellido");
	apellido2.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	apellido2.setBounds(20, 89, 100, 30);
		
	campoApellido2 = new JTextField();
	campoApellido2.setFont(Estilos.FUENTE_CAMPOS);
	campoApellido2.setBounds(125, 89, 200, 30);
		
	etiquetaDireccion = new JLabel("DIRECCIÓN");
	etiquetaDireccion.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	etiquetaDireccion.setForeground(Estilos.PRINCIPAL_OSCURO);
	etiquetaDireccion.setBounds(20, 188, 150, 30);
		
	separadorDireccion = new JSeparator();
	separadorDireccion.setBounds(180, 203, 550, 2);
	separadorDireccion.setForeground(Estilos.PRINCIPAL_OSCURO);
		
	provincia = new JLabel("Provincia");
	provincia.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	provincia.setBounds(20, 241, 100, 30);
		
	ciudad = new JLabel("Ciudad");
	ciudad.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	ciudad.setBounds(345, 241, 85, 30);
		
	campoProvincia = new JTextField();
	campoProvincia.setFont(Estilos.FUENTE_CAMPOS);
	campoProvincia.setBounds(125, 241, 200, 30);
		
	campoCiudad = new JTextField();
	campoCiudad.setFont(Estilos.FUENTE_CAMPOS);
	campoCiudad.setBounds(450, 241, 250, 30);
		
	direccion = new JLabel("Direccion");
	direccion.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	direccion.setBounds(20, 282, 100, 30);
		
	campoDireccion = new JTextField();
	campoDireccion.setFont(Estilos.FUENTE_CAMPOS);
	campoDireccion.setBounds(125, 282, 575, 30);
	
	fechaNacimiento = new JLabel("Fecha de Nacimiento");
	fechaNacimiento.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	fechaNacimiento.setBounds(345, 89, 195, 30);
		
	SpinnerDateModel dateModel = new SpinnerDateModel();
	campoFecha = new JSpinner();
	campoFecha.setModel(dateModel);
	campoFecha.setFont(Estilos.FUENTE_CAMPOS);
	campoFecha.setBounds(550, 89, 150, 30);
	JSpinner.DateEditor editor = new JSpinner.DateEditor(campoFecha, "dd/MM/yyyy");
	campoFecha.setEditor(editor);
	
	telefono = new JLabel("Teléfono");
	telefono.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	telefono.setBounds(345, 130, 100, 30);
		
	campoTelefono = new JTextField();
	campoTelefono.setFont(Estilos.FUENTE_CAMPOS);
	campoTelefono.setBounds(450, 130, 250, 30);
	camposObligatorios.add(campoTelefono);
	
	email = new JLabel("Email");
	email.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	email.setBounds(20, 130, 100, 30);
		
	campoEmail = new JTextField();
	campoEmail.setFont(Estilos.FUENTE_CAMPOS);
	campoEmail.setBounds(125, 130, 200, 30);
	camposObligatorios.add(campoEmail);
	
	etiquetaCuenta = new JLabel("CUENTA");
	etiquetaCuenta.setForeground(Estilos.PRINCIPAL_OSCURO);
	etiquetaCuenta.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	etiquetaCuenta.setBounds(20, 335, 120, 30);
	
	separadorCuenta = new JSeparator();
	separadorCuenta.setForeground(Estilos.PRINCIPAL_OSCURO);
	separadorCuenta.setBounds(150, 350, 580, 2);
	
	usuario = new JLabel("Usuario");
	usuario.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	usuario.setBounds(20, 376, 70, 30);
	
	contrasenya = new JLabel("Contraseña");
	contrasenya.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	contrasenya.setBounds(20, 417, 100, 30);
	
	campoUsuario = new JTextField();
	campoUsuario.setFont(Estilos.FUENTE_CAMPOS);
	campoUsuario.setBounds(135, 376, 200, 30);
	camposObligatorios.add(campoUsuario);
		
	campoContrasenya = new JPasswordField();
	campoContrasenya.setFont(Estilos.FUENTE_CAMPOS);
	campoContrasenya.setBounds(135, 417, 200, 30);
	camposObligatorios.add(campoContrasenya);
		
	mostrarContrasenya = new JCheckBox("Mostrar contraseña");
	mostrarContrasenya.setFont(Estilos.FUENTE_CAMPOS);
	mostrarContrasenya.setBackground(Estilos.PRINCIPAL_CLARO);
	mostrarContrasenya.setBounds(345, 417, 195, 30);
		
	guardar = new JButton("Guardar");
	guardar.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	guardar.setBounds(560, 376, 120, 30);
		
	deshacer = new JButton("Deshacer");
	deshacer.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	deshacer.setBounds(560, 417, 120, 30);
		
	eventos();
		
	contentPane.add(etiquetaDatos);
	contentPane.add(separadorDatos);
	contentPane.add(nombre);
	contentPane.add(campoNombre);
	contentPane.add(apellido1);
	contentPane.add(campoApellido1);
	contentPane.add(apellido2);
	contentPane.add(campoApellido2);
	contentPane.add(fechaNacimiento);
	contentPane.add(campoFecha);
	contentPane.add(telefono);
	contentPane.add(campoTelefono);
	contentPane.add(email);
	contentPane.add(campoEmail);
	contentPane.add(etiquetaDireccion);
	contentPane.add(separadorDireccion);
	contentPane.add(provincia);
	contentPane.add(ciudad);
	contentPane.add(campoProvincia);
	contentPane.add(campoCiudad);
	contentPane.add(direccion);
	contentPane.add(campoDireccion);
	contentPane.add(etiquetaCuenta);
	contentPane.add(separadorCuenta);
	contentPane.add(usuario);
	contentPane.add(contrasenya);
	contentPane.add(campoUsuario);
	contentPane.add(campoContrasenya);
	contentPane.add(guardar);
	contentPane.add(deshacer);
	contentPane.add(mostrarContrasenya);
    }
    
    private void inicializaComponentes(Empleado empleado) {
		
	camposObligatorios = new ArrayList<>();
	
	etiquetaDatos = new JLabel("DATOS PERSONALES");
	etiquetaDatos.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	etiquetaDatos.setForeground(Estilos.PRINCIPAL);
	etiquetaDatos.setBounds(20, 12, 195, 30);
		
	separadorDatos = new JSeparator();
	separadorDatos.setBounds(220, 25, 515, 2);
	separadorDatos.setForeground(Estilos.PRINCIPAL);
	
	nombre = new JLabel("Nombre:");
	nombre.setFont(Estilos.FUENTE_CAMPOS);
	nombre.setBounds(20, 48, 100, 30);
	
	campoNombre = new JTextField();
	campoNombre.setFont(Estilos.FUENTE_CAMPOS);
	campoNombre.setBounds(125, 48, 200, 30);
	camposObligatorios.add(campoNombre);
	campoNombre.setText(empleado.getNombre());
		
	apellido1 = new JLabel("1º Apellido:");
	apellido1.setFont(Estilos.FUENTE_CAMPOS);
	apellido1.setBounds(345, 48, 100, 30);
	
	campoApellido1 = new JTextField();
	campoApellido1.setFont(Estilos.FUENTE_CAMPOS);
	campoApellido1.setBounds(450, 48, 250, 30);
	camposObligatorios.add(campoApellido1);
	campoApellido1.setText(empleado.getApellido1());
		
	apellido2 = new JLabel("2º Apellido:");
	apellido2.setFont(Estilos.FUENTE_CAMPOS);
	apellido2.setBounds(20, 89, 100, 30);
	
	campoApellido2 = new JTextField();
	campoApellido2.setFont(Estilos.FUENTE_CAMPOS);
	campoApellido2.setBounds(125, 89, 200, 30);
	campoApellido2.setText(empleado.getApellido2());
	
	fechaNacimiento = new JLabel("Fecha de Nacimiento:");
	fechaNacimiento.setFont(Estilos.FUENTE_CAMPOS);
	fechaNacimiento.setBounds(345, 89, 195, 30);
	
	SpinnerDateModel dateModel = new SpinnerDateModel();
	campoFecha = new JSpinner();
	campoFecha.setModel(dateModel);
	campoFecha.setFont(Estilos.FUENTE_CAMPOS);
	campoFecha.setBounds(550, 89, 150, 30);
	JSpinner.DateEditor editor = new JSpinner.DateEditor(campoFecha, "dd/MM/yyyy");
	campoFecha.setEditor(editor);
	campoFecha.setValue(empleado.getFechaNacimiento());
	
	email = new JLabel("Email:");
	email.setFont(Estilos.FUENTE_CAMPOS);
	email.setBounds(20, 130, 100, 30);
	
	campoEmail = new JTextField();
	campoEmail.setFont(Estilos.FUENTE_CAMPOS);
	campoEmail.setBounds(125, 130, 200, 30);
	camposObligatorios.add(campoEmail);
	campoEmail.setText(empleado.getEmail());
	
	telefono = new JLabel("Teléfono:");
	telefono.setFont(Estilos.FUENTE_CAMPOS);
	telefono.setBounds(345, 130, 100, 30);
	
	campoTelefono = new JTextField();
	campoTelefono.setFont(Estilos.FUENTE_CAMPOS);
	campoTelefono.setBounds(450, 130, 250, 30);
	camposObligatorios.add(campoTelefono);
	campoTelefono.setText(empleado.getTelefono());
	
	etiquetaDireccion = new JLabel("DIRECCIÓN");
	etiquetaDireccion.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	etiquetaDireccion.setForeground(Estilos.PRINCIPAL);
	etiquetaDireccion.setBounds(20, 188, 120, 30);
	
	separadorDireccion = new JSeparator();
	separadorDireccion.setBounds(145, 203, 585, 2);
	separadorDireccion.setForeground(Estilos.PRINCIPAL);
	
	provincia = new JLabel("Provincia");
	provincia.setFont(Estilos.FUENTE_CAMPOS);
	provincia.setBounds(20, 241, 100, 30);
	
	ciudad = new JLabel("Ciudad");
	ciudad.setFont(Estilos.FUENTE_CAMPOS);
	ciudad.setBounds(345, 241, 85, 30);
	
	campoProvincia = new JTextField();
	campoProvincia.setFont(Estilos.FUENTE_CAMPOS);
	campoProvincia.setBounds(125, 241, 200, 30);
	campoProvincia.setText(empleado.getDireccionPostal().getProvincia());
	
	campoCiudad = new JTextField();
	campoCiudad.setFont(Estilos.FUENTE_CAMPOS);
	campoCiudad.setBounds(450, 241, 250, 30);
	campoCiudad.setText(empleado.getDireccionPostal().getCiudad());
	
	direccion = new JLabel("Direccion");
	direccion.setFont(Estilos.FUENTE_CAMPOS);
	direccion.setBounds(20, 282, 100, 30);
	
	campoDireccion = new JTextField();
	campoDireccion.setFont(Estilos.FUENTE_CAMPOS);
	campoDireccion.setBounds(125, 282, 575, 30);
	campoDireccion.setText(empleado.getDireccionPostal().getDireccion());
	
	guardar = new JButton("Guardar");
	guardar.setFont(Estilos.FUENTE_CAMPOS);
	guardar.setBounds(560, 376, 120, 30);
	
	deshacer = new JButton("Deshacer");
	deshacer.setFont(Estilos.FUENTE_CAMPOS);
	deshacer.setBounds(560, 417, 120, 30);
	
	eventosRellenado();
		
	contentPane.add(etiquetaDatos);
	contentPane.add(separadorDatos);
	contentPane.add(nombre);
	contentPane.add(campoNombre);
	contentPane.add(apellido1);
	contentPane.add(campoApellido1);
	contentPane.add(apellido2);
	contentPane.add(campoApellido2);
	contentPane.add(fechaNacimiento);
	contentPane.add(campoFecha);
	contentPane.add(telefono);
	contentPane.add(campoTelefono);
	contentPane.add(email);
	contentPane.add(campoEmail);
	contentPane.add(etiquetaDireccion);
	contentPane.add(separadorDireccion);
	contentPane.add(provincia);
	contentPane.add(ciudad);
	contentPane.add(campoProvincia);
	contentPane.add(campoCiudad);
	contentPane.add(direccion);
	contentPane.add(campoDireccion);
	contentPane.add(guardar);
	contentPane.add(deshacer);
    }
	
    private void eventos() {
		
	campoTelefono.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent e) {
		char caracter = e.getKeyChar();
				
		if (caracter < '0' || caracter > '9' || campoTelefono.getText().length() >= 9) {
		    e.consume();
		}
	    }
	});
		
	mostrarContrasenya.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (mostrarContrasenya.isSelected()) {
		    campoContrasenya.setEchoChar((char)0);
		} else {
		    campoContrasenya.setEchoChar('*');
		}
	    }
	});
		
	guardar.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		if(compruebaCampos()) {
		    JOptionPane.showMessageDialog(contentPane, "Valido");
		} else {
		    JOptionPane.showMessageDialog(contentPane, "No Valido");
		}
		//TODO: Guardar
	    }
	});
		
	deshacer.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		for (JTextField tf : camposObligatorios) {
		    tf.setText("");
		}
		campoApellido2.setText("");
		campoFecha.setValue(Date.valueOf(LocalDate.now()));
		campoProvincia.setText("");
		campoCiudad.setText("");
		campoDireccion.setText("");
	    }
	});
    }
	
    private void eventosRellenado() {
		
	campoTelefono.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent e) {
		char caracter = e.getKeyChar();
		
		if (caracter < '0' || caracter > '9' || campoTelefono.getText().length() >= 9) {
		    e.consume();
		}
	    }
	});
	
	guardar.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		if(compruebaCamposRellenado()) {
		    JOptionPane.showMessageDialog(contentPane, "Valido");
		} else {
		    JOptionPane.showMessageDialog(contentPane, "No Valido");
		}
		//TODO: Guardar
	    }
	});
		
	deshacer.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		campoNombre.setText(empleado.getNombre());
		campoApellido1.setText(empleado.getApellido1());
		campoApellido2.setText(empleado.getApellido2());
		campoFecha.setValue(empleado.getFechaNacimiento());
		campoEmail.setText(empleado.getEmail());
		campoTelefono.setText(empleado.getTelefono());
		campoProvincia.setText(empleado.getDireccionPostal().getProvincia());
		campoCiudad.setText(empleado.getDireccionPostal().getCiudad());
		campoDireccion.setText(empleado.getDireccionPostal().getDireccion());
	    }
	});
    }

    private boolean compruebaCampos() {
	
	boolean valido = true;
	
	for (JTextField tf : camposObligatorios) {
	    if(tf.getText().equals("")) {
		valido = false;
		break;
	    }
	}
		
	if (campoContrasenya.getPassword().length < 8) {
	    valido = false;
	}
		
	return valido && compruebaEmail() && compruebaFecha();
    }
	
    private boolean compruebaCamposRellenado() {
		
	boolean valido = true;
		
	for (JTextField tf : camposObligatorios) {
	    if(tf.getText().equals("")) {
		valido = false;
		break;
	    }
	}
		
	return valido && compruebaEmail() && compruebaFecha();
    }
    
    private boolean compruebaEmail() {
	String correo = campoEmail.getText();
	boolean valido = true;
		
	if (correo.equals("")) {
	    valido = false;
	} else {
	    int contador = 0;
	    int posicionArroba = 0, posicionPunto = 0;
	    boolean duplicado = false;
	    
	    for(int i = 0; i < correo.length(); i++) {
		if(correo.charAt(i) == '@') {
		    contador++;
		    posicionArroba = i;
		    
		    if(contador >= 2) {
			duplicado = true;
			break;
		    }
		}
		if (correo.charAt(i) == '.') {
		    posicionPunto = i;
		}
	    }

	    if (posicionArroba < 1 || posicionPunto < posicionArroba + 2 || posicionPunto + 2 >= correo.length() || duplicado) {
		valido = false;
	    }
	}
		
	return valido;
    }

    private boolean compruebaFecha() {
	Date fecha = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(campoFecha.getValue()));
	Date limiteInferior = Date.valueOf("1945-09-02");
	Date limiteSuperior = Date.valueOf(LocalDate.now().minusYears(16));
	return fecha.after(limiteInferior) && fecha.before(limiteSuperior);
    }

}
