
package capaInterfaz.MenuHistoricoAlumnos;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.listados.ListadoEvaluacion;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
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

/** Clase que gestiona el panel del menú de consulta del histórico de alumnos.
 *
 * @author Confiencial
 */
public class PanelMenuConsultarHistoricoAlumnos extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_HISTORICO_ALUMNOS = 6;
    private static final int LISTADO_CONSULTAR_HISTORICO_ALUMNOS = 611;

    private JTextField campo_dni;
    private JTextField campo_curso;
    private JTextField campo_convocatoria;

    /** Crea e inicializa el panel correspondiente al menú Consultar Histórico de Alumnos
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuConsultarHistoricoAlumnos(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor



    private void cargarElementos() {
        JLabel ruta = new JLabel("MENU PRINCIPAL>>Histórico de Alumnos>Consultar");
        ruta.setBounds(20,20,600,70);

        JLabel introduzca_dni = new JLabel("D.N.I.");
        introduzca_dni.setBounds(150,150,200,70);
        Font auxFont = introduzca_dni.getFont();
        introduzca_dni.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_curso = new JLabel("Curso (año inicio)");
        introduzca_curso.setBounds(150,200,200,70);
        auxFont = introduzca_curso.getFont();
        introduzca_curso.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_convocatoria = new JLabel("Convocatoria");
        introduzca_convocatoria.setBounds(150,250,200,70);
        auxFont = introduzca_convocatoria.getFont();
        introduzca_convocatoria.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        campo_dni = new JTextField();
        campo_dni.setBounds(300,175, 150, 20);

        campo_curso = new JTextField();
        campo_curso.setBounds(300,225, 150, 20);

        campo_convocatoria = new JTextField();
        campo_convocatoria.setBounds(300,275, 150, 20);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonSearch = new JButtonOp("Buscar",LISTADO_CONSULTAR_HISTORICO_ALUMNOS);
        botonSearch.setBounds(475, 225, 100, 30);
        botonSearch.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Atrás",MENU_HISTORICO_ALUMNOS);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(introduzca_dni);
        this.add(introduzca_curso);
        this.add(introduzca_convocatoria);
        this.add(campo_dni);
        this.add(campo_curso);
        this.add(campo_convocatoria);
        this.add(botonHome);
        this.add(botonSearch);
        this.add(botonBack);
    } // fin del método cargarElementos

    /** Método que captura los eventos del panel de consulta de histórico de alumnos
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
            case LISTADO_CONSULTAR_HISTORICO_ALUMNOS:

                String dni = campo_dni.getText();
                String curso = campo_curso.getText();
                String convocatoria = campo_convocatoria.getText();
                int curso_consulta;
                if (this.noEstaVacio(curso)) {
                    curso_consulta = Integer.parseInt(curso);
                } else {
                    curso_consulta = -1;
                }
                try {
                    Evaluacion evaluacion = new Evaluacion();
                    List<ListadoEvaluacion> resultado = evaluacion.consultarEvaluacion(
                                                               dni, null, null,
                                                               convocatoria, curso_consulta);

                    PanelResultadoConsultarHistoricoAlumnos pResultadoConsultarHistoricoAlumnos = new PanelResultadoConsultarHistoricoAlumnos(ancho,alto);
                    JScrollPane pResultadoConsultarAlumnosConScroll = new JScrollPane(pResultadoConsultarHistoricoAlumnos);
                    FrameMenuPrincipal.getFramePrincipal().setContentPane(pResultadoConsultarAlumnosConScroll);
                    pResultadoConsultarHistoricoAlumnos.mostrarResultados(resultado);
                    FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                } catch (NumberFormatException e1) {
                   JDialogOperacionFail jDialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               "Curso no válido",true);
                   jDialogFail.setVisible(true);
                } catch (RuntimeException e2) {
                   JDialogOperacionFail jDialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               e2.getMessage(),true);
                   jDialogFail.setVisible(true);
                }
                break;
            case MENU_HISTORICO_ALUMNOS:
                PanelMenuHistoricoAlumnos pSubmenuHistoricoAlumnos = new PanelMenuHistoricoAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuHistoricoAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed



    /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si no está vacía.
     *
     * @return TRUE si la cadena no está vacía.
     *         FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    } // fin del método noEstaVacio

} // fin de la clase PanelMenuConsultarHistoricoAlumnos