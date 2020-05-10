

package capaInterfaz.menuCalificaciones;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.listados.ListadoEvaluacion;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.Evaluacion;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/** Clase que gestiona el panel del menú para consultar calificaciones.
 *
 * @author Confiencial
 */
public class PanelMenuConsultarCalificaciones extends JPanel implements ActionListener{
    
    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_CALIFICACIONES = 2;
    private static final int LISTADO_CONSULTAR_CALIFICACIONES = 221;

    private JTextField introduzca_dni;
    private JTextField introduzca_n_mat;
    private JTextField introduzca_grupo;
    private JTextField introduzca_convocatoria;


    /** Crea e inicializa un nuevo PanelMenuConsultarCalificaciones
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuConsultarCalificaciones(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor


    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Calificaciones>Consultar");
        ruta.setBounds(20,20,400,70);

        JLabel filtro_dni = new JLabel("D.N.I.");
        filtro_dni.setBounds(150,150,200,70);
        Font auxFont = filtro_dni.getFont();
        filtro_dni.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_n_mat = new JLabel("Nº matrícula");
        filtro_n_mat.setBounds(150,200,200,70);
        auxFont = filtro_n_mat.getFont();
        filtro_n_mat.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_grupo = new JLabel("Grupo de clase");
        filtro_grupo.setBounds(150,250,200,70);
        auxFont = filtro_grupo.getFont();
        filtro_grupo.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_convocatoria = new JLabel("Convocatoria");
        filtro_convocatoria.setBounds(150,300,200,70);
        auxFont = filtro_convocatoria.getFont();
        filtro_convocatoria.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        introduzca_dni = new JTextField();
        introduzca_dni.setBounds(300,175, 150, 20);

        introduzca_n_mat = new JTextField();
        introduzca_n_mat.setBounds(300,225, 150, 20);

        introduzca_grupo = new JTextField();
        introduzca_grupo.setBounds(300,275, 150, 20);

        introduzca_convocatoria = new JTextField();
        introduzca_convocatoria.setBounds(300,325, 150, 20);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Atrás",MENU_CALIFICACIONES);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        JButtonOp botonSearch = new JButtonOp("Buscar",LISTADO_CONSULTAR_CALIFICACIONES);
        botonSearch.setBounds(500, 250, 80, 30);
        botonSearch.addActionListener(this);


        this.add(ruta);
        this.add(filtro_dni);
        this.add(filtro_n_mat);
        this.add(filtro_grupo);
        this.add(filtro_convocatoria);
        this.add(introduzca_dni);
        this.add(introduzca_n_mat);
        this.add(introduzca_grupo);
        this.add(introduzca_convocatoria);
        this.add(botonHome);
        this.add(botonBack);
        this.add(botonSearch);

    } // fin del método cargarElementos

    /** Método que captura los eventos del panel menu consultar calificaciones
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
            case MENU_CALIFICACIONES:
                PanelMenuCalificaciones pSubmenuCalificaciones = new PanelMenuCalificaciones(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuCalificaciones);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case LISTADO_CONSULTAR_CALIFICACIONES:

                String dni = introduzca_dni.getText();
                String n_mat = introduzca_n_mat.getText();
                String grupo = introduzca_grupo.getText();
                String convocatoria = introduzca_convocatoria.getText();
                try {
                    Evaluacion evaluacion = new Evaluacion();
                    Curso curso = new Curso();
                    int curso_actual = curso.getCursoActual();
                    List<ListadoEvaluacion> resultado = evaluacion.consultarEvaluacion(
                                                               dni, n_mat, grupo,
                                                               convocatoria, curso_actual);

                    PanelResultadoConsultarCalificaciones pResultadoConsultarCalificaciones = new PanelResultadoConsultarCalificaciones(ancho,alto);
                    JScrollPane pResultadoConsultarAlumnosConScroll = new JScrollPane(pResultadoConsultarCalificaciones);
                    FrameMenuPrincipal.getFramePrincipal().setContentPane(pResultadoConsultarAlumnosConScroll);
                    pResultadoConsultarCalificaciones.mostrarResultados(resultado);
                    FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                } catch (RuntimeException e1) {
                   JDialogOperacionFail jDialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               e1.getMessage(),true);
                   jDialogFail.setVisible(true);
                }
                break;
        }
    } // fin del método actionPerformed


    /** Método que comprueba si una cadena está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena está vacía.
     *         FALSE en caso contrario.
     */
    public boolean estaVacio(String cadena) {
	return ((cadena == null) || ("".equals(cadena)));
    }

    /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena no está vacía.
     *         FALSE en caso contrario.
     */
    public boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    } // fin del método noEstaVacio

} // fin de la clase PanelMenuConsultarCalificaciones