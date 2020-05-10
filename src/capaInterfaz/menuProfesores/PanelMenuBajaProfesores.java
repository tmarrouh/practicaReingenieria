
package capaInterfaz.menuProfesores;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.listados.ListadoProfesor;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.GrupoClase;
import capaLogicaNegocio.Profesor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/** Clase que gestiona el panel de baja de profesores.
 *
 * @author Confiencial
 */
public class PanelMenuBajaProfesores extends JPanel implements ActionListener{
    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_PROFESORES = 4;
    private static final int  LISTADO_BAJA_PROFESORES = 421;

    private JTextField campo_nombre;
    private JTextField campo_apellidos;
    private JTextField campo_grupo_clase;


    /** Crea e inicializa un nuevo PanelMenuBajaProfesores
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuBajaProfesores(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {
        JLabel ruta = new JLabel("MENU PRINCIPAL>Profesores>Baja");
        ruta.setBounds(20,20,400,70);

        JLabel introduzca_nombre = new JLabel("Nombre");
        introduzca_nombre.setBounds(150,150,200,70);
        Font auxFont = introduzca_nombre.getFont();
        introduzca_nombre.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_apellidos = new JLabel("Apellidos");
        introduzca_apellidos.setBounds(150,200,200,70);
        auxFont = introduzca_apellidos.getFont();
        introduzca_apellidos.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_grupo_clase = new JLabel("Grupo de clase");
        introduzca_grupo_clase.setBounds(150,250,200,70);
        auxFont = introduzca_grupo_clase.getFont();
        introduzca_grupo_clase.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        campo_nombre = new JTextField();
        campo_nombre.setBounds(300,175, 150, 20);

        campo_apellidos = new JTextField();
        campo_apellidos.setBounds(300,225, 150, 20);

        campo_grupo_clase = new JTextField();
        campo_grupo_clase.setBounds(300,275, 150, 20);

        JButtonOp botonSearch = new JButtonOp("Buscar",LISTADO_BAJA_PROFESORES);
        botonSearch.setBounds(500, 220, 80, 30);
        botonSearch.addActionListener(this);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Atrás",MENU_PROFESORES);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(introduzca_nombre);
        this.add(introduzca_apellidos);
        this.add(introduzca_grupo_clase);
        this.add(campo_nombre);
        this.add(campo_apellidos);
        this.add(campo_grupo_clase);
        this.add(botonSearch);
        this.add(botonHome);
        this.add(botonBack);
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel baja profesores
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
            case MENU_PROFESORES:
                PanelMenuProfesores pSubmenuProfesores = new PanelMenuProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case LISTADO_BAJA_PROFESORES:
                String nombre = campo_nombre.getText();
                String apellidos = campo_apellidos.getText();
                String grupo_clase = campo_grupo_clase.getText();

                Profesor profesor = new Profesor (nombre,apellidos);
                GrupoClase grupo = new GrupoClase(grupo_clase);

                try {

                    List<ListadoProfesor> resultado = profesor.consultarProfesor(grupo);

                    PanelResultadoBajaProfesores pResultadoBajaProfesores = new PanelResultadoBajaProfesores(ancho,alto);
                    JScrollPane pResultadoBajaAlumnosConScroll = new JScrollPane(pResultadoBajaProfesores);
                    pResultadoBajaAlumnosConScroll.setHorizontalScrollBar(null);
                    FrameMenuPrincipal.getFramePrincipal().setContentPane(pResultadoBajaAlumnosConScroll);
                    pResultadoBajaProfesores.mostrarResultados(resultado);
                    FrameMenuPrincipal.getFramePrincipal().setVisible(true);

                } catch (RuntimeException e1) {
                   JDialogOperacionFail jDialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               e1.getMessage(),true);
                   jDialogFail.setVisible(true);
                }
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuBajaProfesores