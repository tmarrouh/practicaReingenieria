
package capaInterfaz.menuProfesores;


import capaInterfaz.JButtonOp;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Clase que gestiona el panel del menú de profesores.
 *
 * @author Confiencial
 */
public class PanelMenuProfesores extends JPanel implements ActionListener {

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_ALTA_PROFESORES = 41;
    private static final int MENU_BAJA_PROFESORES = 42;
    private static final int MENU_MODIFICAR_PROFESORES = 43;
    private static final int MENU_CONSULTAR_PROFESORES = 44;


    /** Crea e inicializa un nuevo PanelMenuProfesores
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuProfesores(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {
        JLabel ruta = new JLabel("MENU PRINCIPAL>Profesores");
        ruta.setBounds(20,20,300,70);
        ImageIcon img_alta_profesores = new ImageIcon(getClass().getResource("/capaInterfaz/images/teachers_add_icon.jpg"));
        JButtonOp botonAltaProfesores = new JButtonOp("Alta de profesores",
                                                      img_alta_profesores,
                                                      MENU_ALTA_PROFESORES);
        botonAltaProfesores.setBounds(300, 100, 200, 80);
        botonAltaProfesores.addActionListener(this);
        botonAltaProfesores.setBorder(null);

        ImageIcon img_baja_profesores = new ImageIcon(getClass().getResource("/capaInterfaz/images/teachers_delete_icon.jpg"));
        JButtonOp botonBajaProfesores = new JButtonOp("Baja de profesores",
                                                      img_baja_profesores,
                                                      MENU_BAJA_PROFESORES);
        botonBajaProfesores.setBounds(300, 200, 200, 80);
        botonBajaProfesores.addActionListener(this);
        botonBajaProfesores.setBorder(null);

        ImageIcon img_modificar_profesores = new ImageIcon(getClass().getResource("/capaInterfaz/images/teachers_custom_icon.jpg"));
        JButtonOp botonModificarProfesores = new JButtonOp("Modificar profesores",
                                                           img_modificar_profesores,
                                                           MENU_MODIFICAR_PROFESORES);
        botonModificarProfesores.setBounds(300, 300, 200, 80);
        botonModificarProfesores.addActionListener(this);
        botonModificarProfesores.setBorder(null);
        
        ImageIcon img_consultar_profesores = new ImageIcon(getClass().getResource("/capaInterfaz/images/teachers_search_icon.jpg"));
        JButtonOp botonConsultarProfesores = new JButtonOp("Consultar profesores",
                                                           img_consultar_profesores,
                                                           MENU_CONSULTAR_PROFESORES);
        botonConsultarProfesores.setBounds(300, 400, 200, 80);
        botonConsultarProfesores.addActionListener(this);
        botonConsultarProfesores.setBorder(null);

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
        this.add(botonAltaProfesores);
        this.add(botonBajaProfesores);
        this.add(botonModificarProfesores);
        this.add(botonConsultarProfesores);
        this.add(botonHome);
        this.add(botonBack);

    } // fin del método cargarElementos



    /** Método que captura los eventos del panel menu profesores
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
            case MENU_ALTA_PROFESORES:
                PanelMenuAltaProfesores pSubmenuAltaProfesores = new PanelMenuAltaProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAltaProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_BAJA_PROFESORES:
                PanelMenuBajaProfesores pSubmenuBajaProfesores = new PanelMenuBajaProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuBajaProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_MODIFICAR_PROFESORES:
                PanelMenuModificarProfesores pSubmenuModificarProfesores = new PanelMenuModificarProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuModificarProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_CONSULTAR_PROFESORES:
                PanelMenuConsultarProfesores pSubmenuConsultarProfesores = new PanelMenuConsultarProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuProfesores