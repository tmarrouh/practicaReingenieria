
package capaInterfaz.ficheros;

import capaInterfaz.MenuHistoricoAlumnos.PanelResultadoConsultarHistoricoAlumnos;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Clase que gestiona la generación de un fichero txt a partir de una consulta
 *  del histórico de alumnos.
 *
 * @author Confiencial
 */
public class ActionListenerGuardarTxtHistoricoAlumnos implements ActionListener{

    private PanelResultadoConsultarHistoricoAlumnos panelResultadoConsultarHistoricoAlumnos;

    /** Carácter separador de campos en el fichero. Inicialmente será 'a'.
     */
    public static char separador = 'a';


    /** Crea el ActionListener e inicializa el panel asociado.
     *
     * @param panel panel asociado al ActionListener
     */
    public ActionListenerGuardarTxtHistoricoAlumnos (PanelResultadoConsultarHistoricoAlumnos panel) {
        this.panelResultadoConsultarHistoricoAlumnos = panel;
        separador = 'a';
    } // fin del constructor


    /** Crea el ActionListener sin inicializar el panel asociado.
     *
     */
    public ActionListenerGuardarTxtHistoricoAlumnos () {
        separador = 'a';
    } // fin del constructor


    /** Método que captura el evento al pulsar el botón "Generar txt" del panel
     *  resultado de consultar el histórico de alumnos y ejecuta el código correspondiente
     *  a la generación del fichero txt de histórico de alumnos.
     *
     * @param arg0 evento capturado.
     */
    public void actionPerformed(ActionEvent arg0) {

        FrameEscogerCaracterSeparador frameEscojaSeparador = new FrameEscogerCaracterSeparador(
                                                                 FrameMenuPrincipal.getFramePrincipal(),
                                                                 true);
        frameEscojaSeparador.setVisible(true);

        if (this.separador != 'a') {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo TXT", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(panelResultadoConsultarHistoricoAlumnos);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                System.out.println("Se ha elegido este archivo: " +
                                    chooser.getSelectedFile().getAbsolutePath());

                String fichero = chooser.getSelectedFile().getAbsolutePath();
                String extension = fichero.substring(fichero.length()-3, fichero.length());
                if (!extension.equals("txt")) fichero+=".txt";
                    ManejadorFichero mf = new ManejadorFichero();

                    mf.GuardarFicheroConsultaHistoricoAlumnos(fichero, separador,
                                                            panelResultadoConsultarHistoricoAlumnos);
                }
            this.separador = 'a';
        }
    } // fin del método actionPerformed
    
} // fin de la clase ActionListenerGuardarTxtHistoricoAlumnos