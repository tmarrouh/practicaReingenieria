
package capaInterfaz.menuGruposPracticas;

import capaInterfaz.JButtonOp;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Clase que gestiona el panel del menú de grupos de prácticas.
 *
 * @author Confiencial
 */
public class PanelMenuGruposPracticas extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_ALTA_GRUPO_PRACTICA = 51;
    private static final int MENU_BAJA_GRUPO_PRACTICA = 52;
    private static final int MENU_MODIFICAR_GRUPO_PRACTICA = 53;
    private static final int MENU_CONSULTAR_GRUPO_PRACTICA = 54;


    /** Crea e inicializa un nuevo PanelMenuGruposPracticas
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuGruposPracticas(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {
        JLabel ruta = new JLabel("MENU PRINCIPAL>Grupos de prácticas");
        ruta.setBounds(20,20,300,70);

        ImageIcon img_alta_grupos_practicas = new ImageIcon(getClass().getResource("/capaInterfaz/images/group_students_add_icon.jpg"));
        JButtonOp botonAltaGrupoPractica = new JButtonOp("Alta Grupo de práctica",
                                                         img_alta_grupos_practicas,
                                                         MENU_ALTA_GRUPO_PRACTICA);
        botonAltaGrupoPractica.setBounds(300, 100, 200, 80);
        botonAltaGrupoPractica.addActionListener(this);
        botonAltaGrupoPractica.setBorder(null);

        ImageIcon img_baja_grupos_practicas = new ImageIcon(getClass().getResource("/capaInterfaz/images/group_students_delete_icon.jpg"));
        JButtonOp botonBajaGrupoPractica = new JButtonOp("Baja Grupo de práctica",
                                                         img_baja_grupos_practicas,
                                                         MENU_BAJA_GRUPO_PRACTICA);
        botonBajaGrupoPractica.setBounds(300, 200, 200, 80);
        botonBajaGrupoPractica.addActionListener(this);
        botonBajaGrupoPractica.setBorder(null);


        ImageIcon img_modificar_grupos_practicas = new ImageIcon(getClass().getResource("/capaInterfaz/images/group_students_custom_icon.jpg"));
        JButtonOp botonModificarGrupoPractica = new JButtonOp("Modificar Grupo de práctica",
                                                              img_modificar_grupos_practicas,
                                                              MENU_MODIFICAR_GRUPO_PRACTICA);
        botonModificarGrupoPractica.setBounds(300, 300, 200, 80);
        botonModificarGrupoPractica.addActionListener(this);
        botonModificarGrupoPractica.setBorder(null);


        ImageIcon img_consultar_grupos_practicas = new ImageIcon(getClass().getResource("/capaInterfaz/images/group_students_search_icon.jpg"));
        JButtonOp botonConsultarGrupoPractica = new JButtonOp("Consultar Grupo de práctica",
                                                              img_consultar_grupos_practicas,
                                                              MENU_CONSULTAR_GRUPO_PRACTICA);
        botonConsultarGrupoPractica.setBounds(300, 400, 200, 80);
        botonConsultarGrupoPractica.addActionListener(this);
        botonConsultarGrupoPractica.setBorder(null);
    
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
        this.add(botonAltaGrupoPractica);
        this.add(botonBajaGrupoPractica);
        this.add(botonModificarGrupoPractica);
        this.add(botonConsultarGrupoPractica);
        this.add(botonHome);
        this.add(botonBack);
    
    } // fin del método cargarElementos



    /** Método que captura los eventos del panel menu grupos de prácticas y ejecuta
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
            case MENU_ALTA_GRUPO_PRACTICA:
                PanelMenuAltaGruposPracticas pSubmenuAltaGruposPracticas = new PanelMenuAltaGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAltaGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_BAJA_GRUPO_PRACTICA:
                PanelMenuBajaGruposPracticas pSubmenuBajaGruposPracticas = new PanelMenuBajaGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuBajaGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_MODIFICAR_GRUPO_PRACTICA:
                PanelMenuModificarGruposPracticas pSubmenuModificarGruposPracticas = new PanelMenuModificarGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuModificarGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_CONSULTAR_GRUPO_PRACTICA:
                PanelMenuConsultarGruposPracticas pSubmenuConsultarGruposPracticas = new PanelMenuConsultarGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuGruposPracticas