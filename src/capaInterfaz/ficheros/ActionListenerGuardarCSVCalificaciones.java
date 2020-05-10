
package capaInterfaz.ficheros;

import capaInterfaz.menuCalificaciones.PanelResultadoConsultarCalificaciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Clase que gestiona la generación de un fichero CSV a partir de una consulta
 *  de calificaciones de alumnos.
 *
 * @author Confiencial
 */
public class ActionListenerGuardarCSVCalificaciones implements ActionListener{
    private PanelResultadoConsultarCalificaciones panelResultadoConsultarCalificaciones;

    /** Carácter separador de campos en el fichero. Inicialmente será ','.
     *
     */
    public static char separador = ',';

    /** Crea el ActionListener e inicializa el panel asociado.
     *
     * @param panel panel asociado al ActionListener
     */
    public ActionListenerGuardarCSVCalificaciones (PanelResultadoConsultarCalificaciones panel) {
        this.panelResultadoConsultarCalificaciones = panel;
        separador = ',';
    } // fin del constructor


    /** Crea el ActionListener sin inicializar el panel asociado.
     *
     */
    public ActionListenerGuardarCSVCalificaciones () {
        separador = ',';
    } // fin del constructor



    /** Método que captura el evento al pulsar el botón "Generar CSV" del panel
     *  resultado de consultar calificaciones y ejecuta el código correspondiente
     *  a la generación del fichero CSV de calificaciones.
     *
     * @param arg0 evento capturado.
     */
    public void actionPerformed(ActionEvent arg0) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo CSV", "csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(panelResultadoConsultarCalificaciones);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

            System.out.println("Se ha elegido este archivo: " +
                                chooser.getSelectedFile().getAbsolutePath());

            String fichero = chooser.getSelectedFile().getAbsolutePath();
            String extension = fichero.substring(fichero.length()-3, fichero.length());
            if (!extension.equals("csv")) fichero+=".csv";
                ManejadorFichero mf = new ManejadorFichero();

                mf.GuardarFicheroCSVConsultaCalificaciones(fichero, separador,
                                                           panelResultadoConsultarCalificaciones);
            }
        this.separador = ',';
	}// fin del método actionPerformed

} // fin de la clase ActionListenerGuardarCSVCalificaciones