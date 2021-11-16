package vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import modelos.Empleado;
import utiles.Estilos;
import utiles.Utiles;

@SuppressWarnings("serial")
public class PantallaLogin extends JFrame {

    private static Socket socket = null;
    private static PrintWriter flujo_cliente = null;
    private static BufferedReader flujo_servidor = null;
    
    private JPanel contentPane;
	
    private JLabel logo;
	
    private JLabel etiquetaUsuario;
    private JLabel etiquetaContrasenya;
	
    private JLabel error;
	
    private JTextField campoUsuario;
    private JPasswordField campoContrasenya;
	
    private JButton login;

    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    PantallaLogin frame = new PantallaLogin();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }
	
    public PantallaLogin() {
	setBackground(new Color(153, 255, 153));
	setForeground(new Color(153, 255, 153));
	setIconImage(Estilos.LOGO);
	setResizable(false);
	setTitle("MercaSystem");
	setForeground(Estilos.PRINCIPAL_OSCURO);
	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e){
		flujo_cliente.println("EXIT");
		dispose();
            }
	});
	setBounds(550, 250, 450, 350);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	contentPane.setBackground(Estilos.PRINCIPAL_CLARO);
	setContentPane(contentPane);
	inicializaComponentes();
    }

    private void inicializaComponentes() {
	
	try {
	    socket = new Socket("localhost", 4444);
	    flujo_cliente = new PrintWriter(socket.getOutputStream(), true);
	    flujo_servidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	} catch (UnknownHostException e) {
	    System.err.println("Don't know about host: MercaSystem_Server.");
	    System.exit(1);
	} catch (IOException e) {
	    System.err.println("Couldn't get I/O for the connection to: MercaSystem_Server.");
	    System.exit(1);
	}
	
	logo = new JLabel(new ImageIcon(Estilos.LOGO));
	
	etiquetaUsuario = new JLabel("Usuario:");
	etiquetaUsuario.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	etiquetaContrasenya = new JLabel("Contraseña:");
	etiquetaContrasenya.setFont(Estilos.FUENTE_ETIQUETAS_GRANDES);
	error = new JLabel();
	error.setFont(Estilos.FUENTE_CAMPOS);
	error.setForeground(Color.red);
	error.setVisible(false);
	
	campoUsuario = new JTextField();
	campoUsuario.setToolTipText("");
	campoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
	campoUsuario.setFont(Estilos.FUENTE_CAMPOS);
	campoContrasenya = new JPasswordField();
	campoContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
	campoContrasenya.setFont(Estilos.FUENTE_CAMPOS);
	
	login = new JButton("LOGIN");
	login.setFont(Estilos.FUENTE_CAMPOS);
	login.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e){  
		if (campoUsuario.getText().length() > 0 && campoContrasenya.getPassword().length > 0) {
		    compruebaLogin();
		}
	    }  
	});
		
	logo.setBounds(25, 25, 400, 50);
	etiquetaUsuario.setBounds(35, 90, 160, 40);
	etiquetaContrasenya.setBounds(35, 150, 160, 40);
	campoUsuario.setBounds(200, 90, 180, 40);
	campoContrasenya.setBounds(200, 150, 180, 40);
	error.setBounds(80, 200, 280, 35);
	login.setBounds(170, 250, 100, 35);
	
		
	contentPane.setLayout(null);
	
	contentPane.add(logo);
	contentPane.add(etiquetaUsuario);
	contentPane.add(etiquetaContrasenya);
	contentPane.add(campoUsuario);
	contentPane.add(campoContrasenya);
	contentPane.add(error);
	contentPane.add(login);
    }

    protected void compruebaLogin() {
	try {	    
            Gson gson = new Gson();
            
            String usuario = campoUsuario.getText();
	    String rawContrasenya = "";
	    for (char c : campoContrasenya.getPassword()){
		rawContrasenya += c;
	    }
	    String contrasenya = Utiles.codePassword(rawContrasenya);
		
	    flujo_cliente.println("C000-" + usuario + "-" + contrasenya);
	    String mensaje = flujo_servidor.readLine();
	    System.out.println(mensaje);
			
	    if (mensaje.startsWith("S000-")) {
		Empleado empleado = gson.fromJson(mensaje.substring(5), Empleado.class);
		PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(socket, flujo_cliente, flujo_servidor, this, empleado);
		pantallaPrincipal.setVisible(true);
		this.setVisible(false);
	    } else if (mensaje.startsWith("S001")) {
		error.setText("* Usuario ya logueado *");
		error.setVisible(true);
	    } else {
		error.setText("* Usuario o contraseña erróneos *");
		error.setVisible(true);
	    }
			
	    campoUsuario.setText("");
	    campoContrasenya.setText("");
	} catch (NoSuchAlgorithmException e) {
	    System.err.println("Couldn't code password");
            System.exit(1);
	} catch (IOException e) {
	    System.err.println("Couldn't get I/O for the connection to: MercaSystem_Server.");
            System.exit(1);
	}
    }

}
