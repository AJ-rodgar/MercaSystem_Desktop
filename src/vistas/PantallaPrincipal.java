package vistas;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import modelos.CategoriaProducto;
import modelos.Consumible;
import modelos.DireccionPostal;
import modelos.Empleado;
import modelos.EstadoEmpleado;
import modelos.Pedido;
import modelos.Producto;
import modelos.Proveedor;
import modelos.Rol;
import utiles.EmpleadoTableModel;
import utiles.Estilos;
import utiles.FilterComboBox;
import utiles.PedidosTableModel;
import utiles.ProductoTableModel;
import utiles.ProveedorTableModel;
import utiles.Utiles;
import java.awt.Font;
import java.awt.Point;
import java.awt.ScrollPane;

@SuppressWarnings("serial")
public class PantallaPrincipal extends JFrame {

    private static Socket socket = null;
    private static PrintWriter flujo_cliente = null;
    private static BufferedReader flujo_servidor = null;
    
    private PantallaLogin login;
    
    private JPanel contentPane;
	
    private Empleado empleadoOriginal;
    private Empleado empleadoEditado;
    private String panelActivo = "-";
    private int contadorErrores = 3;
	
    private JPanel topPane;
    private JPanel leftPane;
    private JPanel mainPane;
    private JPanel logoPane;
	
    private JPanel backgroundPane;
    private JPanel personalPane;
    private JPanel proveedoresPane;
    private JPanel productosPane;
    private JPanel inventarioPane;
    private JPanel ventasPane;
    private JPanel pedidosPane;
    private JPanel reposicionesPane;
    private JPanel informesPane;
    private JPanel perfilPane;
    
    private JLabel logo;
    private JLabel mensajeBienvenida;
    private JLabel perfil;
    private JLabel logout;
	
    private JLabel personal;
    private JLabel proveedores;
    private JLabel productos;
    private JLabel inventario;
    private JLabel ventas;
    private JLabel pedidos;
    private JLabel reposiciones;
    private JLabel informes;
    
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    socket = new Socket("localhost", 4444);
		    flujo_cliente = new PrintWriter(socket.getOutputStream(), true);
		    flujo_servidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    String contrasenya = Utiles.codePassword("admin");	
		    flujo_cliente.println("C000-admin-" + contrasenya);
		    String mensaje = flujo_servidor.readLine();
		    Gson gson = new Gson();
		    Empleado emp = gson.fromJson(mensaje.substring(5), Empleado.class);
		    
		    PantallaPrincipal frame = new PantallaPrincipal(socket, flujo_cliente, flujo_servidor, null, emp);
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    public PantallaPrincipal(Socket socket, PrintWriter flujo_cliente,
	    		     BufferedReader flujo_servidor, PantallaLogin login, Empleado empleado) {
	this.socket = socket;
	this.flujo_cliente = flujo_cliente;
	this.flujo_servidor = flujo_servidor;
	this.login = login;
	this.empleadoOriginal = empleado;
	this.empleadoEditado = empleado;
	setIconImage(Estilos.LOGO);
	setTitle("MercaSystem");
	setResizable(false);
	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e){
		try {
		    Gson gson = new Gson();
		    flujo_cliente.println("C003-" + gson.toJson(empleadoOriginal));
		    System.out.println(flujo_servidor.readLine());
		    flujo_cliente.println("EXIT");
		    //login.dispose();
		    dispose();
		} catch (IOException e1) {
		    System.err.println("Couldn't get I/O for the connection to: MercaSystem_Server.");
		    System.exit(1);
		}
            }
	});
	setBounds(400, 100, 900, 700);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	iniciarComponentes();
    }

    private void iniciarComponentes() {
	
	topPane = new JPanel();
	topPane.setBounds(210, 0, 690, 100);
	topPane.setBackground(Estilos.PRINCIPAL);
	topPane.setLayout(null);
	
	leftPane = new JPanel();
	leftPane.setBounds(0, 100, 210, 600);
	leftPane.setBackground(Estilos.PRINCIPAL_OSCURO);
	leftPane.setLayout(null);
	
	mainPane = new JPanel(new CardLayout());
	mainPane.setBounds(210, 100, 690, 600);
	mainPane.setBackground(Estilos.PRINCIPAL_CLARO);
	
	logoPane = new JPanel();
	logoPane.setBackground(Estilos.PRINCIPAL);
	logoPane.setBounds(0, 0, 210, 100);
	logoPane.setLayout(null);
	
	logo = new JLabel(new ImageIcon(Estilos.LOGO));
	logo.setBackground(Estilos.PRINCIPAL);
	logo.setBounds(0, 0, 210, 100);
	
	logoPane.add(logo);
	
	mensajeBienvenida = new JLabel("Bienvenido, " + empleadoOriginal.getNombre() + " " + empleadoOriginal.getApellido1());
	mensajeBienvenida.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	mensajeBienvenida.setForeground(Color.WHITE);
	mensajeBienvenida.setBounds(25, 0, 400, 100);
	
	perfil = new JLabel("Editar perfil");
	perfil.setIcon(new ImageIcon(Estilos.USUARIO));
	perfil.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	perfil.setForeground(Color.WHITE);
	perfil.setBounds(515, 5, 165, 45);
	perfil.addMouseListener(new MouseAdapter()  
	{  
	    public void mouseClicked(MouseEvent e)  
	    {  
	    	CardLayout cl = (CardLayout)(mainPane.getLayout());
	    	if (!panelActivo.equals("Perfil")) {
	    	    cl.show(mainPane, "Perfil");
	    	    panelActivo = "Perfil";
	    	} else {
	    	    cl.show(mainPane, "Background");
	    	    panelActivo = "-";
	    	}
	    	
	    	
	    }  
	});
	
	logout = new JLabel("Cerrar sesión");
	logout.setIcon(new ImageIcon(Estilos.USUARIO));
	logout.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	logout.setForeground(Color.WHITE);
	logout.setBounds(515, 50, 165, 45);
	logout.addMouseListener(new MouseAdapter()  
	{  
	    public void mouseClicked(MouseEvent e)  
	    {  
		try {
		    Gson gson = new Gson();
		    flujo_cliente.println("C003-" + gson.toJson(empleadoOriginal));
		    System.out.println(flujo_servidor.readLine());
		    login.setVisible(true);
		    dispose();
		} catch (IOException e1) {
		    System.err.println("Couldn't get I/O for the connection to: MercaSystem_Server.");
		    System.exit(1);
		}
	    }  
	});
	
	topPane.add(mensajeBienvenida);
	topPane.add(perfil);
	topPane.add(logout);
	
	contentPane.add(topPane);
	contentPane.add(leftPane);
	contentPane.add(mainPane);
	contentPane.add(logoPane);	
	
	backgroundPane = new JPanel();
	backgroundPane.setBackground(Estilos.PRINCIPAL_CLARO);
	backgroundPane.setLayout(null);
	
	inicializaPersonalPane();
	inicializaProveedoresPane();
	inicializaProductosPane();
	inicializaPedidosPane();
	
	inicializaPerfilPane();
	seleccionaPaneles();
	
	
	
	mainPane.add(backgroundPane, "Background");
	mainPane.add(perfilPane, "Perfil");

	mainPane.add(personalPane, "Personal");
	mainPane.add(productosPane, "Productos");
	mainPane.add(proveedoresPane, "Proveedores");
	
	mainPane.add(pedidosPane, "Pedidos");
	
	
	//CardLayout cl = (CardLayout)(mainPane.getLayout());
	//cl.show(mainPane, "Perfil");
	
    }
    
    private void seleccionaPaneles() {
	
	if (empleadoOriginal.getRol().getNombre().equals("Administrador")) {
	    
	    personal = new JLabel("Personal");
	    personal.setIcon(new ImageIcon(Estilos.ICONO));
	    personal.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    personal.setForeground(Color.WHITE);
	    personal.setBounds(10, 10, 190, 50);
	    personal.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e)  {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Personal")) {
			cl.show(mainPane, "Personal");
			panelActivo = "Personal";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
		
	    proveedores = new JLabel("Proveedores");
	    proveedores.setIcon(new ImageIcon(Estilos.ICONO));
	    proveedores.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    proveedores.setForeground(Color.WHITE);
	    proveedores.setBounds(10, 70, 190, 50);
	    proveedores.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e)  {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Proveedores")) {
			cl.show(mainPane, "Proveedores");
			panelActivo = "Proveedores";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
		
	    productos = new JLabel("Productos");
	    productos.setIcon(new ImageIcon(Estilos.ICONO));
	    productos.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    productos.setForeground(Color.WHITE);
	    productos.setBounds(10, 130, 190, 50);
	    productos.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Productos")) {
			cl.show(mainPane, "Productos");
			panelActivo = "Productos";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
		
	    inventario = new JLabel("Inventario");
	    inventario.setIcon(new ImageIcon(Estilos.ICONO));
	    inventario.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    inventario.setForeground(Color.WHITE);
	    inventario.setBounds(10, 190, 190, 50);
	    inventario.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Inventario")) {
			cl.show(mainPane, "Inventario");
			panelActivo = "Inventario";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
	    
	    pedidos = new JLabel("Pedidos");
	    pedidos.setIcon(new ImageIcon(Estilos.ICONO));
	    pedidos.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    pedidos.setForeground(Color.WHITE);
	    pedidos.setBounds(10, 250, 190, 50);
	    pedidos.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Pedidos")) {
			cl.show(mainPane, "Pedidos");
			panelActivo = "Pedidos";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
		
	    ventas = new JLabel("Ventas");
	    ventas.setIcon(new ImageIcon(Estilos.ICONO));
	    ventas.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    ventas.setForeground(Color.WHITE);
	    ventas.setBounds(10, 310, 190, 50);
	    ventas.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Ventas")) {
			cl.show(mainPane, "Ventas");
			panelActivo = "Ventas";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
		
	    reposiciones = new JLabel("Reposiciones");
	    reposiciones.setIcon(new ImageIcon(Estilos.ICONO));
	    reposiciones.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    reposiciones.setForeground(Color.WHITE);
	    reposiciones.setBounds(10, 370, 190, 50);
	    reposiciones.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Reposiciones")) {
			cl.show(mainPane, "Reposiciones");
			panelActivo = "Reposiciones";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
		
	    informes = new JLabel("Informes");
	    informes.setIcon(new ImageIcon(Estilos.ICONO));
	    informes.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    informes.setForeground(Color.WHITE);
	    informes.setBounds(10, 430, 190, 50);
	    informes.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    if (!panelActivo.equals("Informes")) {
			cl.show(mainPane, "Informes");
			panelActivo = "Informes";
		    } else {
			cl.show(mainPane, "Background");
			panelActivo = "-";
		    }
		}  
	    });
		
	    leftPane.add(personal);
    	    leftPane.add(proveedores);
    	    leftPane.add(productos);
    	    leftPane.add(inventario);
    	    leftPane.add(pedidos);
    	    leftPane.add(ventas);
    	    leftPane.add(reposiciones);
    	    leftPane.add(informes);
	    
	    /* TODO:
	     * inicializaPersonalPane();
	     * inicializaProveedoresPane();
	     * inicializaProductosPane();
	     * inicializaInventarioPane();
	     * inicializaPedidosPane();
	     * inicializaVentasPane();
	     * inicializaReposicionesPane();
	     * inicializaInformesPane();
	    */

    	    
	    /* TODO:
	     * mainPane.add(personalPane,"Personal");
	     * mainPane.add(proveedoresPane, "Proveedores");
	     * mainPane.add(productosPane, "Productos");
	     * mainPane.add(inventarioPane, "Inventario");
	     * mainPane.add(pedidosPane, "Pedidos");
	     * mainPane.add(ventasPane, "Ventas");
	     * mainPane.add(reposicionesPane, "Reposiciones");
	     * mainPane.add(informesPane, "Informes");
	     */
	    
	} else if (empleadoOriginal.getRol().getNombre().equals("Encargado de Almacén")) {	 
	    
	    //TODO: Doble click
	    
	    inventario = new JLabel("Inventario");
	    inventario.setIcon(new ImageIcon(Estilos.ICONO));
	    inventario.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    inventario.setForeground(Color.WHITE);
	    inventario.setBounds(10, 10, 190, 50);
	    inventario.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    cl.show(mainPane, "Inventario");
		}  
	    });
	    
	    pedidos = new JLabel("Pedidos");
	    pedidos.setIcon(new ImageIcon(Estilos.ICONO));
	    pedidos.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    pedidos.setForeground(Color.WHITE);
	    pedidos.setBounds(10, 70, 190, 50);
	    pedidos.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    cl.show(mainPane, "Pedidos");
		}  
	    });
		
	    reposiciones = new JLabel("Reposiciones");
	    reposiciones.setIcon(new ImageIcon(Estilos.ICONO));
	    reposiciones.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    reposiciones.setForeground(Color.WHITE);
	    reposiciones.setBounds(10, 130, 190, 50);
	    reposiciones.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    cl.show(mainPane, "Reposiciones");
		}  
	    });
		
    	    leftPane.add(inventario);
    	    leftPane.add(pedidos);
    	    leftPane.add(reposiciones);
	    
	    /* TODO:
	     * inicializaInventarioPane();
	     * inicializaPedidosPane();
	     * inicializaReposicionesPane();
	    */
	    
	    /* TODO:
	     * mainPane.add(inventarioPane, "Inventario");
	     * mainPane.add(pedidosPane, "Pedidos");
	     * mainPane.add(reposicionesPane, "Reposiciones");
	     */
	    
	} else if (empleadoOriginal.getRol().getNombre().equals("Analista")) {
	    
	    informes = new JLabel("Informes");
	    informes.setIcon(new ImageIcon(Estilos.ICONO));
	    informes.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	    informes.setForeground(Color.WHITE);
	    informes.setBounds(10, 10, 190, 50);
	    informes.addMouseListener(new MouseAdapter() {  
		public void mouseClicked(MouseEvent e) {  
		    CardLayout cl = (CardLayout)(mainPane.getLayout());
		    cl.show(mainPane, "Informes");
		}  
	    });
	    
	    leftPane.add(informes);
	    
	    /* TODO:
	     * inicializaInformesPane();
	    */
	    
	    /* TODO:
	     * mainPane.add(informesPane, "Informes");
	     */
	    
	}
	
    }

    private void inicializaPerfilPane() {
	
	perfilPane = new JPanel();
	perfilPane.setBackground(Estilos.PRINCIPAL_CLARO);
	perfilPane.setLayout(null);
	
	JLabel encabezadoDatos = new JLabel("DATOS PERSONALES");
	encabezadoDatos.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	encabezadoDatos.setForeground(Estilos.PRINCIPAL_OSCURO);
	encabezadoDatos.setBounds(20, 12, 195, 30);
	
	JSeparator separadorDatos = new JSeparator();
	separadorDatos.setBounds(220, 25, 445, 2);
	separadorDatos.setForeground(Estilos.PRINCIPAL_OSCURO);
	
	JLabel etiquetaNombre = new JLabel("Nombre");
	etiquetaNombre.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaNombre.setBounds(20, 48, 100, 35);
	
	JTextField campoNombre = new JTextField();
	campoNombre.setFont(Estilos.FUENTE_CAMPOS);
	campoNombre.setBounds(125, 48, 200, 35);
	campoNombre.setText(empleadoOriginal.getNombre());
	campoNombre.setBorder(BorderFactory.createCompoundBorder(
		campoNombre.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	campoNombre.setEditable(false);
	
	JLabel etiquetaApellido1 = new JLabel("1\u00BA Apellido");
	etiquetaApellido1.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaApellido1.setBounds(345, 48, 100, 35);
	
	JTextField campoApellido1 = new JTextField();
	campoApellido1.setFont(Estilos.FUENTE_CAMPOS);
	campoApellido1.setBounds(460, 48, 200, 35);
	campoApellido1.setText(empleadoOriginal.getApellido1());
	campoApellido1.setBorder(BorderFactory.createCompoundBorder(
		campoApellido1.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	campoApellido1.setEditable(false);
	
	JLabel etiquetaApellido2 = new JLabel("2\u00BA Apellido");
	etiquetaApellido2.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaApellido2.setBounds(20, 95, 100, 35);
	
	JTextField campoApellido2 = new JTextField();
	campoApellido2.setFont(Estilos.FUENTE_CAMPOS);
	campoApellido2.setBounds(125, 95, 200, 35);
	campoApellido2.setText(empleadoOriginal.getApellido2());
	campoApellido2.setBorder(BorderFactory.createCompoundBorder(
		campoApellido2.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	campoApellido2.setEditable(false);
	
	JLabel etiquetaFechaNacimiento = new JLabel("Fecha de Nacimiento");
	etiquetaFechaNacimiento.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaFechaNacimiento.setBounds(345, 95, 195, 35);
	
	SpinnerDateModel dateModel = new SpinnerDateModel();
	JSpinner campoFecha = new JSpinner();
	campoFecha.setModel(dateModel);
	campoFecha.setFont(Estilos.FUENTE_CAMPOS);
	campoFecha.setBounds(530, 95, 130, 35);
	JSpinner.DateEditor editor = new JSpinner.DateEditor(campoFecha, "dd/MM/yyyy");
	campoFecha.setEditor(editor);
	campoFecha.setValue(empleadoOriginal.getFechaNacimiento());
	campoFecha.setEnabled(false);
	
	JLabel etiquetaEmail = new JLabel("Email");
	etiquetaEmail.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaEmail.setBounds(20, 142, 100, 35);
	
	JTextField campoEmail = new JTextField();
	campoEmail.setFont(Estilos.FUENTE_CAMPOS);
	campoEmail.setBounds(125, 142, 275, 35);
	campoEmail.setText(empleadoOriginal.getEmail());
	campoEmail.setBorder(BorderFactory.createCompoundBorder(
		campoEmail.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	
	JLabel etiquetaTelefono = new JLabel("Tel\u00E9fono");
	etiquetaTelefono.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaTelefono.setBounds(435, 142, 85, 35);
	
	JTextField campoTelefono = new JTextField();
	campoTelefono.setFont(Estilos.FUENTE_CAMPOS);
	campoTelefono.setBounds(530, 142, 130, 35);
	campoTelefono.setText(empleadoOriginal.getTelefono());
	campoTelefono.setBorder(BorderFactory.createCompoundBorder(
		campoTelefono.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	campoTelefono.addKeyListener(new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
		    char caracter = e.getKeyChar();
	
		    if (caracter < '0' || caracter > '9' || campoTelefono.getText().length() >= 9) {
			e.consume();
		    }
		}
	});
	
	JLabel encabezadoDireccion = new JLabel("DIRECCIÓN");
	encabezadoDireccion.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	encabezadoDireccion.setForeground(Estilos.PRINCIPAL_OSCURO);
	encabezadoDireccion.setBounds(20, 200, 120, 30);
	
	JSeparator separadorDireccion = new JSeparator();
	separadorDireccion.setBounds(145, 215, 520, 2);
	separadorDireccion.setForeground(Estilos.PRINCIPAL_OSCURO);
	
	JLabel etiquetaProvincia = new JLabel("Provincia");
	etiquetaProvincia.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaProvincia.setBounds(20, 241, 100, 35);
	
	JLabel etiquetaCiudad = new JLabel("Ciudad");
	etiquetaCiudad.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaCiudad.setBounds(345, 241, 85, 35);
	
	JTextField campoProvincia = new JTextField();
	campoProvincia.setFont(Estilos.FUENTE_CAMPOS);
	campoProvincia.setBounds(125, 241, 200, 35);
	campoProvincia.setText(empleadoOriginal.getDireccionPostal().getProvincia());
	campoProvincia.setBorder(BorderFactory.createCompoundBorder(
		campoProvincia.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	
	JTextField campoCiudad = new JTextField();
	campoCiudad.setFont(Estilos.FUENTE_CAMPOS);
	campoCiudad.setBounds(460, 241, 200, 35);
	campoCiudad.setText(empleadoOriginal.getDireccionPostal().getCiudad());
	campoCiudad.setBorder(BorderFactory.createCompoundBorder(
		campoCiudad.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	
	JLabel etiquetaDireccion = new JLabel("Direccion");
	etiquetaDireccion.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaDireccion.setBounds(20, 294, 100, 35);
	
	JTextField campoDireccion = new JTextField();
	campoDireccion.setFont(Estilos.FUENTE_CAMPOS);
	campoDireccion.setBounds(125, 294, 535, 35);
	campoDireccion.setText(empleadoOriginal.getDireccionPostal().getDireccion());
	campoDireccion.setBorder(BorderFactory.createCompoundBorder(
		campoDireccion.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	
	JLabel encabezadoCuenta = new JLabel("CUENTA");
	encabezadoCuenta.setForeground(Estilos.PRINCIPAL_OSCURO);
	encabezadoCuenta.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	encabezadoCuenta.setBounds(20, 352, 85, 30);
	
	JSeparator separadorCuenta = new JSeparator();
	separadorCuenta.setForeground(Estilos.PRINCIPAL_OSCURO);
	separadorCuenta.setBounds(125, 367, 540, 2);
	
	JLabel etiquetaContrasenyaAntigua = new JLabel("Antigua Contraseña");
	etiquetaContrasenyaAntigua.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaContrasenyaAntigua.setBounds(20, 393, 165, 35);
	
	JLabel etiquetaContrasenyaNueva = new JLabel("Nueva Contraseña");
	etiquetaContrasenyaNueva.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaContrasenyaNueva.setBounds(20, 439, 165, 35);
	
	JPasswordField campoContrasenyaAntigua = new JPasswordField();
	campoContrasenyaAntigua.setFont(Estilos.FUENTE_CAMPOS);
	campoContrasenyaAntigua.setBounds(220, 393, 200, 35);
	campoContrasenyaAntigua.setBorder(BorderFactory.createCompoundBorder(
		campoContrasenyaAntigua.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	
	JPasswordField campoContrasenyaNueva = new JPasswordField();
	campoContrasenyaNueva.setFont(Estilos.FUENTE_CAMPOS);
	campoContrasenyaNueva.setBounds(220, 439, 200, 35);
	campoContrasenyaNueva.setBorder(BorderFactory.createCompoundBorder(
		campoContrasenyaNueva.getBorder(), 
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	
	JCheckBox mostrarContrasenyaAntigua = new JCheckBox("Mostrar contrase\u00F1a");
	mostrarContrasenyaAntigua.setFont(Estilos.FUENTE_CAMPOS);
	mostrarContrasenyaAntigua.setBackground(Estilos.PRINCIPAL_CLARO);
	mostrarContrasenyaAntigua.setBounds(440, 393, 195, 35);
	mostrarContrasenyaAntigua.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    if (mostrarContrasenyaAntigua.isSelected()) {
			campoContrasenyaAntigua.setEchoChar((char)0);
		    } else {
			campoContrasenyaAntigua.setEchoChar('*');
		    }
		}
	});
	
	JCheckBox mostrarContrasenyaNueva = new JCheckBox("Mostrar contraseña");
	mostrarContrasenyaNueva.setFont(Estilos.FUENTE_CAMPOS);
	mostrarContrasenyaNueva.setBackground(Estilos.PRINCIPAL_CLARO);
	mostrarContrasenyaNueva.setBounds(440, 438, 195, 35);
	mostrarContrasenyaNueva.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    if (mostrarContrasenyaNueva.isSelected()) {
			campoContrasenyaNueva.setEchoChar((char)0);
		    } else {
			campoContrasenyaNueva.setEchoChar('*');
		    }
		}
	});
	
	JButton guardar = new JButton("Guardar");
	guardar.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	guardar.setBounds(205, 500, 120, 30);
	guardar.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		try {
		    if (compruebaCamposPerfil()) {
			empleadoEditado.setEmail(campoEmail.getText());
			empleadoEditado.setTelefono(campoTelefono.getText());
			empleadoEditado.setDireccionPostal(new DireccionPostal(campoProvincia.getText(), campoCiudad.getText(), campoDireccion.getText()));
		    
			if (campoContrasenyaNueva.getPassword().length > 0) {
			    if (campoContrasenyaAntigua.getPassword().length > 0) {
				String rawContrasenyaNueva = "";
				for (char c : campoContrasenyaNueva.getPassword()){
				    rawContrasenyaNueva += c;
				}
				String contrasenyaNueva = Utiles.codePassword(rawContrasenyaNueva);
				
				String rawContrasenyaAntigua = "";
				for (char c : campoContrasenyaAntigua.getPassword()){
				    rawContrasenyaAntigua += c;
				}
				String contrasenyaAntigua = Utiles.codePassword(rawContrasenyaAntigua);
				
				if (empleadoOriginal.getContrasenya().equals(contrasenyaAntigua)) {
				    empleadoEditado.setContrasenya(contrasenyaNueva);
				    Gson gson = new Gson();
				    flujo_cliente.println("C007-" + gson.toJson(empleadoEditado));
				    String mensaje = flujo_servidor.readLine();
				    System.out.println(mensaje);
				    if (mensaje.startsWith("S014")) {
					JOptionPane.showMessageDialog(null,"Cambios realizados con éxito");
					empleadoOriginal = empleadoEditado;
				    } else {
					JOptionPane.showMessageDialog(null,"Error al realizar los cambios");
					empleadoEditado = empleadoOriginal;
				    }
				} else {
				    if (contadorErrores > 0) {
					contadorErrores--;
					JOptionPane.showMessageDialog(null,"Contraseña incorrecta. Intentos restantes: " + contadorErrores);
				    } else {
					try {
					    Gson gson = new Gson();
					    flujo_cliente.println("C003-" + gson.toJson(empleadoOriginal));
					    System.out.println(flujo_servidor.readLine());
					    flujo_cliente.println("EXIT");
					    //TODO: login.dispose();
					    dispose();
					} catch (IOException e1) {
					    System.err.println("Couldn't get I/O for the connection to: MercaSystem_Server.");
					    System.exit(1);
					}
				    }
				}
			    } else {
				JOptionPane.showMessageDialog(null,"Introduzca la contraseña original");
			    }
			} else {
			    Gson gson = new Gson();
			    flujo_cliente.println("C007-" + gson.toJson(empleadoEditado));
			    String mensaje = flujo_servidor.readLine();
			    System.out.println(mensaje);
			    if (mensaje.startsWith("S014")) {
				JOptionPane.showMessageDialog(null,"Cambios realizados con éxito");
				empleadoOriginal = empleadoEditado;
			    } else {
				JOptionPane.showMessageDialog(null,"Error al realizar los cambios");
				empleadoEditado = empleadoOriginal;
			    }
			}
		    } else {
			JOptionPane.showMessageDialog(null,"Todos los campos deben estar rellenos (Excepto el de Contraseña)");
		    }
		    
		} catch (IOException | NoSuchAlgorithmException exception) {
		    System.err.println("Couldn't get I/O for the connection to: MercaSystem_Server.");
	            System.exit(1);
		}
		
	    }

	    private boolean compruebaCamposPerfil() {
		if (campoEmail.getText().length() == 0 | campoTelefono.getText().length() == 0 | campoProvincia.getText().length() == 0 | campoCiudad.getText().length() == 0 | campoDireccion.getText().length() == 0) {
		    return false;
		} else if(!compruebaEmail(campoEmail.getText()) || campoTelefono.getText().length() < 9) {
		    return false;
		} else {
		    return true;
		}
	    }
	    
	    private boolean compruebaEmail(String email) {
		Pattern patron = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
		Matcher matcher = patron.matcher(email);
		
		return matcher.find();
	    }
	});
	
	JButton deshacer = new JButton("Deshacer");
	deshacer.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	deshacer.setBounds(345, 500, 120, 30);
	deshacer.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		campoEmail.setText(empleadoOriginal.getEmail());
		campoTelefono.setText(empleadoOriginal.getTelefono());
		campoProvincia.setText(empleadoOriginal.getDireccionPostal().getProvincia());
		campoCiudad.setText(empleadoOriginal.getDireccionPostal().getCiudad());
		campoDireccion.setText(empleadoOriginal.getDireccionPostal().getDireccion());
		campoContrasenyaAntigua.setText("");
		mostrarContrasenyaAntigua.setSelected(false);
		campoContrasenyaAntigua.setEchoChar('*');
		campoContrasenyaNueva.setText("");
		mostrarContrasenyaNueva.setSelected(false);
		campoContrasenyaNueva.setEchoChar('*');
	    }
	});
	
	
	perfilPane.add(encabezadoDatos);
	perfilPane.add(separadorDatos);
	perfilPane.add(etiquetaNombre);
	perfilPane.add(campoNombre);
	perfilPane.add(etiquetaApellido1);
	perfilPane.add(campoApellido1);
	perfilPane.add(etiquetaApellido2);
	perfilPane.add(campoApellido2);
	perfilPane.add(etiquetaFechaNacimiento);
	perfilPane.add(campoFecha);
	perfilPane.add(etiquetaTelefono);
	perfilPane.add(campoTelefono);
	perfilPane.add(etiquetaEmail);
	perfilPane.add(campoEmail);
	perfilPane.add(encabezadoDireccion);
	perfilPane.add(separadorDireccion);
	perfilPane.add(etiquetaProvincia);
	perfilPane.add(etiquetaCiudad);
	perfilPane.add(campoProvincia);
	perfilPane.add(campoCiudad);
	perfilPane.add(etiquetaDireccion);
	perfilPane.add(campoDireccion);
	perfilPane.add(encabezadoCuenta);
	perfilPane.add(separadorCuenta);
	perfilPane.add(etiquetaContrasenyaAntigua);
	perfilPane.add(etiquetaContrasenyaNueva);
	perfilPane.add(campoContrasenyaAntigua);
	perfilPane.add(campoContrasenyaNueva);
	perfilPane.add(mostrarContrasenyaAntigua);
	perfilPane.add(mostrarContrasenyaNueva);
	perfilPane.add(guardar);
	perfilPane.add(deshacer);
	
    }  
    
    private void inicializaPersonalPane() {
	personalPane = new JPanel();
	personalPane.setBackground(Estilos.PRINCIPAL_CLARO);
	personalPane.setLayout(null);
	
	JLabel encabezadoPanel = new JLabel("EMPLEADOS");
	encabezadoPanel.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	encabezadoPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	encabezadoPanel.setBounds(15, 15, 160, 35);
	
	JSeparator separadorPanel = new JSeparator();
	separadorPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	separadorPanel.setBounds(15, 60, 650, 2);
	
	JButton alta = new JButton("NUEVO +");
	alta.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	alta.setBounds(525, 13, 125, 35);
	alta.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			
		}
	});
	
	JLabel etiquetaNombre = new JLabel("Nombre / Apellidos");
	etiquetaNombre.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaNombre.setBounds(15, 80, 160, 35);
	
	JTextField campoNombre = new JTextField();
	campoNombre.setFont(Estilos.FUENTE_CAMPOS);
	campoNombre.setBounds(185, 80, 200, 35);
	
	JLabel etiquetaRol = new JLabel("Rol");
	etiquetaRol.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaRol.setBounds(15, 130, 150, 35);
	
	Gson gson = new Gson();
	List<Rol> listaRoles = new ArrayList<>();
	try {
	    flujo_cliente.println("C009");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type RolListType = new TypeToken<ArrayList<Rol>>(){}.getType();  
	    listaRoles = gson.fromJson(mensaje, RolListType);
	    
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	JComboBox<Rol> campoRol = new JComboBox<>();
	campoRol.setFont(Estilos.FUENTE_CAMPOS);
	campoRol.setBounds(185, 130, 200, 35);
	campoRol.addItem(null);
	for (Rol rol : listaRoles) {
	    campoRol.addItem(rol);
	}
	
	JLabel etiquetaEstado = new JLabel("Estado");
	etiquetaEstado.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaEstado.setBounds(15, 180, 150, 35);
	
	JComboBox<EstadoEmpleado> campoEstado = new JComboBox<EstadoEmpleado>();
	campoEstado.setFont(Estilos.FUENTE_CAMPOS);
	campoEstado.setBounds(185, 180, 200, 35);
	campoEstado.addItem(null);
	campoEstado.addItem(EstadoEmpleado.Activo);
	campoEstado.addItem(EstadoEmpleado.Despedido);
	campoEstado.addItem(EstadoEmpleado.Inactivo);
	campoEstado.addItem(EstadoEmpleado.TemporalmenteFuera);
	
	JTable tablaEmpleados = new JTable();
	tablaEmpleados.setModel(new EmpleadoTableModel(new ArrayList<>()));
	tablaEmpleados.setBounds(0, 0, 498, 179);
	tablaEmpleados.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent mouseEvent) {
	        JTable table = (JTable) mouseEvent.getSource();
	        Point point = mouseEvent.getPoint();
	        int row = table.rowAtPoint(point);
	        if (mouseEvent.getClickCount() == 2 && row != -1) {
	            EmpleadoTableModel model = (EmpleadoTableModel) table.getModel();
	            if (model.getEmpleadoAt(row) != null) {
	        	System.out.println(model.getEmpleadoAt(row));
	            }
	        }
	    }
	});
	
	JButton buscar = new JButton("Buscar");
	buscar.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	buscar.setBounds(500, 180, 150, 35);
	buscar.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    try {
			String filtros = "";
			if (campoNombre.getText().length() > 0) {
			    filtros += campoNombre.getText() + ";";
			} else {
			    filtros += "%;";
			}
			
			if (campoRol.getSelectedItem() != null) {
			    Rol rol = (Rol) campoRol.getSelectedItem();
			    filtros += rol.getRolId() + ";";
			} else {
			    filtros += "%;";
			}
			
			if (campoEstado.getSelectedItem() != null) {
			    filtros += campoEstado.getSelectedItem().toString() + ";";
			} else {
			    filtros += "%;";
			}
			
			flujo_cliente.println("C008-" + filtros);
			String mensaje = flujo_servidor.readLine().substring(5);
			System.out.println(mensaje);
			java.lang.reflect.Type EmpleadoListType = new TypeToken<ArrayList<Empleado>>(){}.getType();  
			List<Empleado> empleados = gson.fromJson(mensaje, EmpleadoListType);
			EmpleadoTableModel modelo = new EmpleadoTableModel(empleados);
    			tablaEmpleados.setModel(modelo);
		    } catch (IOException e1) {
			System.err.println(e1.getMessage());
		    }
		}
	});
	      
	ScrollPane scroll = new ScrollPane();
	scroll.setBounds(15, 230, 644, 315);
	scroll.add(tablaEmpleados);
	
	personalPane.add(encabezadoPanel);
	personalPane.add(separadorPanel);
	personalPane.add(alta);
	personalPane.add(etiquetaNombre);
	personalPane.add(campoNombre);
	personalPane.add(etiquetaRol);
	personalPane.add(campoRol);
	personalPane.add(etiquetaEstado);
	personalPane.add(campoEstado);
	personalPane.add(buscar);
	personalPane.add(scroll);
	
    }
    
    private void inicializaProveedoresPane() {
	
	proveedoresPane = new JPanel();
	proveedoresPane.setLayout(null);
	proveedoresPane.setBackground(Estilos.PRINCIPAL_CLARO);
	
	JLabel encabezadoPanel = new JLabel("PROVEEDORES");
	encabezadoPanel.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	encabezadoPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	encabezadoPanel.setBounds(15, 15, 200, 35);
	
	JSeparator separadorPanel = new JSeparator();
	separadorPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	separadorPanel.setBounds(15, 60, 650, 2);
	
	JButton alta = new JButton("NUEVO +");
	alta.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	alta.setBounds(525, 13, 125, 35);
	alta.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			
		}
	});
	
	
	JLabel etiquetaNombre = new JLabel("Nombre");
	etiquetaNombre.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaNombre.setBounds(15, 80, 160, 35);
	
	JTextField campoNombre = new JTextField();
	campoNombre.setFont(Estilos.FUENTE_CAMPOS);
	campoNombre.setBounds(185, 80, 200, 35);

	JLabel etiquetaCategoria = new JLabel("Categoria Producto");
	etiquetaCategoria.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaCategoria.setBounds(15, 130, 160, 35);
	
	Gson gson = new Gson();
	List<CategoriaProducto> listaCategorias = new ArrayList<>();
	listaCategorias.add(null);
	try {
	    flujo_cliente.println("C012");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type categoriaListType = new TypeToken<ArrayList<CategoriaProducto>>(){}.getType();  
	    listaCategorias.addAll(gson.fromJson(mensaje, categoriaListType));
	    
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	FilterComboBox<CategoriaProducto> campoCategoria = new FilterComboBox<>(listaCategorias);
	campoCategoria.setFont(Estilos.FUENTE_CAMPOS);
	campoCategoria.setBounds(185, 130, 200, 35);
	
	
	JLabel etiquetaProducto = new JLabel("Producto");
	etiquetaProducto.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaProducto.setBounds(15, 180, 160, 35);
	
	List<Producto> listaProductos = new ArrayList<>();
	listaProductos.add(null);
	try {
	    flujo_cliente.println("C013-%;%;%");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type productoListType = new TypeToken<ArrayList<Producto>>(){}.getType();  
	    listaProductos.addAll(gson.fromJson(mensaje, productoListType));
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	FilterComboBox<Producto> campoProducto = new FilterComboBox<>(listaProductos);
	campoProducto.setFont(Estilos.FUENTE_CAMPOS);
	campoProducto.setBounds(185, 180, 200, 35);
	
	JTable tablaProveedores = new JTable();
	tablaProveedores.setModel(new ProveedorTableModel(new ArrayList<>()));
	tablaProveedores.setBounds(0, 0, 498, 179);
	tablaProveedores.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent mouseEvent) {
	        JTable table = (JTable) mouseEvent.getSource();
	        Point point = mouseEvent.getPoint();
	        int row = table.rowAtPoint(point);
	        if (mouseEvent.getClickCount() == 2 && row != -1) {
	            ProveedorTableModel model = (ProveedorTableModel) table.getModel();
	            if (model.getProveedorAt(row) != null) {
	        	System.out.println(model.getProveedorAt(row));
	            }
	        }
	    }
	});
	
	JButton buscar = new JButton("Buscar");
	buscar.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	buscar.setBounds(500, 180, 150, 35);
	buscar.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    try {
			String filtros = "";
			if (campoNombre.getText().length() > 0) {
			    filtros += campoNombre.getText() + ";";
			} else {
			    filtros += "%;";
			}
			
			if (campoCategoria.getSelectedItem() != null) {
			    CategoriaProducto categoria = (CategoriaProducto) campoCategoria.getSelectedItem();
			    filtros += categoria.getIdCategoria() + ";";
			} else {
			    filtros += "%;";
			}
			
			if (campoProducto.getSelectedItem() != null) {
			    Producto producto = (Producto) campoProducto.getSelectedItem();
			    filtros += producto.getIdProducto() + ";";
			} else {
			    filtros += "%;";
			}
			
			flujo_cliente.println("C016-" + filtros);
			String mensaje = flujo_servidor.readLine().substring(5);
			System.out.println(mensaje);
			java.lang.reflect.Type ProveedorListType = new TypeToken<ArrayList<Proveedor>>(){}.getType();  
			List<Proveedor> proveedores = gson.fromJson(mensaje, ProveedorListType);
			ProveedorTableModel modelo = new ProveedorTableModel(proveedores);
			tablaProveedores.setModel(modelo);
		    } catch (IOException e1) {
			System.err.println(e1.getMessage());
		    }
		}
	});
	
	ScrollPane scroll = new ScrollPane();
	scroll.setBounds(15, 230, 644, 315);
	scroll.add(tablaProveedores);
	
//	campoCategoria.addActionListener(new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//		    List<Producto> listaProductos = new ArrayList<>();
//		    listaProductos.add(null);
//		    if (campoCategoria.getSelectedItem() == null) {    
//			try {
//			    flujo_cliente.println("C013-%;%;%");
//			    String mensaje = flujo_servidor.readLine().substring(5);
//			    System.out.println(mensaje);
//				    
//			    java.lang.reflect.Type productoListType = new TypeToken<ArrayList<Producto>>(){}.getType();  
//			    listaProductos.addAll(gson.fromJson(mensaje, productoListType));
//			} catch (IOException e1) {
//			    System.err.println(e1.getMessage());
//			}
//		    } else {
//			try {
//			    CategoriaProducto cp = (CategoriaProducto) campoCategoria.getSelectedItem();
//			    flujo_cliente.println("C013-%;" + cp.getIdCategoria() +";%");
//			    String mensaje = flujo_servidor.readLine().substring(5);
//			    System.out.println(mensaje);
//				    
//			    java.lang.reflect.Type productoListType = new TypeToken<ArrayList<Producto>>(){}.getType();  
//			    listaProductos.addAll(gson.fromJson(mensaje, productoListType));
//			} catch (IOException e1) {
//			    System.err.println(e1.getMessage());
//			}
//		    }
//		    campoProducto.setItems(listaProductos);
//		}
//		
//	});
	
	
	proveedoresPane.add(encabezadoPanel);
	proveedoresPane.add(separadorPanel);
	proveedoresPane.add(alta);
	proveedoresPane.add(etiquetaNombre);
	proveedoresPane.add(campoNombre);
	proveedoresPane.add(etiquetaCategoria);
	proveedoresPane.add(campoCategoria);
	proveedoresPane.add(etiquetaProducto);
	proveedoresPane.add(campoProducto);
	proveedoresPane.add(buscar);
	proveedoresPane.add(scroll);
    }

    private void inicializaProductosPane() {
	
	productosPane = new JPanel();
	productosPane.setLayout(null);
	productosPane.setBackground(Estilos.PRINCIPAL_CLARO);
	
	JLabel encabezadoPanel = new JLabel("PRODUCTOS");
	encabezadoPanel.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	encabezadoPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	encabezadoPanel.setBounds(15, 15, 200, 35);
	
	JSeparator separadorPanel = new JSeparator();
	separadorPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	separadorPanel.setBounds(15, 60, 650, 2);
	
	JButton alta = new JButton("NUEVO +");
	alta.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	alta.setBounds(525, 13, 125, 35);
	alta.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			
		}
	});
	
	JLabel etiquetaNombre = new JLabel("Nombre");
	etiquetaNombre.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaNombre.setBounds(15, 80, 160, 35);
	
	JTextField campoNombre = new JTextField();
	campoNombre.setFont(Estilos.FUENTE_CAMPOS);
	campoNombre.setBounds(185, 80, 200, 35);

	JLabel etiquetaCategoria = new JLabel("Categoria Producto");
	etiquetaCategoria.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaCategoria.setBounds(15, 130, 160, 35);
	
	Gson gson = new Gson();
	List<CategoriaProducto> listaCategorias = new ArrayList<>();
	listaCategorias.add(null);
	try {
	    flujo_cliente.println("C012");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type categoriaListType = new TypeToken<ArrayList<CategoriaProducto>>(){}.getType();  
	    listaCategorias.addAll(gson.fromJson(mensaje, categoriaListType));	    
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	FilterComboBox<CategoriaProducto> campoCategoria = new FilterComboBox<>(listaCategorias);
	campoCategoria.setFont(Estilos.FUENTE_CAMPOS);
	campoCategoria.setBounds(185, 130, 200, 35);
	
	JLabel etiquetaProveedor = new JLabel("Proveedor");
	etiquetaProveedor.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaProveedor.setBounds(15, 180, 160, 35);
	
	
	List<Proveedor> listaProveedores = new ArrayList<>();
	listaProveedores.add(null);
	try {
	    flujo_cliente.println("C016-%;%;%");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type proveedorListType = new TypeToken<ArrayList<Proveedor>>(){}.getType();  
	    listaProveedores.addAll(gson.fromJson(mensaje, proveedorListType));
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	FilterComboBox<Proveedor> campoProveedor = new FilterComboBox<>(listaProveedores);
	campoProveedor.setFont(Estilos.FUENTE_CAMPOS);
	campoProveedor.setBounds(185, 180, 200, 35);

	JTable tablaProductos = new JTable();
	tablaProductos.setModel(new ProductoTableModel(new ArrayList<>()));
	tablaProductos.setBounds(0, 0, 498, 179);
	tablaProductos.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent mouseEvent) {
	        JTable table = (JTable) mouseEvent.getSource();
	        Point point = mouseEvent.getPoint();
	        int row = table.rowAtPoint(point);
	        if (mouseEvent.getClickCount() == 2 && row != -1) {
	            ProductoTableModel model = (ProductoTableModel) table.getModel();
	            if (model.getProductoAt(row) != null) {
	        	System.out.println(model.getProductoAt(row));
	            }
	        }
	    }
	});
	
	JButton buscar = new JButton("Buscar");
	buscar.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	buscar.setBounds(500, 180, 150, 35);
	buscar.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    try {
			String filtros = "";
			if (campoNombre.getText().length() > 0) {
			    filtros += campoNombre.getText() + ";";
			} else {
			    filtros += "%;";
			}
			
			if (campoCategoria.getSelectedItem() != null) {
			    CategoriaProducto categoria = (CategoriaProducto) campoCategoria.getSelectedItem();
			    filtros += categoria.getIdCategoria() + ";";
			} else {
			    filtros += "%;";
			}
			
			if (campoProveedor.getSelectedItem() != null) {
			    Proveedor proveedor = (Proveedor) campoProveedor.getSelectedItem();
			    filtros += proveedor.getIdProveedor() + ";";
			} else {
			    filtros += "%;";
			}
			
			flujo_cliente.println("C013-" + filtros);
			String mensaje = flujo_servidor.readLine().substring(5);
			System.out.println(mensaje);
			java.lang.reflect.Type productoListType = new TypeToken<ArrayList<Producto>>(){}.getType();  
			List<Producto> proveedores = gson.fromJson(mensaje, productoListType);
			ProductoTableModel modelo = new ProductoTableModel(proveedores);
			tablaProductos.setModel(modelo);
		    } catch (IOException e1) {
			System.err.println(e1.getMessage());
		    }
		}
	});
	
	ScrollPane scroll = new ScrollPane();
	scroll.setBounds(15, 230, 644, 315);
	scroll.add(tablaProductos);
	
	productosPane.add(encabezadoPanel);
	productosPane.add(separadorPanel);
	productosPane.add(alta);
	productosPane.add(etiquetaNombre);
	productosPane.add(campoNombre);
	productosPane.add(etiquetaCategoria);
	productosPane.add(campoCategoria);
	productosPane.add(etiquetaProveedor);
	productosPane.add(campoProveedor);
	productosPane.add(buscar);
	productosPane.add(scroll);
    }
   
    private void inicializaPedidosPane() {
	
	pedidosPane = new JPanel();
	pedidosPane.setLayout(null);
	pedidosPane.setBackground(Estilos.PRINCIPAL_CLARO);
	
	JLabel encabezadoPanel = new JLabel("PEDIDOS");
	encabezadoPanel.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	encabezadoPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	encabezadoPanel.setBounds(15, 15, 200, 35);
	
	JSeparator separadorPanel = new JSeparator();
	separadorPanel.setForeground(Estilos.PRINCIPAL_OSCURO);
	separadorPanel.setBounds(15, 60, 650, 2);
	
	JButton alta = new JButton("NUEVO +");
	alta.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	alta.setBounds(525, 13, 125, 35);
	alta.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			
		}
	});
	
	JLabel etiquetaProveedor = new JLabel("Proveedor");
	etiquetaProveedor.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaProveedor.setBounds(15, 80, 90, 35);
	
	Gson gson = new Gson();
	List<Proveedor> listaProveedores = new ArrayList<>();
	listaProveedores.add(null);
	try {
	    flujo_cliente.println("C016-%;%;%");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type proveedorListType = new TypeToken<ArrayList<Proveedor>>(){}.getType();  
	    listaProveedores.addAll(gson.fromJson(mensaje, proveedorListType));
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	FilterComboBox<Proveedor> campoProveedor = new FilterComboBox<>(listaProveedores);
	campoProveedor.setFont(Estilos.FUENTE_CAMPOS);
	campoProveedor.setBounds(115, 80, 200, 35);
	
	JLabel etiquetaConsumible = new JLabel("Consumible");
	etiquetaConsumible.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaConsumible.setBounds(340, 130, 100, 35);
	etiquetaConsumible.setEnabled(false);
	
	List<Consumible> listaConsumibles = new ArrayList<>();
	listaConsumibles.add(null);
	try {
	    flujo_cliente.println("C021-%;%");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type consumibleListType = new TypeToken<ArrayList<Consumible>>(){}.getType();  
	    listaConsumibles.addAll(gson.fromJson(mensaje, consumibleListType));
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	FilterComboBox<Consumible> campoConsumible = new FilterComboBox<>(listaConsumibles);
	campoConsumible.setFont(Estilos.FUENTE_CAMPOS);
	campoConsumible.setBounds(450, 130, 200, 35);
	campoConsumible.setEnabled(false);
	
	JLabel etiquetaProducto = new JLabel("Producto");
	etiquetaProducto.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaProducto.setBounds(15, 130, 90, 35);
	
	List<Producto> listaProductos = new ArrayList<>();
	listaProductos.add(null);
	try {
	    flujo_cliente.println("C013-%;%;%");
	    String mensaje = flujo_servidor.readLine().substring(5);
	    System.out.println(mensaje);
	    
	    java.lang.reflect.Type productoListType = new TypeToken<ArrayList<Producto>>(){}.getType();  
	    listaProductos.addAll(gson.fromJson(mensaje, productoListType));
	} catch (IOException e1) {
	    System.err.println(e1.getMessage());
	}
	
	FilterComboBox<Producto> campoProducto = new FilterComboBox<>(listaProductos);
	campoProducto.setFont(Estilos.FUENTE_CAMPOS);
	campoProducto.setBounds(115, 130, 200, 35);
	campoProducto.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (campoProducto.getSelectedItem() == null) {
				etiquetaConsumible.setEnabled(false);
				campoConsumible.setEnabled(false);
			} else {
				etiquetaConsumible.setEnabled(true);
				campoConsumible.setEnabled(true);
				// Filtro por el producto seleccionado
			}			
		}		
	});	
	
	JLabel etiquetaMinTotal = new JLabel("Min Total");
	etiquetaMinTotal.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaMinTotal.setBounds(15, 180, 90, 35);
	
	JSpinner campoMinTotal = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999.99, 1));
	campoMinTotal.setFont(Estilos.FUENTE_CAMPOS);
	campoMinTotal.setBounds(115, 180, 75, 35);
	
	JLabel etiquetaMaxTotal = new JLabel("Max Total");
	etiquetaMaxTotal.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaMaxTotal.setBounds(225, 180, 90, 35);
	
	JSpinner campoMaxTotal = new JSpinner(new SpinnerNumberModel(999.0, 0.0, 9999.99, 1));
	campoMaxTotal.setFont(Estilos.FUENTE_CAMPOS);
	campoMaxTotal.setBounds(325, 180, 75, 35);
	
	campoMinTotal.addKeyListener(new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
		    char caracter = e.getKeyChar();
	
		    if (caracter < '0' || caracter > '9' || caracter != '.') {
			e.consume();
		    } else {
			if (campoMinTotal.getValue().toString().contains(".")) {
			    e.consume();
			}
		    }
		}
	});
	campoMinTotal.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		if ((Double) campoMinTotal.getValue() > (Double) campoMaxTotal.getValue()) {
		    campoMaxTotal.setValue(campoMinTotal.getValue());
		}
	    }
	});
	campoMaxTotal.addKeyListener(new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
		    char caracter = e.getKeyChar();
	
		    if (caracter < '0' || caracter > '9' || caracter != '.') {
			e.consume();
		    } else {
			if (campoMaxTotal.getValue().toString().contains(".")) {
			    e.consume();
			}
		    }
		}
	});
	campoMaxTotal.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		if ((Double) campoMaxTotal.getValue() < (Double) campoMinTotal.getValue()) {
		    campoMinTotal.setValue(campoMaxTotal.getValue());
		}
	    }
	});
	
	JLabel etiquetaFechas = new JLabel("Entre");
	etiquetaFechas.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	etiquetaFechas.setBounds(15, 230, 90, 35);
	
	JSpinner campoFechaMin = new JSpinner(new SpinnerDateModel());
	JSpinner.DateEditor editor1 = new JSpinner.DateEditor(campoFechaMin, "dd/MM/yyyy");
	campoFechaMin.setEditor(editor1);
	campoFechaMin.setFont(Estilos.FUENTE_CAMPOS);
	campoFechaMin.setValue(java.util.Date.from(Instant.now().minusSeconds(604800)));
	campoFechaMin.setBounds(115, 230, 120, 35);
	
	JSpinner campoFechaMax = new JSpinner(new SpinnerDateModel());
	JSpinner.DateEditor editor2 = new JSpinner.DateEditor(campoFechaMax, "dd/MM/yyyy");
	campoFechaMax.setEditor(editor2);
	campoFechaMax.setFont(Estilos.FUENTE_CAMPOS);
	campoFechaMax.setBounds(280, 230, 120, 35);
	
	campoFechaMin.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		java.util.Date minUtil = (java.util.Date) campoFechaMin.getValue();
		java.util.Date maxUtil = (java.util.Date) campoFechaMax.getValue();
		
		Date min = new Date(minUtil.getTime());
		Date max = new Date(maxUtil.getTime());
		
		if (min.after(max)) {
		    campoFechaMax.setValue(campoFechaMin.getValue());
		}
	    }
	});
	campoFechaMax.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		java.util.Date minUtil = (java.util.Date) campoFechaMin.getValue();
		java.util.Date maxUtil = (java.util.Date) campoFechaMax.getValue();
		
		Date min = new Date(minUtil.getTime());
		Date max = new Date(maxUtil.getTime());
		
		if (max.before(min)) {
		    campoFechaMin.setValue(campoFechaMax.getValue());
		}
	    }
	});
	
	JTable tablaPedidos = new JTable();
	tablaPedidos.setModel(new PedidosTableModel(new ArrayList<>()));
	tablaPedidos.setBounds(0, 0, 498, 179);
	tablaPedidos.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent mouseEvent) {
	        JTable table = (JTable) mouseEvent.getSource();
	        Point point = mouseEvent.getPoint();
	        int row = table.rowAtPoint(point);
	        if (mouseEvent.getClickCount() == 2 && row != -1) {
	            PedidosTableModel model = (PedidosTableModel) table.getModel();
	            if (model.getPedidoAt(row) != null) {
	        	System.out.println(model.getPedidoAt(row));
	            }
	        }
	    }
	});
	
	JButton buscar = new JButton("Buscar");
	buscar.setFont(Estilos.FUENTE_ETIQUETAS_PEQUEÑAS);
	buscar.setBounds(500, 230, 150, 35);
	buscar.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    try {
			String filtros = "";
			
			if (campoProveedor.getSelectedItem() != null) {
			    Proveedor proveedor = (Proveedor) campoProveedor.getSelectedItem();
			    filtros += proveedor.getIdProveedor() + ";";
			} else {
			    filtros += "%;";
			}
			
			if (campoProducto.getSelectedItem() != null) {
			    Producto producto = (Producto) campoProducto.getSelectedItem();
			    filtros += producto.getIdProducto() + ";";
			    
			    if (campoConsumible.getSelectedItem() != null) {
				Consumible consumible = (Consumible) campoConsumible.getSelectedItem();
				filtros += consumible.getIdConsumible() + ";";
			    } else {
				filtros += "%;";
			    }
			} else {
			    filtros += "%;%;";
			}
			
			filtros += campoMinTotal.getValue() + ";" + campoMaxTotal.getValue() + ";";
			
			java.util.Date minUtil = (java.util.Date) campoFechaMin.getValue();
			java.util.Date maxUtil = (java.util.Date) campoFechaMax.getValue();
			
			Date min = new Date(minUtil.getTime());
			Date max = new Date(maxUtil.getTime());
			
			filtros += min.toString() + ";" + max.toString();
			
			flujo_cliente.println("C022-" + filtros);
			String mensaje = flujo_servidor.readLine().substring(5);
			System.out.println(mensaje);
			java.lang.reflect.Type pedidoListType = new TypeToken<ArrayList<Pedido>>(){}.getType();  
			List<Pedido> pedidos = gson.fromJson(mensaje, pedidoListType);
			PedidosTableModel modelo = new PedidosTableModel(pedidos);
			tablaPedidos.setModel(modelo);
		    } catch (IOException e1) {
			System.err.println(e1.getMessage());
		    }
		}
	});
	
	ScrollPane scroll = new ScrollPane();
	scroll.setBounds(15, 280, 644, 265);
	scroll.add(tablaPedidos);
	
	pedidosPane.add(encabezadoPanel);
	pedidosPane.add(separadorPanel);
	pedidosPane.add(alta);
	pedidosPane.add(etiquetaProveedor);
	pedidosPane.add(campoProveedor);
	pedidosPane.add(etiquetaProducto);
	pedidosPane.add(campoProducto);
	pedidosPane.add(etiquetaConsumible);
	pedidosPane.add(campoConsumible);
	pedidosPane.add(etiquetaMinTotal);
	pedidosPane.add(campoMinTotal);
	pedidosPane.add(etiquetaMaxTotal);
	pedidosPane.add(campoMaxTotal);
	pedidosPane.add(etiquetaFechas);
	pedidosPane.add(campoFechaMin);
	pedidosPane.add(campoFechaMax);
	pedidosPane.add(buscar);
	pedidosPane.add(scroll);
    }
    
    
}
