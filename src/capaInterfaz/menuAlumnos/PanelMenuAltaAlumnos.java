
package capaInterfaz.menuAlumnos;


import capaInterfaz.JButtonOp;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Clase que gestiona un nuevo panel de alta de alumnos.
 *
 * @author Confiencial
 */
public class PanelMenuAltaAlumnos extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_ALUMNOS = 3;
    private static final int MENU_ALTA_ALUMNOS_INDIVIDUAL = 311;
    private static final int MENU_ALTA_ALUMNOS_DESDE_FICHERO = 312;

    /** Crea e inicializa un nuevo PanelMenuAltaAlumnos
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuAltaAlumnos(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor


    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Alumnos>Alta");
        ruta.setBounds(20,20,200,70);

        ImageIcon img_alta_individual_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/student_single_add_icon.jpg"));
        JButtonOp botonAltaAlumnosIndividual = new JButtonOp("Alta individual",
                                                             img_alta_individual_alumnos,
                                                             MENU_ALTA_ALUMNOS_INDIVIDUAL);
        botonAltaAlumnosIndividual.setBounds(300, 150, 200, 80);
        botonAltaAlumnosIndividual.addActionListener(this);
        botonAltaAlumnosIndividual.setBorder(null);


        ImageIcon img_alta_desde_fichero_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/student_txt_add_icon.jpg"));
        JButtonOp botonAltaAlumnosDesdeFichero = new JButtonOp("Alta desde fichero",
                                                               img_alta_desde_fichero_alumnos,
                                                               MENU_ALTA_ALUMNOS_DESDE_FICHERO);
        botonAltaAlumnosDesdeFichero.setBounds(300, 300, 200, 80);
        botonAltaAlumnosDesdeFichero.addActionListener(this);
        botonAltaAlumnosDesdeFichero.setBorder(null);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Atrás",MENU_ALUMNOS);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(botonAltaAlumnosIndividual);
        this.add(botonAltaAlumnosDesdeFichero);
        this.add(botonHome);
        this.add(botonBack);

    } // fin del método cargarElementos


    /** Método que captura los eventos del panel alta de alumnos y ejecuta
     *  el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {
        JButtonOp b = (JButtonOp) e.getSource();
        int ancho = FrameMenuPrincipal.ancho;
        int alto = FrameMenuPrincipal.alto;
        switch(b.getNumOperacion()){
            case MENU_PRINCIPAL:
                PanelMenuPrincipal pSubmenuPrincipal = new PanelMenuPrincipal(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuPrincipal);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_ALUMNOS:
                PanelMenuAlumnos pSubmenuAlumnos = new PanelMenuAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_ALTA_ALUMNOS_INDIVIDUAL:
                PanelMenuAltaAlumnosIndividual pSubmenuAltaAlumnosIndividual = new PanelMenuAltaAlumnosIndividual(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAltaAlumnosIndividual);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_ALTA_ALUMNOS_DESDE_FICHERO:
                PanelMenuAltaAlumnosDesdeFichero pSubmenuAltaAlumnosDesdeFichero = new PanelMenuAltaAlumnosDesdeFichero(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAltaAlumnosDesdeFichero);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuAltaAlumnos