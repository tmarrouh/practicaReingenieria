
package capaInterfaz.menuCalificaciones;

import capaInterfaz.JButtonOp;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/** Clase que gestiona el panel del menú de calificaciones.
 *
 * @author Confiencial
 */
public class PanelMenuCalificaciones extends JPanel implements ActionListener {

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_MODIFICAR_CALIFICACIONES = 21;
    private static final int MENU_CONSULTAR_CALIFICACIONES = 22;
    
    /** Crea e inicializa un nuevo PanelMenuCalificaciones
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuCalificaciones(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor


    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Calificaciones");
        ruta.setBounds(20,20,200,70);

        ImageIcon img_modificar_calificaciones = new ImageIcon(getClass().getResource("/capaInterfaz/images/marks_custom_icon.jpg"));
        JButtonOp botonModificarCalificaciones = new JButtonOp("Modificar Calificaciones",
                                                               img_modificar_calificaciones,
                                                               MENU_MODIFICAR_CALIFICACIONES);
        botonModificarCalificaciones.setBounds(300, 150, 200, 80);
        botonModificarCalificaciones.addActionListener(this);
        botonModificarCalificaciones.setBorder(null);

        ImageIcon img_consultar_calificaciones = new ImageIcon(getClass().getResource("/capaInterfaz/images/marks_search_icon.jpg"));
        JButtonOp botonConsultarCalificaciones = new JButtonOp("Consultar Calificaciones",
                                                               img_consultar_calificaciones,
                                                               MENU_CONSULTAR_CALIFICACIONES);
        botonConsultarCalificaciones.setBounds(300, 300, 200, 80);
        botonConsultarCalificaciones.addActionListener(this);
        botonConsultarCalificaciones.setBorder(null);


        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);
       // botonHome.setBorder(null);


        JButtonOp botonBack = new JButtonOp("Atrás",MENU_PRINCIPAL);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(botonModificarCalificaciones);
        this.add(botonConsultarCalificaciones);
        this.add(botonHome);
        this.add(botonBack);

    } // fin del método cargarElementos

    /** Método que captura los eventos del panel menu calificaciones y ejecuta
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
            case MENU_MODIFICAR_CALIFICACIONES:
                PanelMenuModificarCalificaciones pSubmenuModificarCalificaciones = new PanelMenuModificarCalificaciones(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuModificarCalificaciones);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_CONSULTAR_CALIFICACIONES:
                PanelMenuConsultarCalificaciones pSubmenuConsultarCalificaciones = new PanelMenuConsultarCalificaciones(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarCalificaciones);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuCalificaciones