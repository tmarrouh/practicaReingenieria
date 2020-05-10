
package capaInterfaz.MenuHistoricoAlumnos;

import capaInterfaz.JButtonOp;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Clase que gestiona el panel del menú de histórico de alumnos.
 *
 * @author Confiencial
 */
public class PanelMenuHistoricoAlumnos extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_CONSULTAR_HISTORICO_ALUMNOS = 61;


    /** Crea e inicializa el panel correspondiente al menú Histórico de Alumnos
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuHistoricoAlumnos(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor


    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Histórico de alumnos");
        ruta.setBounds(20,20,400,70);

        ImageIcon img_consultar_historico_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/history_students_search_icon.jpg"));
        JButtonOp botonConsultarHistoricoAlumnos = new JButtonOp("Consultar Histórico",
                                                                 img_consultar_historico_alumnos,
                                                                 MENU_CONSULTAR_HISTORICO_ALUMNOS);
        botonConsultarHistoricoAlumnos.setBounds(300, 200, 200, 80);
        botonConsultarHistoricoAlumnos.addActionListener(this);
        botonConsultarHistoricoAlumnos.setBorder(null);
        

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
        this.add(botonConsultarHistoricoAlumnos);
        this.add(botonHome);
        this.add(botonBack);

    } // fin del método cargarElementos


    /** Método que captura los eventos del panel histórico de alumnos y ejecuta
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
            case MENU_CONSULTAR_HISTORICO_ALUMNOS:
                PanelMenuConsultarHistoricoAlumnos pSubmenuConsultarHistoricoAlumnos = new PanelMenuConsultarHistoricoAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarHistoricoAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuHistoricoAlumnos