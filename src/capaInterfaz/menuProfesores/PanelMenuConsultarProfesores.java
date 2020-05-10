
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

/** Clase que gestiona el panel de consultar profesores.
 *
 * @author Confiencial
 */
public class PanelMenuConsultarProfesores extends JPanel implements ActionListener {

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_PROFESORES = 4;
    private static final int LISTADO_CONSULTAR_PROFESORES = 441;

    private JTextField introduzca_nombre;
    private JTextField introduzca_apellidos;
    private JTextField introduzca_grupo_clase;


    /** Crea e inicializa un nuevo PanelMenuConsultarProfesores
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuConsultarProfesores(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor


    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Profesores>Consultar");
        ruta.setBounds(20,20,400,70);

        JLabel filtro_nombre = new JLabel("Nombre");
        filtro_nombre.setBounds(150,150,200,70);
        Font auxFont = filtro_nombre.getFont();
        filtro_nombre.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_apellidos = new JLabel("Apellidos");
        filtro_apellidos.setBounds(150,200,200,70);
        auxFont = filtro_apellidos.getFont();
        filtro_apellidos.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_grupo = new JLabel("Grupo de clase");
        filtro_grupo.setBounds(150,250,200,70);
        auxFont = filtro_grupo.getFont();
        filtro_grupo.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        introduzca_nombre = new JTextField();
        introduzca_nombre.setBounds(300,175, 150, 20);

        introduzca_apellidos = new JTextField();
        introduzca_apellidos.setBounds(300,225, 150, 20);

        introduzca_grupo_clase = new JTextField();
        introduzca_grupo_clase.setBounds(300,275, 150, 20);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Atrás",MENU_PROFESORES);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        JButtonOp botonSearch = new JButtonOp("Buscar",LISTADO_CONSULTAR_PROFESORES);
        botonSearch.setBounds(500, 220, 80, 30);
        botonSearch.addActionListener(this);


        this.add(ruta);
        this.add(filtro_nombre);
        this.add(filtro_apellidos);
        this.add(filtro_grupo);
        this.add(introduzca_nombre);
        this.add(introduzca_apellidos);
        this.add(introduzca_grupo_clase);
        this.add(botonHome);
        this.add(botonBack);
        this.add(botonSearch);

    } // fin del método cargarElementos

    /** Método que captura los eventos del panel consultar profesores
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
            case LISTADO_CONSULTAR_PROFESORES:
                String nombre = this.introduzca_nombre.getText();
                String apellidos = this.introduzca_apellidos.getText();
                String grupo = this.introduzca_grupo_clase.getText();
                
                Profesor profesor = new Profesor (nombre, apellidos);
                GrupoClase grupo_clase = new GrupoClase(grupo);
                
                try {
                    List<ListadoProfesor> resultado = profesor.consultarProfesor(grupo_clase);
                    PanelResultadoConsultarProfesores pResultadoConsultarProfesores = new PanelResultadoConsultarProfesores(ancho,alto);
                    JScrollPane pResultadoConsultarAlumnosConScroll = new JScrollPane(pResultadoConsultarProfesores);
                    pResultadoConsultarAlumnosConScroll.setHorizontalScrollBar(null);
                    FrameMenuPrincipal.getFramePrincipal().setContentPane(pResultadoConsultarAlumnosConScroll);
                    pResultadoConsultarProfesores.mostrarResultados(resultado);
                    FrameMenuPrincipal.getFramePrincipal().setVisible(true);                    
                } catch (RuntimeException e1) {
                   JDialogOperacionFail jDialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               e1.getMessage(),true);
                   jDialogFail.setVisible(true);
                }
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuConsultarProfesores