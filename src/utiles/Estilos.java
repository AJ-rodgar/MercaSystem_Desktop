package utiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class Estilos {

    //Imagenes
    public static final Image LOGO = Toolkit.getDefaultToolkit().getImage(Estilos.class.getResource("/imagenes/logo.png"));
    public static final Image USUARIO = Toolkit.getDefaultToolkit().getImage(Estilos.class.getResource("/imagenes/user2.png"));
    public static final Image LOGOUT = Toolkit.getDefaultToolkit().getImage(Estilos.class.getResource("/imagenes/logout2.png"));
    
    public static final Image ICONO = Toolkit.getDefaultToolkit().getImage(Estilos.class.getResource("/imagenes/cestita.png"));
    public static final Image ICONO_2 = Toolkit.getDefaultToolkit().getImage(Estilos.class.getResource("/imagenes/icono.png"));
    
    //Fuentes
    public static final Font FUENTE_ETIQUETAS_GRANDES = new Font("Times New Roman", Font.BOLD, 24);
    public static final Font FUENTE_ETIQUETAS_PEQUEÑAS = new Font("Times New Roman", Font.BOLD, 18);
    public static final Font FUENTE_CAMPOS = new Font("Times New Roman", Font.PLAIN, 18);

    //Colores
    public static final Color PRINCIPAL = new Color(83, 138, 79);
    public static final Color PRINCIPAL_CLARO = new Color(202, 230, 200);
    public static final Color PRINCIPAL_OSCURO = new Color(52, 87, 49);
    
}
