
package capaInterfaz.menuAlumnos;


import capaInterfaz.JButtonOp;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.Convocatoria;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Clase que gestiona el panel del menú de alumnos.
 *
 * @author Confiencial
 */
public class PanelMenuAlumnos extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_ALTA_ALUMNOS = 31;
    private static final int MENU_BAJA_ALUMNOS = 32;
    private static final int MENU_MODIFICAR_ALUMNOS = 33;
    private static final int MENU_CONSULTAR_ALUMNOS = 34;


    /** Crea e inicializa un nuevo PanelMenuAlumnos.
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuAlumnos(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {
        JLabel ruta = new JLabel("MENU PRINCIPAL>Alumnos");
        ruta.setBounds(20,20,300,70);

        ImageIcon img_alta_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/student_add_icon.jpg"));
        JButtonOp botonAltaAlumnos = new JButtonOp("Alta de alumnos",
                                                   img_alta_alumnos,
                                                   MENU_ALTA_ALUMNOS);
        botonAltaAlumnos.setBounds(300, 100, 200, 80);
        botonAltaAlumnos.addActionListener(this);
        botonAltaAlumnos.setBorder(null);
        Convocatoria convocatoria = new Convocatoria();
        convocatoria.recuperarConvocatoriaActual();
        if (convocatoria.getConvocatoriaActual().equals("extraordinaria")) {
            botonAltaAlumnos.setEnabled(false);
        }

        ImageIcon img_baja_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/student_delete_icon.jpg"));
        JButtonOp botonBajaAlumnos = new JButtonOp("Baja de alumnos",
                                                   img_baja_alumnos,
                                                   MENU_BAJA_ALUMNOS);
        botonBajaAlumnos.setBounds(300, 200, 200, 80);
        botonBajaAlumnos.addActionListener(this);
        botonBajaAlumnos.setBorder(null);

        ImageIcon img_modificar_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/student_custom_icon.jpg"));
        JButtonOp botonModificarAlumno = new JButtonOp("Modificar alumnos",
                                                       img_modificar_alumnos,
                                                       MENU_MODIFICAR_ALUMNOS);
        botonModificarAlumno.setBounds(300, 300, 200, 80);
        botonModificarAlumno.addActionListener(this);
        botonModificarAlumno.setBorder(null);


        ImageIcon img_consultar_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/student_search_icon.jpg"));
        JButtonOp botonConsultarAlumnos = new JButtonOp("Consultar alumnos",
                                                        img_consultar_alumnos,
                                                        MENU_CONSULTAR_ALUMNOS);
        botonConsultarAlumnos.setBounds(300, 400, 200, 80);
        botonConsultarAlumnos.addActionListener(this);
        botonConsultarAlumnos.setBorder(null);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Atrás",MENU_PRINCIPAL);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(botonAltaAlumnos);
        this.add(botonBajaAlumnos);
        this.add(botonModificarAlumno);
        this.add(botonConsultarAlumnos);
        this.add(botonHome);
        this.add(botonBack);

    } // fin del método cargarElementos


    /** Método que captura los eventos del panel alta de alumnos
     *  y ejecuta el código correspondiente a cada evento.
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
            case MENU_ALTA_ALUMNOS:
                PanelMenuAltaAlumnos pSubmenuAltaAlumnos = new PanelMenuAltaAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAltaAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_BAJA_ALUMNOS:
                PanelMenuBajaAlumnos pSubmenuBajaAlumnos = new PanelMenuBajaAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuBajaAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_MODIFICAR_ALUMNOS:
                PanelMenuModificarAlumnos pSubmenuModificarAlumnos = new PanelMenuModificarAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuModificarAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_CONSULTAR_ALUMNOS:
                PanelMenuConsultarAlumnos pSubmenuConsultarAlumnos = new PanelMenuConsultarAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuAlumnos