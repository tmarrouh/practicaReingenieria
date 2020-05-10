
package capaInterfaz.menuPrincipal;

import capaInterfaz.*;
import capaLogicaNegocio.*;
import javax.swing.JFrame;

/** Clase que gestiona el Frame principal de la aplicación.
 *
 * @author Confiencial
 */

public class FrameMenuPrincipal extends JFrame {

    /** alto nº de pixels de altura del panel.  */
    public static int alto;
    /** ancho nº de pixels de anchura del panel. */
    public static int ancho;

    private static FrameMenuPrincipal framePrincipal = null;
    private PanelMenuPrincipal pMenuPrincipal;


    /** Método que devuelve el frame principal. Si no está inicializado,
     *  lo inicializa.
     *
     * @return frame principal de la aplicación.
     */
    public static FrameMenuPrincipal getFramePrincipal(){
        if (framePrincipal==null){
            alto = 600;
            ancho = 800;
            framePrincipal = new FrameMenuPrincipal(ancho,alto);
        }
        return framePrincipal;
    } // fin del método getFramePrincipal


    /** Crea e inicializa un nuevo FrameMenuPrincipal
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    private FrameMenuPrincipal(int ancho, int alto){

        this.setBounds(0,0,ancho,alto);
        this.ancho = ancho;
        this.alto = alto;
        JBarraMenus menuBar = new JBarraMenus();
        this.setJMenuBar(menuBar.menuBar);
    } // fin del constructor


    /** Método con el que arranca la aplicación.
     *  Inicializa todos los parámetros del sistema y crea el frame principal.
     */
    public void iniciar(){
        try {
            Curso curso = new Curso ();
            curso.recuperarCursoActual();
            Convocatoria convocatoria = new Convocatoria ();
            convocatoria.recuperarConvocatoriaActual();
            Profesor profesor = new Profesor();
            profesor.recuperarUltimoCodProfesor();
            Matricula matricula = new Matricula ();
            matricula.recuperarUltimoCodMatricula();
            Evaluacion evaluacion = new Evaluacion ();
            evaluacion.recuperarUltimoCodEvaluacion();
            Tutoria tutoria = new Tutoria();
            tutoria.recuperarUltimoCodTutoria();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            JDialogOperacionFail fail = new JDialogOperacionFail(
                                FrameMenuPrincipal.getFramePrincipal(),
                                e.getMessage(),
                                true);
            fail.setVisible(true);
        }

        pMenuPrincipal = new PanelMenuPrincipal(ancho,alto);
        this.setContentPane(pMenuPrincipal);
        this.setVisible(true);
        this.setResizable(false);
    } // fin del método iniciar

} // fin de la clase FrameMenuPrincipal