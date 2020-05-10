
package capaInterfaz.menuPrincipal;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogAboutBox;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.MenuHistoricoAlumnos.PanelMenuHistoricoAlumnos;
import capaInterfaz.menuAlumnos.PanelMenuAlumnos;
import capaInterfaz.menuCalificaciones.PanelMenuCalificaciones;
import capaInterfaz.menuConfiguracion.JDialogMenuConfiguracion;
import capaInterfaz.menuGruposPracticas.PanelMenuGruposPracticas;
import capaInterfaz.menuProfesores.PanelMenuProfesores;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Clase que gestiona el panel del menú principal.
 *
 * @author Confiencial
 */
public class PanelMenuPrincipal  extends JPanel implements ActionListener{

    private static final int MENU_CALIFICACIONES = 2;
    private static final int MENU_ALUMNOS = 3;
    private static final int MENU_PROFESORES = 4;
    private static final int MENU_GRUPOS_PRACTICAS = 5;
    private static final int MENU_HISTORICO = 6;
    private static final int MENU_CONFIGURACION = 7;
    private static final int MENU_ABOUT_BOX = 8;
    private static final int MENU_HELP = 9;

    /** Crea e inicializa un nuevo PanelMenuPrincipal
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuPrincipal(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos(){


        JLabel ruta = new JLabel("MENU PRINCIPAL");
        ruta.setBounds(20,20,200,70);

        JLabel titulo = new JLabel("EUI Notes");
        titulo.setBounds(110,420,500,70);
        Font auxFont = titulo.getFont();
        titulo.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 40));

        ImageIcon img_calificaciones = new ImageIcon(getClass().getResource("/capaInterfaz/images/marks_icon.jpg"));
        JButtonOp botonMenuCalificaciones = new JButtonOp("Calificaciones",
                                                          img_calificaciones,
                                                          MENU_CALIFICACIONES);
        botonMenuCalificaciones.setBounds(475, 30, 200, 80);
        botonMenuCalificaciones.addActionListener(this);
        botonMenuCalificaciones.setBorder(null);


        ImageIcon img_grupo_practicas = new ImageIcon(getClass().getResource("/capaInterfaz/images/group_students_icon.jpg"));
        JButtonOp botonMenuGrupoPracticas = new JButtonOp("Grupos de práctica",
                                                          img_grupo_practicas,
                                                          MENU_GRUPOS_PRACTICAS);
        botonMenuGrupoPracticas.setBounds(475,130,200,80);
        botonMenuGrupoPracticas.addActionListener(this);
        botonMenuGrupoPracticas.setBorder(null);


        ImageIcon img_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/student_icon.jpg"));
        JButtonOp botonMenuAlumnos = new JButtonOp("Alumnos",
                                                   img_alumnos,
                                                   MENU_ALUMNOS);
        botonMenuAlumnos.setBounds(475,230,200,80);
        botonMenuAlumnos.addActionListener(this);
        botonMenuAlumnos.setBorder(null);

        ImageIcon img_profesores = new ImageIcon(getClass().getResource("/capaInterfaz/images/teachers_icon.jpg"));
        JButtonOp botonMenuProfesores = new JButtonOp("Profesores",
                                                      img_profesores,
                                                      MENU_PROFESORES);
        botonMenuProfesores.setBounds(475,330,200,80);
        botonMenuProfesores.addActionListener(this);
        botonMenuProfesores.setBorder(null);


        ImageIcon img_historico_alumnos = new ImageIcon(getClass().getResource("/capaInterfaz/images/history_students_icon.jpg"));
        JButtonOp botonMenuHistoricoAlumnos = new JButtonOp("Histórico de alumnos",
                                                            img_historico_alumnos,
                                                            MENU_HISTORICO);
        botonMenuHistoricoAlumnos.setBounds(475,430,200,80);
        botonMenuHistoricoAlumnos.addActionListener(this);
        botonMenuHistoricoAlumnos.setBorder(null);


        ImageIcon img = new ImageIcon(getClass().getResource("/capaInterfaz/images/UPM1.jpg"));
        JButtonOp botonAboutBox = new JButtonOp("",img,MENU_ABOUT_BOX);
        botonAboutBox.setBounds(20, 100, 367, 300);
        botonAboutBox.setBorder(null);
        botonAboutBox.addActionListener(this);

        ImageIcon img_options = new ImageIcon(getClass().getResource("/capaInterfaz/images/options4a.jpg"));
        JButtonOp botonMenuConfig = new JButtonOp("",img_options,MENU_CONFIGURACION);
        botonMenuConfig.setBounds(700, 10, 60, 60);
        botonMenuConfig.setBorder(null);
        botonMenuConfig.addActionListener(this);

        ImageIcon img_help = new ImageIcon(getClass().getResource("/capaInterfaz/images/help_icon.jpg"));
        JButtonOp botonMenuHelp = new JButtonOp("",img_help,MENU_HELP);
        botonMenuHelp.setBounds(700, 470, 60, 60);
        botonMenuHelp.setBorder(null);
        botonMenuHelp.addActionListener(this);

        this.add(ruta);
        this.add(titulo);
        this.add(botonMenuCalificaciones);
        this.add(botonMenuGrupoPracticas);
        this.add(botonMenuAlumnos);
        this.add(botonMenuProfesores);
        this.add(botonMenuHistoricoAlumnos);
        this.add(botonMenuConfig);
        this.add(botonMenuHelp);
        this.add(botonAboutBox);
        
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel del menu principal
     *  y ejecuta el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {
        JButtonOp b = (JButtonOp) e.getSource();
        int ancho = FrameMenuPrincipal.ancho;
        int alto = FrameMenuPrincipal.alto;
        switch(b.getNumOperacion()){
            case MENU_CALIFICACIONES:
                PanelMenuCalificaciones pSubmenuCalificaciones = new PanelMenuCalificaciones(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuCalificaciones);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_ALUMNOS:
                PanelMenuAlumnos pSubmenuAlumnos = new PanelMenuAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_PROFESORES:
                PanelMenuProfesores pSubmenuProfesores = new PanelMenuProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_GRUPOS_PRACTICAS:
                PanelMenuGruposPracticas pSubmenuGruposPracticas = new PanelMenuGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_HISTORICO:
                PanelMenuHistoricoAlumnos pSubmenuHistoricoAlumnos = new PanelMenuHistoricoAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuHistoricoAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_CONFIGURACION:
                JDialogMenuConfiguracion jSubMenuConfiguracion = new JDialogMenuConfiguracion(
                                                                    FrameMenuPrincipal.getFramePrincipal(),
                                                                    true);
                jSubMenuConfiguracion.setVisible(true);
                break;
            case MENU_ABOUT_BOX:
                JDialogAboutBox jDialogAboutBox = new JDialogAboutBox(
                                                      FrameMenuPrincipal.getFramePrincipal(),
                                                      true);
                jDialogAboutBox.setVisible(true);
                break;
            case MENU_HELP:
                String tutorial = "tutorial/Tutorial.pdf";
                try {
                    File file = new File(tutorial);
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    JDialogOperacionFail dialogOperacionFail = new JDialogOperacionFail(
                                                                FrameMenuPrincipal.getFramePrincipal(),
                                                                "Error al abrir el archivo",true);
                    dialogOperacionFail.setVisible(true);
                }
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuPrincipal