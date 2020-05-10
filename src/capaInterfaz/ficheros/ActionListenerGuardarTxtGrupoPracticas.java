
package capaInterfaz.ficheros;

import capaInterfaz.menuGruposPracticas.PanelResultadoConsultaGruposPracticas;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Clase que gestiona la generación de un fichero txt a partir de una consulta
 *  de grupos de prácticas.
 *
 * @author Confiencial
 */
public class ActionListenerGuardarTxtGrupoPracticas implements ActionListener{

    private PanelResultadoConsultaGruposPracticas panelResultadoConsultaGruposPracticas;

    /** Carácter separador de campos en el fichero. Inicialmente será 'a'.
     */
    public static char separador = 'a';


    /** Crea el ActionListener e inicializa el panel asociado.
     *
     * @param panel panel asociado al ActionListener
     */
    public ActionListenerGuardarTxtGrupoPracticas (PanelResultadoConsultaGruposPracticas panel) {
        this.panelResultadoConsultaGruposPracticas = panel;
        separador = 'a';
    } // fin del constructor


    /** Crea el ActionListener sin inicializar el panel asociado.
     *
     */
    public ActionListenerGuardarTxtGrupoPracticas () {
        separador = 'a';
    } // fin del constructor



    /** Método que captura el evento al pulsar el botón "Generar txt" del panel
     *  resultado de consultar grupos de prácticas y ejecuta el código correspondiente
     *  a la generación del fichero txt de grupos de prácticas.
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
            int returnVal = chooser.showSaveDialog(panelResultadoConsultaGruposPracticas);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                System.out.println("Se ha elegido este archivo: " +
                                    chooser.getSelectedFile().getAbsolutePath());

                String fichero = chooser.getSelectedFile().getAbsolutePath();
                String extension = fichero.substring(fichero.length()-3, fichero.length());
                if (!extension.equals("txt")) fichero+=".txt";
                    ManejadorFichero mf = new ManejadorFichero();

                    mf.GuardarFicheroConsultaGruposPracticas(fichero, separador,
                                                             panelResultadoConsultaGruposPracticas);
                }
            this.separador = 'a';
        }
    } // fin del método actionPerformed

} // fin de la clase ActionListenerGuardarTxtGrupoPracticas